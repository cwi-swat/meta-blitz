package nogbeter.crossing;

import nogbeter.paths.PathIndex;

public class SetOperations<L extends PathIndex, R extends PathIndex> {
	
	final Crossings<L,R> crossSortLeft;
	final L l; R r;

	public SetOperations(Crossings<L, R> crossSortLeft, L l, R r) {
		this.crossSortLeft = crossSortLeft;
		this.l = l;
		this.r = r;
	}
	
	Path<PathIndex> merge(boolean leftInside, boolean rightInside){
		
	}

}
