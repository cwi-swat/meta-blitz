package nogbeter.paths.simple;

import java.util.Collections;
import java.util.List;

import bezier.util.STuple;
import bezier.util.Tuple;
import nogbeter.paths.BestProject;
import nogbeter.paths.ConnectedPath;
import nogbeter.paths.simple.lines.VerticalLine;
import nogbeter.util.Interval;

public abstract class SimplePath extends ConnectedPath{
	public final Interval tInterval;
	
	public SimplePath(Interval tInterval) {
		this.tInterval = tInterval;
	}
	
	public abstract STuple<SimplePath> splitSimpler();

	

	protected Tuple<List<Double>, List<Double>> makeIntersectionResult(
			SimplePath lhs, double tl, double tr) {
		return new Tuple<List<Double>, List<Double>>(
				Collections.singletonList(lhs.tInterval.getAtFactor(tl)),
				Collections.singletonList(tInterval.getAtFactor(tr)));
	}
	
	public BestProject<Tuple<Double, Double>> makeBestProject(double dist,SimplePath lhs,
			double tl, double tr) {
		return new BestProject<Tuple<Double,Double>>(dist,
				new Tuple<Double, Double>(
						lhs.tInterval.getAtFactor(tl), 
						tInterval.getAtFactor(tr)));
	}
}
