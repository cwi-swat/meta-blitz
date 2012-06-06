package paths.paths.paths.compound;

import paths.paths.paths.PathIndex;

public class SetIndex extends PathIndex {
	
	public final int choice;

	public SetIndex(int choice,PathIndex next) {
		super(next);
		this.choice = choice;
	}
	
	public String toString(){
		return "Set("+choice+")" + (next == null? "" : "," + next.toString());
	}
	
	@Override
	public int compareTo(PathIndex o) {
		if(o instanceof SetIndex){
			SetIndex pi = (SetIndex)o;
			int cmp= Integer.compare(choice, pi.choice);
			if(cmp == 0){
				return this.next.compareTo(o.next);
			} else {
				return cmp;
			}
		} else {
			throw new Error("Comparing incomparable pathindexes!");
		}
	}

	
	@Override
	public double getSimple() {
		return next.getSimple();
	}
	
}
