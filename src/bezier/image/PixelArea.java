package bezier.image;

import nogbeter.points.twod.Vec;

public class PixelArea {

	public final int x, y, width, height;

	public PixelArea(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
	public PixelArea(Vec ... vecs) {
		double minx = Double.POSITIVE_INFINITY, miny = Double.POSITIVE_INFINITY,
				maxx = Double.NEGATIVE_INFINITY, maxy = Double.NEGATIVE_INFINITY;
		for(Vec v : vecs){
			minx = Math.min(v.x, minx);
			miny = Math.min(v.y, miny);
			maxx = Math.max(v.x, maxx);
			maxy = Math.max(v.y, maxy);
		}
		this.x = (int)minx; this.y = (int)miny;
		this.width = (int)Math.ceil(maxx) - this.x;
		this.height = (int)Math.ceil(maxy) - this.y;
	}
	
	public static PixelArea merge(PixelArea ...areas){
		int minx = Integer.MAX_VALUE, miny = Integer.MAX_VALUE,
				maxxr = Integer.MIN_VALUE, maxyd = Integer.MIN_VALUE;
		boolean any = false;
		for(PixelArea a : areas){
			if(a == null) continue;
			minx = Math.min(minx,a.x);
			miny = Math.min(miny,a.y);
			maxxr = Math.max(maxxr,a.x + a.width);
			maxyd = Math.max(maxyd,a.y + a.height);
			any = true;
		}
		if(!any){
			return null;
		} else {
			return new PixelArea(minx, miny, maxxr - minx, maxyd - miny);
		}
		
	}
	
	
	
}
