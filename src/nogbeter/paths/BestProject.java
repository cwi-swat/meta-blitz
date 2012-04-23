package nogbeter.paths;



public class BestProject<A>{
	
	public static final BestProject noResult = new BestProject(Double.MAX_VALUE,null);
	
	public final double distSquared;
	public final A t;
	
	public BestProject(double distSquared, A t) {
		this.distSquared = distSquared;
		this.t = t;
	}
	
	public BestProject<A> merge(BestProject<A> other){
		if(other.distSquared < distSquared){
			return other;
		} else {
			return this;
		}
	}
}