package deform.segments;

import deform.Vec;

public class SegmentsMaker {
	SegPath res;
	
	
	
	public SegmentsMaker() {
		res = new SegPath();
	}
	
	public void line(Vec s, Vec e){
		if(res.start == null){
			res.start = s;
		}
		res.segs.add(new LineTo(e));
	}
	
	public void quad(Vec s, Vec c, Vec e){
		if(res.start == null){
			res.start = s;
		}
		res.segs.add(new QuadTo(c,e));
	}
	
	public void cubic(Vec s, Vec cl, Vec cr, Vec e){
		if(res.start == null){
			res.start = s;
		}
		res.segs.add(new CubicTo(cl,cr,e));
	}
	
	public SegPath done(){
		return res;
	}

}
