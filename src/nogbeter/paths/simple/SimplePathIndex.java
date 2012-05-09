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
			throw new Error("Comparing incomparable pathindexes!" + this + " " + o);
		}
	}

	@Override
	public double getSimple() {
		return t;
	}
	
}
