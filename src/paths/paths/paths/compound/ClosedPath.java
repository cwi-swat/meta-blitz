package paths.paths.paths.compound;


import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import paths.crossing.CrossingsInfo;
import paths.paths.paths.Path;
import paths.paths.paths.PathIndex;
import paths.paths.paths.SplittablePath;
import paths.paths.paths.simple.Line;
import paths.paths.paths.simple.SimplePathIndex;
import paths.paths.results.intersections.IIntersections;
import paths.paths.results.project.BestProject;
import paths.paths.results.project.BestProjectTup;
import paths.paths.results.transformers.IPathIndexTransformer;
import paths.paths.results.transformers.PITransformers;
import paths.paths.results.transformers.PathIndexTupleTransformer;
import paths.paths.results.transformers.TupleTransformers;
import paths.points.angles.AngularInterval;
import paths.points.angles.AngularIntervalFactory;
import paths.points.twod.BBox;
import paths.points.twod.Vec;
import paths.transform.IToTransform;
import paths.transform.nonlinear.IDeform;
import paths.transform.nonlinear.ILineTransformer;
import paths.transform.nonlinear.pathdeform.PathDeform;

import util.Tuple;


public class ClosedPath extends Path<ClosedPathIndex>{
	
	final Path<PathIndex> actual;
	final PathIndexTupleTransformer<?,?> closeLeft, closeRight;
	final IPathIndexTransformer<ClosedPathIndex> closeTransformer;
	final PathIndex minPathIndex, maxPathIndex;
	
	public ClosedPath(Path<PathIndex> actual) {
		this.actual = actual;
		this.minPathIndex = actual.minPathIndex();
		this.maxPathIndex = actual.maxPathIndex();
		this.closeTransformer=  
				PITransformers.closedT(minPathIndex, maxPathIndex);
		this.closeLeft = TupleTransformers.left(closeTransformer);
		this.closeRight = TupleTransformers.right(closeTransformer);
	}

	@Override
	public BBox makeBBox() {
		return actual.makeBBox();
	}

	@Override
	public Vec getAt(ClosedPathIndex t) {
		return actual.getAt(t.next);
	}

	@Override
	public Vec getTangentAt(ClosedPathIndex t) {
		return actual.getTangentAt(t.next);
	}

	@Override
	public <RPP extends PathIndex> IIntersections<ClosedPathIndex, RPP> intersection(
			Path<RPP> other) {
		return (IIntersections<ClosedPathIndex, RPP>) 
				actual.intersection(other).transform(closeLeft);
	}

	@Override
	public IIntersections<SimplePathIndex, ClosedPathIndex> intersectionLLine(
			Line lhs) {
		return (IIntersections<SimplePathIndex, ClosedPathIndex>) 
				actual.intersectionLLine(lhs).transform(closeRight);
	}

	@Override
	public IIntersections<SetIndex, ClosedPathIndex> intersectionLSet(
			ShapeSet lhs) {
		return (IIntersections<SetIndex, ClosedPathIndex>) 
				actual.intersectionLSet(lhs).transform(closeRight);
	}

	@Override
	public <LPP extends PathIndex> IIntersections<LPP, ClosedPathIndex> intersectionLSplittable(
			SplittablePath<LPP> lhs) {
		return (IIntersections<LPP, ClosedPathIndex>) 
				actual.intersectionLSplittable(lhs).transform(closeRight);
	}

	@Override
	public BestProject<ClosedPathIndex> project(double best, Vec p) {
		return actual.project(best,p).transform(closeTransformer);
	}

	@Override
	public <RPP extends PathIndex> BestProjectTup<ClosedPathIndex, RPP> project(
			double best, Path<RPP> other) {
		return (BestProjectTup<ClosedPathIndex, RPP>)
				actual.project(other).transform(closeLeft);
	}

	@Override
	public BestProjectTup<SimplePathIndex, ClosedPathIndex> projectLLine(
			double best, Line lhs) {
		return 
				(BestProjectTup<SimplePathIndex, ClosedPathIndex>)
				actual.projectLLine(best,lhs).transform(closeRight);
	}
	@Override
	public BestProjectTup<SetIndex, ClosedPathIndex> projectLSet(double best,
			ShapeSet lhs) {
		return 
				(BestProjectTup<SetIndex, ClosedPathIndex>)
				actual.projectLSet(best,lhs).transform(closeRight);
	}

