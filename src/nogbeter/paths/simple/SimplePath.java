package nogbeter.paths.simple;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import bezier.points.Vec;
import bezier.util.STuple;
import bezier.util.Tuple;
import nogbeter.paths.Path;
import nogbeter.paths.PathIndex;
import nogbeter.paths.SplittablePath;
import nogbeter.paths.compound.SplitIndex;
import nogbeter.paths.results.intersections.IIntersections;
import nogbeter.paths.results.intersections.Intersections;
import nogbeter.paths.results.project.BestProject;
import nogbeter.paths.results.project.BestProjectTup;
import nogbeter.paths.results.transformers.IPathIndexTransformer;
import nogbeter.paths.results.transformers.PITransformers;
import nogbeter.paths.simple.lines.VerticalLine;
import nogbeter.util.Interval;

public abstract class SimplePath extends SplittablePath<SimplePathIndex,SimplePath,SimplePath>{

	public final Interval tInterval;
	
	public SimplePath(Interval tInterval) {
		this.tInterval=tInterval;
	}
	
	public Vec getStartPoint(){
		return getAtLocal(0.0);
	}
	
	public Vec getEndPoint(){
		return getAtLocal(1.0);
	}
	
	public abstract Vec getAtLocal(double t);
	public abstract Vec getTangentAtLocal(double t);
	
	public Vec getAt(SimplePathIndex t){
		return getAtLocal(tInterval.getFactorForPoint(t.t));
	}
	
	public Vec getTangentAt(SimplePathIndex t){
		return getTangentAtLocal(tInterval.getFactorForPoint(t.t));
	}
	
	
	
	protected IIntersections<SimplePathIndex,SimplePathIndex> makeIntersectionResult(
			SimplePath lhs, double tl, double tr) {
		SimplePathIndex l = lhs.makeGlobalPathIndexFromLocal(tl);
		SimplePathIndex r = makeGlobalPathIndexFromLocal(tr);
		return new Intersections<SimplePathIndex, SimplePathIndex>(l, r);
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
	
	@Override
	public IPathIndexTransformer<SimplePathIndex> getLeftTransformer() {
		return PITransformers.unit;
	}

	@Override
	public IPathIndexTransformer<SimplePathIndex> getRightTransformer() {
		return PITransformers.unit;
	}
}
