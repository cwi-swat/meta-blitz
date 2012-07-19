package paths.paths.paths.compound;

import java.util.List;

import paths.paths.paths.QueryPath;
import paths.paths.paths.PathIndex;
import paths.paths.paths.compound.SplitIndex.SplitChoice;
import paths.paths.results.transformers.IPathIndexTransformer;
import paths.paths.results.transformers.PITransformers;
import paths.paths.results.transformers.PathIndexTupleTransformer;
import paths.paths.results.transformers.TupleTransformers;
import transform.IToTransform;
import transform.nonlinear.IDeform;
import transform.nonlinear.ILineTransformer;
import util.Tuple;

public class Append extends CompoundSplittablePath {

	final PathIndex borderLeft, borderRight;

	public Append(QueryPath left, QueryPath right) {
		super(left, right);
		borderLeft = new AppendIndex(SplitChoice.Left, left.maxPathIndex());
		borderRight = new AppendIndex(SplitChoice.Right, right.minPathIndex());
	}

	private static QueryPath createAppend(List<QueryPath> paths, int start, int end) {
		if (start == end - 1) {
			return paths.get(start);
		} else {
			int mid = (start + end) / 2;
			return new Append(createAppend(paths, start, mid), createAppend(
					paths, mid, end));
		}
	}

	public static QueryPath createAppends(List<QueryPath> paths) {
		return createAppend(paths, 0, paths.size());
	}

	@Override
	public String toString() {
		return String.format("(%s + %s)", left, right);
	}

	@Override
	public Tuple<QueryPath, Double> normaliseToLength(double prevLength) {
		Tuple<QueryPath, Double> ln = left.normaliseToLength(prevLength);
		Tuple<QueryPath, Double> rn = right.normaliseToLength(ln.r);
		return new Tuple<QueryPath, Double>(new Append(ln.l, rn.l), rn.r);
	}

	@Override
	public IPathIndexTransformer getLeftTransformer() {
		return PITransformers.appendLeft;
	}

	@Override
	public IPathIndexTransformer getRightTransformer() {
		return PITransformers.appendRight;
	}

	@Override
	public PathIndexTupleTransformer getLeftLeftTransformer() {
		return TupleTransformers.aleftLeft;
	}

	@Override
	public PathIndexTupleTransformer getLeftRightTransformer() {
		return TupleTransformers.aleftRight;
	}

	@Override
	public PathIndexTupleTransformer getRightLeftTransformer() {
		return TupleTransformers.arightLeft;
	}

	@Override
	public PathIndexTupleTransformer getRightRightTransformer() {
		return TupleTransformers.arightRight;
	}

}
