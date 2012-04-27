package nogbeter.paths;

import bezier.util.Tuple;



public class BestProject<A>{

	public static final BestProject noBestYet = new BestProject();
	
	public final double distSquared;
	public final A t;
	
	public BestProject(){
		this.distSquared = Double.POSITIVE_INFINITY;
		this.t = null;
	}
	
	public BestProject(double distSquared, A t) {
		this.distSquared = distSquared;
		this.t = t;
	}
	
	public BestProject(double bestDistSqrd) {
		this.distSquared = bestDistSqrd;
		this.t = null;
	}

	public BestProject<A> choose(BestProject<A> rhs){
		if(distSquared < rhs.distSquared){
			return rhs;
		} else {
			return this;
		}
	}
	
	
}