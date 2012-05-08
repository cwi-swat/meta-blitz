package nogbeter.crossing;

import nogbeter.paths.PathIndex;
import nogbeter.paths.results.intersections.Intersection;

public class LineStateBeforeAndAfter {
	public final boolean leftBeforeInside;
	public final boolean leftAfterInside;
	
	public LineStateBeforeAndAfter(boolean leftBeforeInside,
			boolean leftAfterInside) {
		this.leftBeforeInside = leftBeforeInside;
		this.leftAfterInside = leftAfterInside;
	}


	public <L extends PathIndex,R extends PathIndex> 
	Crossing<L, R> toCrossing(Intersection<L,R> i){
		if(leftBeforeInside != leftAfterInside){
			return new Crossing<L, R>(i.left,i.right, i.loc, leftAfterInside);
		} else {
			return null;
		}
		
	}
}
