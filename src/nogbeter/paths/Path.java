package nogbeter.paths;

import static bezier.util.Util.square;
import nogbeter.paths.compound.SetIndex;
import nogbeter.paths.compound.ShapeSet;
import nogbeter.paths.results.intersections.IIntersections;
import nogbeter.paths.results.project.BestProject;
import nogbeter.paths.results.project.BestProjectTup;
import nogbeter.paths.simple.SimplePathIndex;
import nogbeter.paths.simple.lines.DiagonalLine;
import nogbeter.paths.simple.lines.HorizontalLine;
import nogbeter.paths.simple.lines.VerticalLine;
import nogbeter.paths.simple.nonlinear.Curve;
import nogbeter.points.twod.BBox;
import nogbeter.points.twod.Vec;
import nogbeter.transform.AffineTransformation;
import bezier.util.Tuple;

public abstract class Path<PathParam extends PathIndex,LSimp extends Path,RSimp extends Path> {
	
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

	public abstract <RPP extends PathIndex,RLSimp extends Path,RRSimp extends Path> 
		IIntersections<PathParam, RPP> intersection(
			Path<RPP,RLSimp,RRSimp> other);

	public abstract IIntersections<SimplePathIndex, PathParam> intersectionLDiaLine(
			DiagonalLine lhs);

	public abstract IIntersections<SimplePathIndex, PathParam>  intersectionLHorLine(
			HorizontalLine lhs);

	public abstract IIntersections<SimplePathIndex, PathParam> intersectionLVerLine(
			VerticalLine lhs);

	public IIntersections<SimplePathIndex, PathParam> intersectionLCurve(
			Curve lhs){
		return intersectionLSplittable(lhs);
	}
	
	public abstract IIntersections<SetIndex, PathParam> intersectionLSet(
			ShapeSet lhs);


	
	public abstract <LPP extends PathIndex, LLSimp extends Path,LRSimp extends Path> IIntersections<LPP, PathParam> 
			intersectionLSplittable(
				SplittablePath<LPP,LLSimp,LRSimp> lhs);
	
	
	public BestProject<PathParam> project(Vec p) {;
		return project(Double.POSITIVE_INFINITY,p);
	}
	

	public abstract BestProject<PathParam> project(double best, Vec p);


	public abstract <RPP extends PathIndex,RLS extends Path,RRS extends Path> 
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
	
	public abstract <LPI extends PathIndex,LLS extends Path,LRS extends Path> BestProjectTup<LPI, PathParam> 
			projectLSplittable(
			double best, Path<LPI,LLS,LRS> lhs) ;
	

	protected double minDistTo(BBox br){
		BBox bl = getBBox();
		double xDist = square(bl.xInterval.minDistance(br.xInterval));
		double yDist = square(bl.yInterval.minDistance(br.yInterval));
		return xDist + yDist;
	}
	
	
	public abstract int nrChildren();
	public abstract Path getChild(int i);
	

	public abstract Path<PathParam,LSimp,RSimp> 
		getWithAdjustedStartPoint(Vec newStartPoint);
	
	public abstract Vec getStartPoint();
	public abstract Vec getEndPoint();
	
	public boolean isClosed(){
		return getStartPoint().isEqError(getEndPoint());
	}
	
	public abstract Path<PathParam,LSimp,RSimp> transform(AffineTransformation t);
}
