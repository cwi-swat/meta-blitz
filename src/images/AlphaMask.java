package images;

import nogbeter.points.twod.BBox;
import nogbeter.points.twod.Vec;

public class AlphaMask {

	public int leftUpx, leftUpy;
	public final int width, height;
	public final float[] pixels; // row-first
	
	public AlphaMask(int leftUpx, int leftUpy, int width, int height) {
		this.leftUpx = leftUpx;
		this.leftUpy = leftUpy;
		this.width = width;
		this.height = height;
		pixels = new float[width * height];
	}
	
	private int getIndex(int x, int y){
		return (x - leftUpx) * width + (y - leftUpy);
	}
	
	public void set(int x, int y, float val){
		pixels[getIndex(x,y)] = val;
	}
	
	public float get(int x, int y){
		return pixels[getIndex(x,y)];
	}
	
	public void fill(BBox b){
		int width = (int)b.xInterval.length;
		for(int x = (int)(b.xInterval.low - leftUpx); x < width; x++){
			for(int y = (int)(b.yInterval.low - leftUpy); y < width; y++){
				set(x,y,1.0f);
			}
		}
	}
}
