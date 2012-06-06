package paths.paths.paths.compound;

import paths.paths.paths.PathIndex;
import paths.paths.paths.compound.SplitIndex.SplitChoice;
import paths.paths.paths.simple.SimplePathIndex;

public class AppendIndex extends SplitIndex {
	


	public AppendIndex(SplitChoice choice, PathIndex next) {
		super(choice,next);
	}
	
	public String toString(){
		return "Append("+choice+")" + (next == null? "" : "," + next.toString());
	}

	
}
