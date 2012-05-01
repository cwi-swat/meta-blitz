package nogbeter.paths.simple;

import java.util.Collections;
import java.util.List;

import bezier.points.Vec;
import bezier.util.STuple;
import bezier.util.Tuple;
import nogbeter.paths.BestProject;
import nogbeter.paths.BestProjectTup;
import nogbeter.paths.IConnectedPath;
import nogbeter.paths.Path;
import nogbeter.paths.PathIndex;
import nogbeter.paths.simple.lines.VerticalLine;
import nogbeter.util.Interval;

public abstract class SimplePath extends Path<SimplePathIndex,SimplePath,SimplePath> implements IConnectedPath<SimplePath>{

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
	
	
	
	protected Tuple<List<SimplePathIndex>, List<SimplePathIndex>> makeIntersectionResult(
			SimplePath lhs, double tl, double tr) {
		return new Tuple<List<SimplePathIndex>, List<SimplePathIndex>>(
				Collections.singletonList(lhs.makeGlobalPathIndexFromLocal(tl)),
				Collections.singletonList(makeGlobalPathIndexFromLocal(tr)));
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
}