	@Override
	public <LPI extends PathIndex> BestProjectTup<LPI, ClosedPathIndex> projectLSplittable(
			double best, SplittablePath<LPI> lhs) {
		return 
				(BestProjectTup<LPI, ClosedPathIndex>)
				actual.projectLSplittable(best,lhs).transform(closeRight);
	}

	@Override
	public int nrChildren() {
		return 1;
	}

	@Override
	public Path getChild(int i) {
		return actual;
	}

	@Override
	public Path<ClosedPathIndex> getWithAdjustedStartPoint(Vec newStartPoint) {
		return new ClosedPath(actual.getWithAdjustedStartPoint(newStartPoint));
	}

	@Override
	public Vec getStartPoint() {
		return actual.getStartPoint();
	}

	@Override
	public Vec getEndPoint() {
		return actual.getStartPoint();
	}

	@Override
	public Path<ClosedPathIndex> transform(IToTransform t) {
		return new ClosedPath(actual.transform(t));
	}

	@Override
	public Tuple<Path<ClosedPathIndex>, Double> normaliseToLength(
			double prevLength) {
		Tuple<Path<PathIndex>, Double> resDeep = actual.normaliseToLength(prevLength);
		return new Tuple<Path<ClosedPathIndex>, Double>(new ClosedPath(resDeep.l), resDeep.r);
	}

	@Override
	public void getSubPath(ClosedPathIndex from, ClosedPathIndex to,
			List<Path> result) {
		if(from.compareTo(to) < 0){
			actual.getSubPath(from.next, to.next, result);
		} else {
			actual.getSubPathFrom(from.next, result);
			actual.getSubPathTo(to.next, result);
		}
	}

	@Override
	public void getSubPathFrom(ClosedPathIndex from, List<Path> result) {
		throw new Error("SubPathFrom of closedPath is whole path!");
	}

	@Override
	public void getSubPathTo(ClosedPathIndex to, List<Path> result) {
		throw new Error("SubPathTill of closedPath is whole path!");
	}

	@Override
	public AngularInterval getAngularInsideInterval(ClosedPathIndex t) {
		if(t.next.isEq(minPathIndex) || t.next.isEq(maxPathIndex)){
			return 
			AngularIntervalFactory.createAngularIntervalSingleIfEq(actual.getStartTan(),
					actual.getEndTan().negate());
		}
		return actual.getAngularInsideInterval(t.next);
	}

	@Override
	public Vec getStartTan() {
		throw new Error("Closedpath has no start or end!");
	}

	@Override
	public Vec getEndTan() {
		throw new Error("Closedpath has no start or end!");
	}
	
	@Override
	public Path getClosedPath(ClosedPathIndex p) {
		return this;
	}
	
	@Override
	public void getClosedSegmentsNotInSet(Set<Path> segments, List<ClosedPath> res){
		if(!segments.contains(this)){
			res.add(this);
		}
	}

	@Override
	public Path<PathIndex> reverse() {
		return (Path)new ClosedPath(actual.reverse());
	}

	public Vec getArbPoint(){ return actual.getArbPoint();}
	
	public Vec getArbPointTan(){ return actual.getArbPointTan();}
	
	public boolean isDefindedClockwise() {
		Vec outside = getBBox().getLeftUp().add(new Vec(-10,-10));
		return !isInside(outside);
	}
	
	@Override
	public String toString() {
		return String.format("Closed(%s)",actual.toString());
	}
	
	@Override
	public PathIndex minPathIndex() {
		throw new Error("ClosedPath does not have begin nor end!");
	}

	@Override
	public PathIndex maxPathIndex() {
		throw new Error("ClosedPath does not have begin nor end!");
	}

	@Override
	public List<Vec> getTangents(ClosedPathIndex t) {
		if(t.next.isEq(minPathIndex) || t.next.isEq(maxPathIndex)){
			List<Vec> v = new ArrayList<Vec>();
			v.addAll(actual.getTangents(minPathIndex));
			v.addAll(actual.getTangents(maxPathIndex));
			return v;
		} else {
			return actual.getTangents(t.next);
		}
	}

	public boolean isSelfCrossing() {
		CrossingsInfo<ClosedPathIndex, ClosedPathIndex> ci = crossingsInfo(this);
		return !ci.isEmpty();
	}

	@Override
	public Path<ClosedPathIndex> deformActual(IDeform p) {
		return new ClosedPath(actual.deform(p));
	}

	@Override
	public Path transformApproxLines(ILineTransformer t) {
		return new ClosedPath(actual.transformApproxLines(t));
	}

}
