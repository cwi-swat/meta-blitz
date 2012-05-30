package nogbeter.paths.compound;

import nogbeter.paths.PathIndex;
import nogbeter.paths.compound.SplitIndex.SplitChoice;
import nogbeter.paths.simple.SimplePathIndex;

public class AppendIndex extends SplitIndex {
	


	public AppendIndex(SplitChoice choice, PathIndex next) {
		super(choice,next);
	}
	
	public String toString(){
		return "Append("+choice+")" + (next == null? "" : "," + next.toString());
	}

	
}
