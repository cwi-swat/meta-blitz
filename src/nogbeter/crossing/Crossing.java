package nogbeter.crossing;

import nogbeter.paths.PathIndex;
import nogbeter.points.twod.Vec;

public class Crossing{

	public final PathIndex l;
	public final PathIndex r;
	public final Vec loc;
	public final CrossingType type;
	
	public Crossing(PathIndex l, PathIndex r, Vec loc, CrossingType type) {
		this.l = l;
		this.r = r;
		this.loc = loc;
		this.type = type;
	}
	
	
}
