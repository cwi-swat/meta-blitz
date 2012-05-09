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


	public boolean isBorder(){
		
		PathIndex cur = this;
		SplitChoice cchoice= choice;
		cur = cur.next;
		while(cur instanceof AppendIndex && ((AppendIndex)cur).choice != cchoice){
			cur = cur.next;
		}
		if(cur instanceof SimplePathIndex){
			if(cchoice == SplitChoice.Left){
				return ((SimplePathIndex)cur).t == 1;
			} else {
				return ((SimplePathIndex)cur).t == 0;
			}
		} else {
			return false;
		}
	}

	public boolean isCyclicBorder() {
		PathIndex cur = this;
		SplitChoice cchoice= choice;
		while(cur instanceof AppendIndex && ((AppendIndex)cur).choice == cchoice){
			cur = cur.next;
		}
		if(cur instanceof SimplePathIndex){
			if(cchoice == SplitChoice.Left){
				return ((SimplePathIndex)cur).t == 0;
			} else {
				return ((SimplePathIndex)cur).t == 1;
			}
		} else {
			return false;
		}
	}
	
}
