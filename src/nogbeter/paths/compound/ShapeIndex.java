package nogbeter.paths.compound;

import nogbeter.paths.PathIndex;

public class ShapeIndex extends SplitIndex{

	public ShapeIndex(SplitChoice choice, PathIndex next) {
		super(choice, next);
	}
	
	public String choiceString(){
		switch(choice){
		case Left: return "Border";
		case Right: return "Inner";
		}
		throw new Error("Unknown choice "+ this);
	}
	
	public String toString(){
		return "Shape("+choiceString()+")" + (next == null? "" : "," + next.toString());
	}
	

}
