package nogbeter.paths;

import static bezier.util.Util.square;

import java.util.List;
import java.util.Set;

import nogbeter.crossing.Crossing;
import nogbeter.crossing.CrossingsInfo;
import nogbeter.crossing.IntersectionsToCrossings;
import nogbeter.crossing.MakePathsFromCrossings;
import nogbeter.paths.compound.ClosedPath;
import nogbeter.paths.compound.NotClosedException;
import nogbeter.paths.compound.SetIndex;
import nogbeter.paths.compound.ShapeSet;
import nogbeter.paths.results.intersections.IIntersections;
import nogbeter.paths.results.project.BestProject;
import nogbeter.paths.results.project.BestProjectTup;
import nogbeter.paths.simple.Line;
import nogbeter.paths.simple.SimplePathIndex;
import nogbeter.paths.simple.curve.Curve;
import nogbeter.points.angles.AngularInterval;
import nogbeter.points.twod.BBox;
import nogbeter.points.twod.Vec;
import nogbeter.transform.AffineTransformation;
import bezier.util.Tuple;

public abstract class Path
	<PathParam extends PathIndex> 
	{
	
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
	public abstract AngularInterval getAngularInsideInterval(PathParam t);
	public abstract List<Vec> getTangents(PathParam t);
	

	public abstract <RPP extends PathIndex> IIntersections<PathParam, RPP> intersection(
			Path<RPP> other);

	public abstract IIntersections<SimplePathIndex, PathParam> intersectionLLine(
			Line lhs);

	public IIntersections<SimplePathIndex, PathParam> intersectionLCurve(
			Curve lhs){
		return intersectionLSplittable(lhs);
	}
	
	public abstract IIntersections<SetIndex, PathParam> intersectionLSet(
			ShapeSet lhs);


	
	public abstract <LPP extends PathIndex> 
			IIntersections<LPP, PathParam> intersectionLSplittable(
				SplittablePath<LPP> lhs);
	
	
	public BestProject<PathParam> project(Vec p) {;
		return project(Double.POSITIVE_INFINITY,p);
	}
	
	
	public abstract BestProject<PathParam> project(double best, Vec p);

	public  <RPP extends PathIndex>  
			BestProjectTup<PathParam, RPP>  project(
					Path<RPP>  other){
		return project(Double.POSITIVE_INFINITY, other);
	}

	public abstract <RPP extends PathIndex> 
			BestProjectTup<PathParam, RPP> project(
			double best,
			Path<RPP>  other);
	
	public abstract BestProjectTup<SimplePathIndex, PathParam> projectLLine(
			double best,
			Line lhs);
	
	public BestProjectTup<SimplePathIndex, PathParam> projectLCurve(
			double best,
			Curve lhs){
		return projectLSplittable(best,lhs);
	}
	
	public abstract BestProjectTup<SetIndex, PathParam> projectLSet(
			double best,
			ShapeSet lhs);
	
	public abstract <LPI extends PathIndex> BestProjectTup<LPI, PathParam> 
			projectLSplittable(
			double best, SplittablePath<LPI> lhs) ;
	

	protected double minDistTo(BBox br){
		BBox bl = getBBox();
		double xDist = square(bl.xInterval.minDistance(br.xInterval));
		double yDist = square(bl.yInterval.minDistance(br.yInterval));
		return xDist + yDist;
	}
	
	
	public abstract int nrChildren();
	public abstract Path getChild(int i);
	

	public abstract Path<PathParam> 
		getWithAdjustedStartPoint(Vec newStartPoint);
	
	public abstract Vec getStartPoint();
	public abstract Vec getEndPoint();
	public abstract Vec getStartTan();
	public abstract Vec getEndTan();
	
	public boolean isClosed(){
		return getStartPoint().isEqError(getEndPoint());
	}
	
	public abstract Path<PathParam> transform(AffineTransformation t);
	
	public Path<PathParam> normaliseToLength(){
		return normaliseToLength(0).l;
	}
	
	public abstract Tuple<Path<PathParam>,Double> normaliseToLength(double prevLength);
	
	public abstract void getSubPath(PathParam from, PathParam to, List<Path> result);
	public abstract void getSubPathFrom(PathParam from, List<Path> result);
	public abstract void getSubPathTo(PathParam to, List<Path> result);
	
	public boolean isInside(Vec v){
		BestProject<PathParam> project = project(v); // getNearest

		Vec loc = getAt(project.t);
		Vec to = v.sub(loc);
		if(to.isEqError(Vec.ZeroVec)){
			return true;
		} else {
			AngularInterval interval = getAngularInsideInterval(project.t);
			return interval.isInside(to);
		}
	}
	
	
	public Path getClosedPath(PathParam p) {
		throw new Error("Cannot contain segment");
	}
	
	
	public void getClosedSegmentsNotInSet(Set<Path> segments, List<ClosedPath> res){
		throw new Error("Cannot contain closed segment");
	}
	
	public abstract Path<PathIndex> reverse();
	
	public abstract Vec getArbPoint();
	public abstract Vec getArbPointTan();
	
	public boolean contains(Path other){
		Vec v = other.getArbPoint();
		BestProject<PathParam> project = project(v); // getNearest
		Vec loc = getAt(project.t);
		Vec to = v.sub(loc);
		AngularInterval interval = getAngularInsideInterval(project.t);
		return interval.isInside(to);
	}
	
	public boolean isOnBorder(Vec v){
		return project(v).distSquared == 0;
	}
	
	public abstract PathIndex minPathIndex();
	public abstract PathIndex maxPathIndex();
	
	public <RPP extends PathIndex> List<Crossing<PathParam,RPP>> crossings(Path<RPP> other){
		IIntersections<PathParam,RPP> inters = intersection(other);
		return crossingsInfo(other).crossings;
	}
	
	public <RPP extends PathIndex> CrossingsInfo<PathParam, RPP> crossingsInfo(Path<RPP> other){
		IIntersections<PathParam,RPP> inters = intersection(other);
		return new IntersectionsToCrossings(inters, this, other).getCrossingsInfo();
	}
	
	public <RPP extends PathIndex> Path union(Path<RPP> other) throws NotClosedException{
		return new 
				MakePathsFromCrossings<PathParam, RPP>
		(crossingsInfo(other), false, false, false).makeAllPaths();
	}
	
	public <RPP extends PathIndex> Path intersectionOp(Path<RPP> other) throws NotClosedException{
		return new 
				MakePathsFromCrossings<PathParam, RPP>
		(crossingsInfo(other), true, true, false).makeAllPaths();
	}
	
	public <RPP extends PathIndex> Path subtract(Path<RPP> other) throws NotClosedException{
		return new 
				MakePathsFromCrossings<PathParam, RPP>
		(crossingsInfo(other), false, true, true).makeAllPaths();
	}
}
