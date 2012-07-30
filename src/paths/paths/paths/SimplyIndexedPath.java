package paths.paths.paths;

import static paths.paths.results.transformers.TupleTransformers.unitTup;
import deform.Vec;
import paths.paths.paths.simple.SimplePathIndex;
import paths.paths.results.transformers.IPathIndexTransformer;
import paths.paths.results.transformers.PITransformers;
import paths.paths.results.transformers.PathIndexTupleTransformer;
import paths.points.oned.Interval;
import transform.IToTransform;

public abstract class SimplyIndexedPath extends SplittablePath {

	public Interval tInterval;

	public SimplyIndexedPath(Interval tInterval) {
		this.tInterval = tInterval;
	}

	public abstract Vec getAtSimply(double t);

	public abstract Vec getTangentAtSimply(double t);

	@Override
	public Vec getAt(PathIndex p) {
		return getAtSimply(((SimplePathIndex) p).t);
	}

	@Override
	public Vec getTangentAt(PathIndex p) {
		return getTangentAtSimply(((SimplePathIndex) p).t);
	}

	@Override
	public IPathIndexTransformer getLeftTransformer() {
		return PITransformers.unit;
	}

	@Override
	public IPathIndexTransformer getRightTransformer() {
		return PITransformers.unit;
	}

	@Override
	public PathIndexTupleTransformer getLeftLeftTransformer() {
		return unitTup;
	}

	@Override
	public PathIndexTupleTransformer getLeftRightTransformer() {
		return unitTup;
	}

	@Override
	public PathIndexTupleTransformer getRightLeftTransformer() {
		return unitTup;
	}

	@Override
	public PathIndexTupleTransformer getRightRightTransformer() {
		return unitTup;
	}


	public abstract SimplyIndexedPath getWithAdjustedStartPoint(
			Vec newStartPoint);

	public SimplePathIndex minPathIndex() {
		return new SimplePathIndex(tInterval.low);
	}

	public SimplePathIndex maxPathIndex() {
		return new SimplePathIndex(tInterval.high);
	}

}
