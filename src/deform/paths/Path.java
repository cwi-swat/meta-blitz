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
	QueryPath qpath;
	Path normalized;
	
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
	
	public QueryPath getQueryPath(){
		if(qpath == null){
			qpath = toQueryPath();
		}
		return qpath;
	}
	
	public abstract QueryPath toQueryPath();
	
	public Path normalise(){
		if(normalized == null){
			SegmentsMaker res = new SegmentsMaker();
			render(IdentityTransform.Instance,res);
			normalized =  res.done().toPath();
		}
		return normalized;
	}
}
