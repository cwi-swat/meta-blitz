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
	
}
