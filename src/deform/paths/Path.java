package deform.paths;

import deform.BBox;
import deform.Transform;
import deform.segments.SegPath;
import deform.segments.SegmentsMaker;

public abstract class Path {

	public final BBox bbox;
	
	public Path(BBox bbox) {
		this.bbox = bbox;
	}

	public SegPath render(Transform t){
		SegmentsMaker res = new SegmentsMaker();
		render(t, res);
		return res.done();
	}
	
	void render(Transform t, SegmentsMaker res){
		if(t.isAffine()){
			renderAffine(t,res);
		} else {
			renderNonAffine(t, res);
		}
	}
	abstract void renderAffine(Transform t, SegmentsMaker res);
	abstract void renderNonAffine(Transform t, SegmentsMaker res);
	
}
