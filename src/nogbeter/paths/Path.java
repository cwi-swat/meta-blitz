package nogbeter.paths;

import static bezier.util.Util.square;

import java.util.LinkedList;
import java.util.List;

import nogbeter.paths.compound.CompoundSplitIndex;
import nogbeter.paths.compound.SetIndex;
import nogbeter.paths.compound.ShapeSet;
import nogbeter.paths.compound.SplittableCompoundPath;
import nogbeter.paths.compound.CompoundSplitIndex.SplitChoice;
import nogbeter.paths.simple.SimplePathIndex;
import nogbeter.paths.simple.lines.DiagonalLine;
import nogbeter.paths.simple.lines.HorizontalLine;
import nogbeter.paths.simple.lines.VerticalLine;
import nogbeter.paths.simple.nonlinear.Curve;
import nogbeter.paths.simple.nonlinear.QuadCurve;
import nogbeter.util.BBox;
import bezier.points.Vec;
import bezier.util.STuple;
import bezier.util.Tuple;
import bezier.util.Util;

public abstract class Path<PathParam,LSimp extends Path,RSimp extends Path> {

	protected BBox bbox;

	public abstract BBox makeBBox();

	public BBox getBBox() {
		if (bbox == null) {
			bbox = makeBBox();
		}
		return bbox;
	}
	
	public abstract Vec getAt(PathParam t);
	public abstract Vec getTangentAt(PathParam t);
	
	
	public abstract Tuple<LSimp,RSimp> splitSimpler() ;

	public abstract <RPP,RLSimp extends Path,RRSimp extends Path> Tuple<List<PathParam>, List<RPP>> intersection(
			Path<RPP,RLSimp,RRSimp> other);

	public abstract Tuple<List<SimplePathIndex>, List<PathParam>> intersectionLDiaLine(
			DiagonalLine lhs);

	public abstract Tuple<List<SimplePathIndex>, List<PathParam>> intersectionLHorLine(
			HorizontalLine lhs);

	public abstract Tuple<List<SimplePathIndex>, List<PathParam>> intersectionLVerLine(
			VerticalLine lhs);

	public Tuple<List<SimplePathIndex>, List<PathParam>> intersectionLCurve(
			Curve lhs){
		return intersectionLSplittable(lhs);
	}
	
	public abstract Tuple<List<SetIndex>, List<PathParam>> intersectionLSet(
			ShapeSet lhs);

	public <LPI,LLSimp extends Path,LRSimp extends Path> Tuple<List<LPI>, List<PathParam>> 
			intersectionLSplittable(
				Path<LPI,LLSimp,LRSimp> lhs){
		if (getBBox().diagonalLengthSquared() > lhs.getBBox()
				.diagonalLengthSquared()) {
			Tuple<LSimp,RSimp> simp = splitSimpler();
			return Util.appendTupList(prependLeftListRhs(lhs.intersection(simp.l)),
					prependRightListRhs(lhs.intersection(simp.r)));
		} else {
			Tuple<LLSimp,LRSimp> simp = lhs.splitSimpler();
			return Util.appendTupList(
					lhs.prependLeftListRhs(simp.l.intersection(this)).flip(),
					lhs.prependLeftListRhs(simp.r.intersection(this)).flip());
		}
	}
	
	public abstract  <LPI> Tuple<List<LPI>, List<PathParam>> prependRightListRhs(
			Tuple<List<LPI>, List<? extends PathIndex>> intersections) ;

	public abstract <LPI> Tuple<List<LPI>, List<PathParam>> prependLeftListRhs(
			Tuple<List<LPI>, List<? extends PathIndex>> intersections) ;

	public BestProject<PathParam> project(Vec p) {;
		return project(Double.POSITIVE_INFINITY,p);
	}
	

	public BestProject<PathParam> project(double best, Vec p) {
		if(getBBox().getNearestPoint(p).distanceSquared(p) > best){
			return BestProject.noBestYet;
		}
		Tuple<LSimp,RSimp> sp = splitSimpler();
		if (sp.l.getBBox().avgDistSquared(p) < 
			sp.r.getBBox().avgDistSquared(p)) {
			return projectSimpler(best,p,sp.l,sp.r);
		} else {
			return projectSimpler(best,p,sp.r,sp.l);
		}
	}
	
