package nogbeter.paths.compound;

import nogbeter.paths.PathIndex;


public class SplitIndex extends PathIndex {
	
	
	public static enum SplitChoice{
		Left,Right;
	}

	public final SplitChoice choice;

	public SplitIndex(SplitChoice choice, PathIndex next) {
		super(next);
		this.choice = choice;
	}
	
	public String toString(){
		return "Split("+choice+")" + (next == null? "" : "," + next.toString());
	}

}
