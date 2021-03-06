package deform.render;


import deform.BBox;
import deform.Color;
import deform.Vec;

public class ScanLiner{
	
	final BBox whole, part;
	int endOfLineInc,  curLine, width, height, y, endOfLineIncFill;
	public int cur, curFill;
	int xReal, yReal;
	
	public ScanLiner(BBox whole, BBox part) {
		this.whole = whole;
		this.part = part;
		int wholeWidth = whole.getWidthInt();
		this.width = part.getWidthInt();
		this.height = part.getHeightInt();
		curLine = 0;
		y = 0;
		xReal = part.getXInt();
		yReal = part.getYInt();
		curFill =  (wholeWidth * (part.getYInt() - whole.getYInt()) 
				+ (part.getXInt() - whole.getXInt()));
		cur = curFill* Color.SampleSize;
		endOfLineIncFill = (whole.getWidthInt() - part.getWidthInt()) + 1;
		endOfLineInc = endOfLineIncFill * Color.SampleSize;
	}
	
	public int getRealX(){
		return xReal;
	}
	
	public int getRealY(){
		return yReal;
	}
	
	public Vec getLoc(){
		return new Vec(xReal,yReal);
	}
	
	public void increment(){
		if(curLine == width - 1){
			cur+=endOfLineInc;
			curFill+=endOfLineIncFill;
			curLine = 0;
			xReal = part.getXInt();
			yReal++;
			y++;
		} else {
			cur+=Color.SampleSize;
			xReal++;
			curFill++;
			curLine++;
		}
	}
	
	public boolean isDone(){
		return y == height;
	}
}