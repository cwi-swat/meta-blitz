package nogbeter.crossing;

import nogbeter.paths.PathIndex;
import nogbeter.points.twod.Vec;

public class Crossing<L extends PathIndex, R extends PathIndex>{

	public final L l;
	public final R r;
	public final Vec loc;
	public boolean leftAfterInside;
	public Crossing(L l, R r, Vec loc, boolean leftAfterInside) {
		this.l = l;
		this.r = r;
		this.loc = loc;
		this.leftAfterInside = leftAfterInside;
	}
	
}
