package nogbeter.paths.simple;

import static nogbeter.paths.results.transformers.TupleTransformers.unitTup;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import bezier.util.Tuple;
import nogbeter.paths.Path;
import nogbeter.paths.PathIndex;
import nogbeter.paths.SimplyIndexedPath;
import nogbeter.paths.SplittablePath;
import nogbeter.paths.results.intersections.IIntersections;
import nogbeter.paths.results.intersections.Intersections;
import nogbeter.paths.results.project.BestProjectTup;
import nogbeter.paths.results.transformers.IPathIndexTransformer;
import nogbeter.paths.results.transformers.PITransformers;
import nogbeter.paths.results.transformers.PathIndexTupleTransformer;
import nogbeter.points.angles.AngularInterval;
import nogbeter.points.angles.AngularIntervalFactory;
import nogbeter.points.oned.Interval;
import nogbeter.points.twod.Vec;

public abstract class SimplePath extends SimplyIndexedPath{


	public SimplePath(Interval tInterval) {
		super(tInterval);
	}

	public Vec getStartPoint(){
		return getAtLocal(0.0);
	}
	
	public Vec getEndPoint(){
		return getAtLocal(1.0);
	}
	
	public abstract Vec getAtLocal(double t);
	public abstract Vec getTangentAtLocal(double t);
	
	@Override
	public Vec getAtSimply(double t){
		return getAtLocal(tInterval.getFactorForPoint(t));
	}
	
	@Override
	public Vec getTangentAtSimply(double t){
		return getTangentAtLocal(tInterval.getFactorForPoint(t));
	}
	
	
	
	protected IIntersections<SimplePathIndex,SimplePathIndex> makeIntersectionResult(
			SimplePath lhs, double tl, double tr) {
		SimplePathIndex l = lhs.makeGlobalPathIndexFromLocal(tl);
		SimplePathIndex r = makeGlobalPathIndexFromLocal(tr);
		return new Intersections<SimplePathIndex, SimplePathIndex>(l, r,
				lhs.getAtLocal(tl), getAtLocal(tr), lhs.getTangentAtLocal(tl),getTangentAtLocal(tr));
	}
	
	public SimplePathIndex makeGlobalPathIndexFromLocal(double t){
		return new SimplePathIndex(tInterval.getAtFactor(t));
	}
	
	public BestProjectTup<SimplePathIndex, SimplePathIndex> makeBestProject(double dist,SimplePath lhs,
			double tl, double tr) {
		return new BestProjectTup<SimplePathIndex,SimplePathIndex>(dist,
						lhs.makeGlobalPathIndexFromLocal(tl), 
						makeGlobalPathIndexFromLocal(tr));
	}
	
	public int nrChildren(){
		return 0;
	}
	
	public Path getChild(int i){
		return null;
	}

	public abstract int awtCurSeg(float[] coords) ;
	
	public abstract Tuple<Path<SimplePathIndex>,Double> normaliseToLength(double prevLength);

	
	@Override
	public AngularInterval getAngularInsideInterval(SimplePathIndex t) {
		return AngularIntervalFactory.create180DegreesInterval(getTangentAt(t));
	}
	
	@Override
	public Vec getStartTan() {
		return getTangentAtLocal(0.0);
	}

	@Override
	public Vec getEndTan() {
		return getTangentAtLocal(1.0);
	}
	
	public Vec getArbPoint(){ return getStartPoint();}
	public Vec getArbPointTan(){ return getStartTan(); }

	@Override
	public List<Vec> getTangents(SimplePathIndex t) {
		List<Vec> v = new ArrayList<Vec>();
		v.add(getTangentAt(t));
		return v;
	}
	
}
