package nogbeter.paths.compound;

import java.util.List;
import java.util.Set;

import bezier.util.Tuple;
import nogbeter.paths.Path;
import nogbeter.paths.PathIndex;
import nogbeter.paths.SplittablePath;
import nogbeter.paths.results.intersections.IIntersections;
import nogbeter.paths.results.project.BestProject;
import nogbeter.paths.results.project.BestProjectTup;
import nogbeter.paths.results.transformers.PITransformers;
import nogbeter.paths.results.transformers.PathIndexTupleTransformer;
import nogbeter.paths.results.transformers.TupleTransformers;
import nogbeter.paths.simple.Line;
import nogbeter.paths.simple.SimplePathIndex;
import nogbeter.points.angles.AngularInterval;
import nogbeter.points.angles.AngularIntervalFactory;
import nogbeter.points.twod.BBox;
import nogbeter.points.twod.Vec;
import nogbeter.transform.AffineTransformation;

public class ClosedPath extends Path<ClosedPathIndex>{
	
	final Path<PathIndex> actual;
	
	public ClosedPath(Path<PathIndex> actual) {
		this.actual = actual;
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
		return (IIntersections<ClosedPathIndex, RPP>) actual.intersection(other).transform(TupleTransformers.closeLeft);
	}

	@Override
	public IIntersections<SimplePathIndex, ClosedPathIndex> intersectionLLine(
			Line lhs) {
		return (IIntersections<SimplePathIndex, ClosedPathIndex>) 
				actual.intersectionLLine(lhs).transform(TupleTransformers.closeRight);
	}

	@Override
	public IIntersections<SetIndex, ClosedPathIndex> intersectionLSet(
			ShapeSet lhs) {
		return (IIntersections<SetIndex, ClosedPathIndex>) 
				actual.intersectionLSet(lhs).transform(TupleTransformers.closeRight);
	}

	@Override
	public <LPP extends PathIndex> IIntersections<LPP, ClosedPathIndex> intersectionLSplittable(
			SplittablePath<LPP> lhs) {
		return (IIntersections<LPP, ClosedPathIndex>) 
				actual.intersectionLSplittable(lhs).transform(TupleTransformers.closeRight);
	}

	@Override
	public BestProject<ClosedPathIndex> project(double best, Vec p) {
		return actual.project(best,p).transform(PITransformers.closedT);
	}

	@Override
	public <RPP extends PathIndex> BestProjectTup<ClosedPathIndex, RPP> project(
			double best, Path<RPP> other) {
		return (BestProjectTup<ClosedPathIndex, RPP>)
				actual.project(other).transform(TupleTransformers.closeLeft);
	}

	@Override
	public BestProjectTup<SimplePathIndex, ClosedPathIndex> projectLLine(
			double best, Line lhs) {
		return 
				(BestProjectTup<SimplePathIndex, ClosedPathIndex>)
				actual.projectLLine(best,lhs).transform(TupleTransformers.closeRight);
	}
	@Override
	public BestProjectTup<SetIndex, ClosedPathIndex> projectLSet(double best,
			ShapeSet lhs) {
		return 
				(BestProjectTup<SetIndex, ClosedPathIndex>)
				actual.projectLSet(best,lhs).transform(TupleTransformers.closeRight);
	}

	@Override
	public <LPI extends PathIndex> BestProjectTup<LPI, ClosedPathIndex> projectLSplittable(
			double best, SplittablePath<LPI> lhs) {
		return 
				(BestProjectTup<LPI, ClosedPathIndex>)
				actual.projectLSplittable(best,lhs).transform(TupleTransformers.closeRight);
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
	public Path<ClosedPathIndex> transform(AffineTransformation t) {
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
		if(actual.isCyclicBorder(t.next)){
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
	public boolean isCyclicBorder(ClosedPathIndex p) {
		throw new Error("Closedpath has no start or end!");
	}
	
	@Override
	public Path getSegment(ClosedPathIndex p) {
		return this;
	}
	
	@Override
	public void getClosedSegmentsNotInSet(Set<Path> segments, List<Path> res){
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
		Vec outside = getBBox().getLeftUp().add(new Vec(-1,-1));
		return !isInside(outside);
	}
	
	@Override
	public String toString() {
		return String.format("Closed(%s)",actual.toString());
	}
}
