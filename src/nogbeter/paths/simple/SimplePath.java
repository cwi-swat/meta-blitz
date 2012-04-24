package nogbeter.paths.simple;

import java.util.Collections;
import java.util.List;

import bezier.util.STuple;
import bezier.util.Tuple;
import nogbeter.paths.BestProject;
import nogbeter.paths.ConnectedPath;
import nogbeter.paths.simple.lines.VerticalLine;
import nogbeter.util.InclusiveInterval;

public abstract class SimplePath extends ConnectedPath{
	public final InclusiveInterval tInterval;
	
	public SimplePath(InclusiveInterval tInterval) {
		this.tInterval = tInterval;
	}
	
	public abstract STuple<SimplePath> splitSimpler();

	

	protected Tuple<List<Double>, List<Double>> makeIntersectionResult(
			SimplePath lhs, double tl, double tr) {
		return new Tuple<List<Double>, List<Double>>(
				Collections.singletonList(lhs.tInterval.getAtFactor(tl)),
				Collections.singletonList(tInterval.getAtFactor(tr)));
	}
	
	protected void updateBestProject(BestProject<Double> best, double distSquared, double t){
		if(best.distSquared > distSquared){
			best.distSquared = distSquared;
			best.t = tInterval.getAtFactor(t);
		}
	}
}
