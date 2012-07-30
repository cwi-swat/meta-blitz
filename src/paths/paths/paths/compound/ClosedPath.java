package paths.paths.paths.compound;

import deform.BBox;
import deform.Vec;
import paths.paths.paths.QueryPath;
import paths.paths.paths.PathIndex;
import paths.paths.paths.SplittablePath;
import paths.paths.paths.simple.Line;
import paths.paths.results.intersections.IIntersections;
import paths.paths.results.project.BestProject;
import paths.paths.results.project.BestProjectTup;
import paths.paths.results.transformers.IPathIndexTransformer;
import paths.paths.results.transformers.PITransformers;
import paths.paths.results.transformers.PathIndexTupleTransformer;
import paths.paths.results.transformers.TupleTransformers;
import transform.nonlinear.ILineTransformer;
import util.Tuple;

public class ClosedPath extends QueryPath {

	final QueryPath actual;
	final PathIndexTupleTransformer closeLeft, closeRight;
	final IPathIndexTransformer closeTransformer;
	final PathIndex minPathIndex, maxPathIndex;

	public ClosedPath(QueryPath actual) {
		this.actual = actual;
		this.minPathIndex = actual.minPathIndex();
		this.maxPathIndex = actual.maxPathIndex();
		this.closeTransformer = PITransformers.closedT(minPathIndex,
				maxPathIndex);
		this.closeLeft = TupleTransformers.left(closeTransformer);
		this.closeRight = TupleTransformers.right(closeTransformer);
	}

	@Override
	public BBox makeBBox() {
		return actual.makeBBox();
	}

	@Override
	public Vec getAt(PathIndex t) {
		return actual.getAt(t.next);
	}

	@Override
	public Vec getTangentAt(PathIndex t) {
		return actual.getTangentAt(t.next);
	}

	@Override
	public IIntersections intersection(QueryPath other) {
		return actual.intersection(other).transform(closeLeft);
	}

	@Override
	public IIntersections intersectionLLine(Line lhs) {
		return actual.intersectionLLine(lhs).transform(closeRight);
	}

	@Override
	public IIntersections intersectionLSet(ShapeSet lhs) {
		return actual.intersectionLSet(lhs).transform(closeRight);
	}

	@Override
	public IIntersections intersectionLSplittable(SplittablePath lhs) {
		return actual.intersectionLSplittable(lhs).transform(closeRight);
	}

	@Override
	public BestProject project(double best, Vec p) {
		return actual.project(best, p).transform(closeTransformer);
	}

	@Override
	public BestProjectTup project(double best, QueryPath other) {
		return actual.project(other).transform(closeLeft);
	}

	@Override
	public BestProjectTup projectLLine(double best, Line lhs) {
		return actual.projectLLine(best, lhs).transform(closeRight);
	}

	@Override
	public BestProjectTup projectLSet(double best, ShapeSet lhs) {
		return actual.projectLSet(best, lhs).transform(closeRight);
	}

	@Override
	public BestProjectTup projectLSplittable(double best, SplittablePath lhs) {
		return actual.projectLSplittable(best, lhs).transform(closeRight);
	}

	@Override
	public int nrChildren() {
		return 1;
	}

	@Override
	public QueryPath getChild(int i) {
		return actual;
	}

	@Override
	public Tuple<QueryPath, Double> normaliseToLength(double prevLength) {
		Tuple<QueryPath, Double> resDeep = actual.normaliseToLength(prevLength);
		return new Tuple<QueryPath, Double>(new ClosedPath(resDeep.l), resDeep.r);
	}

	@Override
	public String toString() {
		return String.format("Closed(%s)", actual.toString());
	}

	@Override
	public PathIndex minPathIndex() {
		return new ClosedPathIndex(minPathIndex);
	}

	@Override
	public PathIndex maxPathIndex() {
		return new ClosedPathIndex(maxPathIndex);
	}

}
