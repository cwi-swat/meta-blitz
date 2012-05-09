package nogbeter.paths;

import static nogbeter.paths.results.transformers.TupleTransformers.*;
import nogbeter.paths.compound.SetIndex;
import nogbeter.paths.compound.ShapeSet;
import nogbeter.paths.results.intersections.IIntersections;
import nogbeter.paths.results.intersections.Intersections;
import nogbeter.paths.results.project.BestProject;
import nogbeter.paths.results.project.BestProjectTup;
import nogbeter.paths.results.transformers.IPathIndexTransformer;
import nogbeter.paths.results.transformers.PathIndexTupleTransformer;
import nogbeter.paths.simple.SimplePathIndex;
import nogbeter.paths.simple.lines.DiagonalLine;
import nogbeter.paths.simple.lines.HorizontalLine;
import nogbeter.paths.simple.lines.Line;
import nogbeter.paths.simple.lines.VerticalLine;
import nogbeter.points.twod.BBox;
import nogbeter.points.twod.Vec;
import bezier.util.Tuple;

public abstract class SplittablePath<PathParam extends PathIndex>
		extends Path<PathParam> {


	public abstract IPathIndexTransformer<PathParam> getLeftTransformer();
	public abstract IPathIndexTransformer<PathParam> getRightTransformer();
	
	// below: memo's transformers
	public abstract <B extends PathIndex> PathIndexTupleTransformer<PathParam,B> getLeftLeftTransformer();
	public abstract <B extends PathIndex> PathIndexTupleTransformer<PathParam,B> getLeftRightTransformer();
	public abstract <B extends PathIndex> PathIndexTupleTransformer<B,PathParam> getRightLeftTransformer();
	public abstract <B extends PathIndex> PathIndexTupleTransformer<B,PathParam> getRightRightTransformer();
	
	@Override
	public<RPP extends PathIndex> 
	IIntersections<PathParam, RPP> intersection(Path<RPP> other) {
		return other.intersectionLSplittable(this);
	}

	private IIntersections<SimplePathIndex, PathParam> intersectionLine(Line lhs){
		if(lhs.overlaps(getBBox())){
			Tuple<Path,Path> sp = splitSimpler();
			return lhs.intersection(sp.l).transform(getRightLeftTransformer()).append(
					lhs.intersection(sp.r).transform(getRightRightTransformer()));
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
	public <LPP extends PathIndex> 
		IIntersections<LPP, PathParam> intersectionLSplittable(
			SplittablePath<LPP> lhs) {
		if(!lhs.getBBox().overlaps(getBBox())){
			return Intersections.NoIntersections;
		}
		if (getBBox().area() > lhs.getBBox().area()) {
			Tuple<Path,Path> simp = splitSimpler();
			IIntersections<LPP, PathParam> l =
					lhs.intersection(simp.l).transform(getRightLeftTransformer());
			IIntersections<LPP, PathParam> r=
					lhs.intersection(simp.r).transform(getRightRightTransformer());
			return l.append(r);
		} else {
			Tuple<Path,Path> simp = lhs.splitSimpler();
			IIntersections<LPP, PathParam> l =
					simp.l.intersection(this).transform(lhs.getLeftLeftTransformer());
			IIntersections<LPP, PathParam> r=
					simp.r.intersection(this).transform(lhs.getLeftRightTransformer());
			return l.append(r);

		}
	}
	public BestProject<PathParam> project(double best, Vec p){
		if(getBBox().getNearestPoint(p).distanceSquared(p) > best){
			return BestProject.noBestYet;
		}
		Tuple<Path,Path> sp = splitSimpler();
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
			Tuple<Path,Path> sp = splitSimpler();
			if(lhs.distanceSquared(sp.l.getBBox().getMiddle()) <
					lhs.distanceSquared(sp.r.getBBox().getMiddle())){
				BestProjectTup<SimplePathIndex, PathParam> fsbest = 
						lhs.project(best,sp.l).transform(getRightLeftTransformer());
				return fsbest.choose(lhs.project(fsbest.distSquared,sp.r)
						.transform(right(getRightTransformer())));
			} else {
				BestProjectTup<SimplePathIndex, PathParam> fsbest = 
						lhs.project(best,sp.r).transform(getRightRightTransformer());
				return fsbest.choose(lhs.project(fsbest.distSquared,sp.l)
						.transform(right(getLeftTransformer())));
			}
		} else {
			return BestProjectTup.noBestYet;
		}
	}
	
	@Override
	public <RPP extends PathIndex> 
		BestProjectTup<PathParam, RPP> project(
			double best, Path<RPP> other) {
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
	

	public <LPI extends PathIndex> 
			BestProjectTup<LPI, PathParam> 
			projectLSplittable(double best, SplittablePath<LPI> lhs) {
		if(best < minDistTo(lhs.getBBox())){
			return BestProjectTup.noBestYet;
		}
		if (getBBox().area() > lhs.getBBox().area()) {
			Tuple<Path,Path> sp = splitSimpler();
			if(sp.l.getBBox().avgDistSquared(lhs.getBBox().getMiddle()) <
					sp.l.getBBox().avgDistSquared(lhs.getBBox().getMiddle())){
				BestProjectTup<LPI, PathParam> res = lhs.project(best, sp.l)
						.transform(getRightLeftTransformer());
				return res.choose(lhs.project(res.distSquared, sp.r)
						.transform(getRightRightTransformer()));
			} else {
				BestProjectTup<LPI, PathParam> res = lhs.project(best, sp.r)
						.transform(getRightRightTransformer());
				return res.choose(lhs.project(res.distSquared, sp.l)
						.transform(getRightLeftTransformer()));
			}
		} else {
			Tuple<Path,Path> sp = lhs.splitSimpler();
			if(sp.l.getBBox().avgDistSquared(getBBox().getMiddle()) <
					sp.l.getBBox().avgDistSquared(getBBox().getMiddle())){
				BestProjectTup<LPI, PathParam> res = sp.l.project(best, this)
						.transform(left(lhs.getLeftTransformer()));
				return res.choose(sp.r.project(res.distSquared, this)
						.transform(left(lhs.getRightTransformer())));
			} else {
				BestProjectTup<LPI, PathParam> res = sp.r.project(best, this)						
						.transform(left(lhs.getRightTransformer()));
				return res.choose(sp.l.project(res.distSquared, this)						
						.transform(left(lhs.getLeftTransformer())));
			}
		}

	}
	

	public abstract Tuple<Path,Path> splitSimpler() ;

}