	public BestProject
		projectSimpler(double best, Vec p, Path fst,
			Path snd) {
		BestProject fstb = fst.project(best, p);
		return fstb.choose(snd.project(fstb.distSquared,p));
	}


	public abstract <RPP,RLS extends Path,RRS extends Path> 
			BestProjectTup<PathParam, RPP> project(
			double best,
			Path<RPP,RLS,RRS>  other);
	
	public abstract BestProjectTup<SimplePathIndex, PathParam> projectLDiaLine(
			double best,
			DiagonalLine lhs);
	
	public abstract BestProjectTup<SimplePathIndex, PathParam> projectLHorLine(
			double best,
			HorizontalLine lhs);
	
	public abstract BestProjectTup<SimplePathIndex, PathParam> projectLVerLine(
			double best,
			VerticalLine lhs);
	
	public BestProjectTup<SimplePathIndex, PathParam> projectLCurve(
			double best,
			Curve lhs){
		return projectLSplittable(best,lhs);
	}
	
	public abstract BestProjectTup<SetIndex, PathParam> projectLSet(
			double best,
			ShapeSet lhs);
	
	public <LPI,LLS extends Path,LRS extends Path> BestProjectTup<LPI, PathParam> 
			projectLSplittable(
			double best, Path<LPI,LLS,LRS> lhs) {
		if(best > minDistTo(lhs.getBBox())){
			if (getBBox().diagonalLengthSquared() > lhs.getBBox()
					.diagonalLengthSquared()) {
				Tuple<LSimp,RSimp> sp = splitSimpler();
				if(sp.l.getBBox().avgDistSquared(lhs.getBBox().getMiddle()) <
						sp.l.getBBox().avgDistSquared(lhs.getBBox().getMiddle())){
					return prependLeftBestTupRhs(projectSimplerTup(best, lhs, sp.l, sp.r));
				} else {
					return prependRightBestTupRhs(projectSimplerTup(best, lhs, sp.r, sp.l));
				}
			} else {
				Tuple<LLS,LRS> sp = lhs.splitSimpler();
				if(sp.l.getBBox().avgDistSquared(getBBox().getMiddle()) <
						sp.l.getBBox().avgDistSquared(getBBox().getMiddle())){
					return lhs.prependLeftBestTupRhs(projectSimplerTup(best, this, sp.l, sp.r)).flip();
				} else {
					return lhs.prependRightBestTupRhs(projectSimplerTup(best, this, sp.r, sp.l)).flip();
				}
			}
		} else {
			return (BestProjectTup)BestProject.noBestYet;
		}
	}
	
	public abstract <LPI> BestProjectTup<LPI, PathParam> prependLeftBestTupRhs(
			BestProjectTup<LPI,? extends PathIndex> projectSimplerTup);
	
	public abstract <LPI> BestProjectTup<LPI, PathParam> prependRightBestTupRhs(
			BestProjectTup<LPI,? extends PathIndex> projectSimplerTup);
	
	
	public abstract <LPI> BestProjectTup<PathParam,LPI> prependLeftBestTupLhs(
			BestProjectTup<? extends PathIndex,LPI> projectSimplerTup);
	
	public abstract <LPI> BestProjectTup<PathParam,LPI> prependRightBestTupLhs(
			BestProjectTup<? extends PathIndex,LPI> projectSimplerTup);

	protected double minDistTo(BBox br){
		BBox bl = getBBox();
		double xDist = square(bl.xInterval.minDistance(br.xInterval));
		double yDist = square(bl.yInterval.minDistance(br.yInterval));
		return xDist + yDist;
	}
	
	public static BestProjectTup projectSimplerTup(
			double best, Path p, 
			Path fst,
			Path snd) {
		BestProjectTup fstb = p.project(best, fst);
		return fstb.choose(p.project(fstb.distSquared,snd));
	}
}
