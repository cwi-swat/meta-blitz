package nogbeter.paths.compound;

import nogbeter.paths.PathIndex;

public class ClosedPathIndex extends PathIndex{

	public ClosedPathIndex(PathIndex next) {
		super(next);
	}

	@Override
	public int compareTo(PathIndex o) {
		if(o instanceof ClosedPathIndex){
			ClosedPathIndex pi = (ClosedPathIndex)o;
			return this.next.compareTo(o.next);
		} else {
			throw new Error("Comparing incomparable pathindexes!");
		}
	}

	@Override
	public double getSimple() {
		return next.getSimple();
	}
	
	public String toString(){
		return "Closed";
	}

}
