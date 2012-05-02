package nogbeter.paths.results.project;

import nogbeter.paths.PathIndex;
import nogbeter.paths.results.intersections.Intersection;
import nogbeter.paths.results.transformers.IPathIndexTransformer;
import nogbeter.paths.results.transformers.PathIndexTupleTransformer;




public class BestProject<A extends PathIndex>{

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
	
	public<LPI extends PathIndex>
	BestProject<LPI> transform(IPathIndexTransformer<LPI> trans){
		if(trans.doesNothing()){
			return (BestProject<LPI>)this;
		} else {
			return new BestProject<LPI>(distSquared, trans.transform(t));
		}
}
	
	
}