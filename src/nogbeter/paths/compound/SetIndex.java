package nogbeter.paths.compound;

import nogbeter.paths.PathIndex;

public class SetIndex extends PathIndex {
	
	public final int choice;

	public SetIndex(int choice,PathIndex next) {
		super(next);
		this.choice = choice;
	}
	
	public String toString(){
		return "Set("+choice+")" + (next == null? "" : "," + next.toString());
	}

}
