package nogbeter.paths.simple;

import java.util.Collections;
import java.util.List;

import bezier.points.Vec;
import bezier.util.STuple;
import bezier.util.Tuple;
import nogbeter.paths.BestProject;
import nogbeter.paths.BestProjectTup;
import nogbeter.paths.ConnectedPath;
import nogbeter.paths.simple.lines.VerticalLine;
import nogbeter.util.Interval;

public abstract class SimplePath extends ConnectedPath{

	
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
	
	public Vec getAt(double t){
		return getAtLocal(tInterval.getFactorForPoint(t));
	}
	
	public Vec getTangentAt(double t){
		return getTangentAtLocal(tInterval.getFactorForPoint(t));
	}

	protected Tuple<List<Double>, List<Double>> makeIntersectionResult(
			SimplePath lhs, double tl, double tr) {
		return new Tuple<List<Double>, List<Double>>(
				Collections.singletonList(lhs.tInterval.getAtFactor(tl)),
				Collections.singletonList(tInterval.getAtFactor(tr)));
	}
	
	public BestProjectTup<Double, Double> makeBestProject(double dist,SimplePath lhs,
			double tl, double tr) {
		return new BestProjectTup<Double,Double>(dist,
						lhs.tInterval.getAtFactor(tl), 
						tInterval.getAtFactor(tr));
	}
}
