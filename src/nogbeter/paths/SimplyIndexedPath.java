package nogbeter.paths;

import static nogbeter.paths.results.transformers.TupleTransformers.unitTup;
import nogbeter.paths.compound.AppendIndex;
import nogbeter.paths.results.transformers.IPathIndexTransformer;
import nogbeter.paths.results.transformers.PITransformers;
import nogbeter.paths.results.transformers.PathIndexTupleTransformer;
import nogbeter.paths.simple.SimplePathIndex;
import nogbeter.points.angles.AngularInterval;
import nogbeter.points.oned.Interval;
import nogbeter.points.twod.Vec;
import nogbeter.transform.AffineTransformation;

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
	
	public abstract SimplyIndexedPath transform(AffineTransformation t);
	
	public abstract SimplyIndexedPath getWithAdjustedStartPoint(Vec newStartPoint);
	
	@Override
	public Path getSegment(SimplePathIndex p) {
		return this;
	}
	
	public SimplePathIndex minPathIndex(){ return new SimplePathIndex(tInterval.low); }
	public SimplePathIndex maxPathIndex(){ return new SimplePathIndex(tInterval.high); }
	
	
}
