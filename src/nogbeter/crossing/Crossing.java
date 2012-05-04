package nogbeter.crossing;

import nogbeter.paths.PathIndex;
import nogbeter.points.twod.Vec;

public class Crossing<L extends PathIndex, R extends PathIndex>{

	public final L l;
	public final R r;
	public final Vec loc;
	public final CrossingType type;
	
	public Crossing(L l, R r, Vec loc, CrossingType type) {
		this.l = l;
		this.r = r;
		this.loc = loc;
		this.type = type;
	}
	
	public String toString(){
		return type.toString() +  loc ;
	}
	
	
}
