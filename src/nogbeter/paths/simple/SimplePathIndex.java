package nogbeter.paths.simple;

import nogbeter.paths.PathIndex;


public class SimplePathIndex extends PathIndex {
	public final double t;

	public SimplePathIndex(double t) {
		super(null);
		this.t = t;
	}

	public String toString(){
		return "SimpInd("+t+")";
	}

	@Override
	public int compareTo(PathIndex o) {
		if(o instanceof SimplePathIndex){
			SimplePathIndex pi = (SimplePathIndex)o;
			return Double.compare(t, pi.t);
		} else {
			throw new Error("Comparing incomparable pathindexes!");
		}
	}
	
	@Override
	public boolean isAdjacentOrderRight(PathIndex other){
		if(other instanceof SimplePathIndex){
			return ((SimplePathIndex)other).t < t;
		} else {
			throw new Error("No adjacent order defined!");
		}
	}
}
