package paths.paths.paths;

import static util.Util.square;


import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import paths.crossing.Crossing;
import paths.crossing.CrossingsInfo;
import paths.crossing.IntersectionsToCrossings;
import paths.crossing.MakePathsFromCrossings;
import paths.paths.factory.PathFactory;
import paths.paths.iterators.ClosedPathIterator;
import paths.paths.iterators.ConnectedIterator;
import paths.paths.paths.compound.ClosedPath;
import paths.paths.paths.compound.NotClosedException;
import paths.paths.paths.compound.SetIndex;
import paths.paths.paths.compound.ShapeSet;
import paths.paths.paths.simple.Line;
import paths.paths.paths.simple.SimplePathIndex;
import paths.paths.paths.simple.curve.Curve;
import paths.paths.results.intersections.IIntersections;
import paths.paths.results.project.BestProject;
import paths.paths.results.project.BestProjectTup;
import paths.points.angles.AngularInterval;
import paths.points.twod.BBox;
import paths.points.twod.Vec;
import paths.transform.IToTransform;
import paths.transform.nonlinear.IDeform;
import paths.transform.nonlinear.ILineTransformer;
import paths.transform.nonlinear.pathdeform.PathDeform;

import util.Tuple;


public abstract class Path
	<PathParam extends PathIndex> 
	{
	
	protected BBox bbox;
	private Path lengthNormalized;
	private double length;

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
	
	public abstract Path<PathParam> transform(IToTransform t);
	
	public double length(){
		normaliseToLength();
		return length;
	}
	
	public Path<PathParam> normaliseToLength(){
		if(lengthNormalized == null){
			 Tuple<Path<PathParam>,Double> tp = normaliseToLength(0);
			lengthNormalized = tp.l;
			length = tp.r;
		}
		return lengthNormalized;
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
	
	public Path<PathParam> pathDeform(Path p){
		ConnectedIterator it = new ConnectedIterator(p);
		List<Path> res = new ArrayList<Path>();
		while(it.hasNext()){
			res.add(deform(new PathDeform(it.next())));
		}
		return PathFactory.createSet(res);
//		try{
//			
//			return res.union(res);
//		} catch(NotClosedException e){
//			throw new Error(e.getMessage());
//		}
	}
	
	public abstract Path transformApproxLines(ILineTransformer t);	
	public abstract Path<PathParam> deformActual(IDeform p);
	
	public Path<PathParam> deform(IDeform p){
		return deformActual(p.subDeform(getBBox()));
	}
}
