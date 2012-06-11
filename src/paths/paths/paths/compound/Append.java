package paths.paths.paths.compound;

import java.util.List;

import paths.paths.paths.Path;
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

	public Append(Path left, Path right) {
		super(left, right);
		borderLeft = new AppendIndex(SplitChoice.Left, left.maxPathIndex());
		borderRight = new AppendIndex(SplitChoice.Right, right.minPathIndex());
	}

	private static Path createAppend(List<Path> paths, int start, int end) {
		if (start == end - 1) {
			return paths.get(start);
		} else {
			int mid = (start + end) / 2;
			return new Append(createAppend(paths, start, mid), createAppend(
					paths, mid, end));
		}
	}

	public static Path createAppends(List<Path> paths) {
		return createAppend(paths, 0, paths.size());
	}

	@Override
	public Append transform(IToTransform t) {
		return new Append(left.transform(t), right.transform(t));
	}

	@Override
	public String toString() {
		return String.format("(%s + %s)", left, right);
	}

	@Override
	public Tuple<Path, Double> normaliseToLength(double prevLength) {
		Tuple<Path, Double> ln = left.normaliseToLength(prevLength);
		Tuple<Path, Double> rn = right.normaliseToLength(ln.r);
		return new Tuple<Path, Double>(new Append(ln.l, rn.l), rn.r);
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

	@Override
	public Append deformActual(IDeform p) {
		return new Append(left.deform(p), right.deform(p));
	}

	@Override
	public Path transformApproxLines(ILineTransformer t) {
		return new Append(left.transformApproxLines(t),
				right.transformApproxLines(t));
	}

}
