package paths.paths.paths;

import static paths.paths.results.transformers.TupleTransformers.unitTup;
import paths.paths.paths.compound.AppendIndex;
import paths.paths.paths.simple.SimplePathIndex;
import paths.paths.results.transformers.IPathIndexTransformer;
import paths.paths.results.transformers.PITransformers;
import paths.paths.results.transformers.PathIndexTupleTransformer;
import paths.points.angles.AngularInterval;
import paths.points.oned.Interval;
import paths.points.twod.Vec;
import transform.IToTransform;

public abstract class SimplyIndexedPath extends SplittablePath<SimplePathIndex>{

	public Interval tInterval;
	
	public SimplyIndexedPath(Interval tInterval) {
		this.tInterval=tInterval;
	}
	
	public abstract Vec getAtSimply(double t);
	
	public abstract Vec getTangentAtSimply(double t);
	
	@Override
	public Vec getAt(SimplePathIndex p){
		return getAtSimply(p.t);
	}
	
	@Override
	public Vec getTangentAt(SimplePathIndex p){
		return getTangentAtSimply(p.t);
	}
	
	@Override
	public IPathIndexTransformer<SimplePathIndex> getLeftTransformer() {
		return PITransformers.unit;
	}

	@Override
	public IPathIndexTransformer<SimplePathIndex> getRightTransformer() {
		return PITransformers.unit;
	}
	
	@Override
	public <B extends PathIndex> PathIndexTupleTransformer<SimplePathIndex, B> getLeftLeftTransformer() {
		return unitTup;
	}

	@Override
	public <B extends PathIndex> PathIndexTupleTransformer<SimplePathIndex, B> getLeftRightTransformer() {
		return unitTup;
	}

	@Override
	public <B extends PathIndex> PathIndexTupleTransformer<B,SimplePathIndex> getRightLeftTransformer() {
		return unitTup;
	}

	@Override
	public <B extends PathIndex> PathIndexTupleTransformer<B,SimplePathIndex> getRightRightTransformer() {
		return unitTup;
	}
	
	public abstract SimplyIndexedPath transform(IToTransform t);
	
	public abstract SimplyIndexedPath getWithAdjustedStartPoint(Vec newStartPoint);
	
	@Override
	public Path getClosedPath(SimplePathIndex p) {
		return this;
	}
	
	public SimplePathIndex minPathIndex(){ return new SimplePathIndex(tInterval.low); }
	public SimplePathIndex maxPathIndex(){ return new SimplePathIndex(tInterval.high); }
	
	
}
