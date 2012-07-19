package deform.render;


import deform.BBox;
import deform.Color;

public class ScanLiner{
	
	final BBox whole, part;
	int endOfLineInc,  curLine, width, height, y, endOfLineIncFill;
	public int cur, curFill;
	
	public ScanLiner(BBox whole, BBox part) {
		this.whole = whole;
		this.part = part;
		int wholeWidth = whole.getWidthInt();
		this.width = part.getWidthInt();
		this.height = part.getHeightInt();
		curLine = 0;
		y = 0;
		curFill =  (wholeWidth * (part.getYInt() - whole.getYInt()) 
				+ (part.getXInt() - whole.getXInt()));
		cur = curFill* Color.SampleSize;
		endOfLineIncFill = ((whole.getWidthInt() - part.getWidthInt()) - (part.getXInt() - whole.getXInt()) ) + 1;
		endOfLineInc = endOfLineIncFill * Color.SampleSize;
	}
	
	
	public void increment(){
		if(curLine == width - 1){
			cur+=endOfLineInc;
			curFill+=endOfLineIncFill;
			curLine = 0;
			y++;
		} else {
			cur+=Color.SampleSize;
			curFill++;
			curLine++;
		}
	}
	
	public boolean isDone(){
		return y == height;
	}
}