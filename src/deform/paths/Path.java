package deform.paths;

import paths.paths.paths.QueryPath;
import deform.BBox;
import deform.Transform;
import deform.segments.SegPath;
import deform.segments.SegmentsMaker;
import deform.transform.affine.AffineTransform;
import deform.transform.affine.IdentityTransform;

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
		if(t instanceof AffineTransform){
			renderAffine(t,res);
		} else {
			renderNonAffine(t, res);
		}
	}
	abstract void renderAffine(Transform t, SegmentsMaker res);
	abstract void renderNonAffine(Transform t, SegmentsMaker res);
	
	
	public abstract QueryPath toQueryPath();
	
	public Path normalise(){
		SegmentsMaker res = new SegmentsMaker();
		render(IdentityTransform.Instance,res);
		return res.done().toPath();
	}
}
