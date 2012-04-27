package nogbeter.paths.compound;

import nogbeter.paths.PathIndex;


public class CompoundSplitIndex extends PathIndex {
	
	public static enum SplitChoice{
		Left,Right;
	}
	
	public final SplitChoice choice;

	public CompoundSplitIndex(SplitChoice choice, PathIndex next) {
		super(next);
		this.choice = choice;
	}
}
