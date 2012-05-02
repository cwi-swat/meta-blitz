package nogbeter.paths;

import static nogbeter.paths.results.transformers.TupleTransformers.*;
import nogbeter.paths.compound.SetIndex;
import nogbeter.paths.compound.ShapeSet;
import nogbeter.paths.results.intersections.IIntersections;
import nogbeter.paths.results.intersections.Intersections;
import nogbeter.paths.results.project.BestProject;
import nogbeter.paths.results.project.BestProjectTup;
import nogbeter.paths.results.transformers.IPathIndexTransformer;
import nogbeter.paths.simple.SimplePathIndex;
import nogbeter.paths.simple.lines.DiagonalLine;
import nogbeter.paths.simple.lines.HorizontalLine;
import nogbeter.paths.simple.lines.Line;
import nogbeter.paths.simple.lines.VerticalLine;
import nogbeter.util.BBox;
import bezier.points.Vec;
import bezier.util.Tuple;

public abstract class SplittablePath<PathParam extends PathIndex,LSimp extends Path,RSimp extends Path>
		extends Path<PathParam,LSimp,RSimp> {


	public abstract IPathIndexTransformer<PathParam> getLeftTransformer();
	public abstract IPathIndexTransformer<PathParam> getRightTransformer();
	
	@Override
	public<RPP extends PathIndex,RLSimp extends Path,RRSimp extends Path> 
	IIntersections<PathParam, RPP> intersection(Path<RPP,RLSimp,RRSimp> other) {
		return other.intersectionLSplittable(this);
	}

	private IIntersections<SimplePathIndex, PathParam> intersectionLine(Line lhs){
		if(lhs.overlaps(getBBox())){
			Tuple<LSimp,RSimp> sp = splitSimpler();
			return lhs.intersection(sp.l).transform(right(getLeftTransformer())).append(
					lhs.intersection(sp.r).transform(right(getRightTransformer())));
		} else {
			return Intersections.NoIntersections;
		}
	}
	
	@Override
	public IIntersections<SimplePathIndex, PathParam> intersectionLDiaLine(
			DiagonalLine lhs) {
		return intersectionLine(lhs);
	}
	@Override
	public IIntersections<SimplePathIndex, PathParam> intersectionLHorLine(
			HorizontalLine lhs) {
		return intersectionLine(lhs);
	}
	@Override
	public IIntersections<SimplePathIndex, PathParam> intersectionLVerLine(
			VerticalLine lhs) {
		return intersectionLine(lhs);
	}
	@Override
	public IIntersections<SetIndex, PathParam> intersectionLSet(ShapeSet lhs) {
		return lhs.intersectionLSplittable(this).flip();
	}
	@Override
	public <LPP extends PathIndex, LLSimp extends Path, LRSimp extends Path> 
		IIntersections<LPP, PathParam> intersectionLSplittable(
			SplittablePath<LPP, LLSimp, LRSimp> lhs) {
		if (getBBox().area() > lhs.getBBox().area()) {
			Tuple<LSimp,RSimp> simp = splitSimpler();
			IIntersections<LPP, PathParam> l =
					lhs.intersection(simp.l).transform(right(getLeftTransformer()));
			IIntersections<LPP, PathParam> r=
					lhs.intersection(simp.r).transform(right(getRightTransformer()));
			return l.append(r);
		} else {
			Tuple<LLSimp,LRSimp> simp = lhs.splitSimpler();
			IIntersections<LPP, PathParam> l =
					simp.l.intersection(this).transform(left(lhs.getLeftTransformer()));
			IIntersections<LPP, PathParam> r=
					simp.r.intersection(this).transform(left(lhs.getRightTransformer()));
			return l.append(r);

		}
	}
	public BestProject<PathParam> project(double best, Vec p){
		if(getBBox().getNearestPoint(p).distanceSquared(p) > best){
			return BestProject.noBestYet;
		}
		Tuple<LSimp,RSimp> sp = splitSimpler();
		if (sp.l.getBBox().avgDistSquared(p) < 
			sp.r.getBBox().avgDistSquared(p)) {
			BestProject<PathParam> res = sp.l.project(best,p).transform(getLeftTransformer());
			return res.choose(sp.r.project(res.distSquared,p).transform(getRightTransformer()));
		} else {
			BestProject<PathParam> res = sp.r.project(best,p).transform(getRightTransformer());
			return res.choose(sp.l.project(res.distSquared,p).transform(getLeftTransformer()));
		}
	}
	
	private BestProjectTup<SimplePathIndex, PathParam> projectLine(double best,Line lhs){
		if(best > lhs.minDistSquaredTo(getBBox())){
			Tuple<LSimp,RSimp> sp = splitSimpler();
			if(lhs.distanceSquared(sp.l.getBBox().getMiddle()) <
					lhs.distanceSquared(sp.r.getBBox().getMiddle())){
				BestProjectTup<SimplePathIndex, PathParam> fsbest = 
						lhs.project(best,sp.l).transform(right(getLeftTransformer()));
				return fsbest.choose(lhs.project(fsbest.distSquared,sp.r).transform(right(getRightTransformer())));
			} else {
				BestProjectTup<SimplePathIndex, PathParam> fsbest = 
						lhs.project(best,sp.r).transform(right(getRightTransformer()));
				return fsbest.choose(lhs.project(fsbest.distSquared,sp.l).transform(right(getLeftTransformer())));
			}
		} else {
			return BestProjectTup.noBestYet;
		}
	}
	
	@Override
	public <RPP extends PathIndex, RLS extends Path, RRS extends Path> BestProjectTup<PathParam, RPP> project(
			double best, Path<RPP, RLS, RRS> other) {
		return other.projectLSplittable(best, this);
	}
	@Override
	public BestProjectTup<SimplePathIndex, PathParam> projectLDiaLine(
			double best, DiagonalLine lhs) {
		return projectLine(best, lhs);
	}
	@Override
	public BestProjectTup<SimplePathIndex, PathParam> projectLHorLine(
			double best, HorizontalLine lhs) {
		return projectLine(best, lhs);
	}
	@Override
	public BestProjectTup<SimplePathIndex, PathParam> projectLVerLine(
			double best, VerticalLine lhs) {
		return projectLine(best, lhs);
	}
	@Override
	public BestProjectTup<SetIndex, PathParam> projectLSet(double best,
			ShapeSet lhs) {
		return lhs.projectLSplittable(best, this).flip();
	}
	

	public <LPI extends PathIndex,LLS extends Path,LRS extends Path> BestProjectTup<LPI, PathParam> 
			projectLSplittable(
			double best, Path<LPI,LLS,LRS> lhs) {
		if(best < minDistTo(lhs.getBBox())){
			return BestProjectTup.noBestYet;
		}
		if (getBBox().area() > lhs.getBBox().area()) {
			Tuple<LSimp,RSimp> sp = splitSimpler();
			if(sp.l.getBBox().avgDistSquared(lhs.getBBox().getMiddle()) <
					sp.l.getBBox().avgDistSquared(lhs.getBBox().getMiddle())){
				BestProjectTup<LPI, PathParam> res = lhs.project(best, sp.l).transform(rightLeft());
				return res.choose(lhs.project(res.distSquared, sp.r).transform(rightRight()));
			} else {
				BestProjectTup<LPI, PathParam> res = lhs.project(best, sp.r).transform(rightRight());
				return res.choose(lhs.project(res.distSquared, sp.l).transform(rightLeft()));
			}
		} else {
			Tuple<LLS,LRS> sp = lhs.splitSimpler();
			if(sp.l.getBBox().avgDistSquared(getBBox().getMiddle()) <
					sp.l.getBBox().avgDistSquared(getBBox().getMiddle())){
				BestProjectTup<LPI, PathParam> res = sp.l.project(best, this).transform(leftLeft());
				return res.choose(sp.r.project(res.distSquared, this).transform(leftRight()));
			} else {
				BestProjectTup<LPI, PathParam> res = sp.r.project(best, this).transform(leftRight());
				return res.choose(sp.l.project(res.distSquared, this).transform(leftLeft()));
			}
		}

	}

}
