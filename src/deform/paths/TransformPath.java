package deform.paths;

import deform.BBox;
import deform.Transform;
import deform.segments.SegmentsMaker;

public class TransformPath extends Path{
	final Path p;
	final Transform t;
	
	public TransformPath(Path p, Transform t) {
		super(t.transformBBox(p.bbox));
		this.p = p;
		this.t = t;
	}

	

	@Override
	void renderAffine(Transform t, SegmentsMaker res) {
		p.render(t.compose(this.t), res);
		
	}

	@Override
	void renderNonAffine(Transform t, SegmentsMaker res) {
		p.render(t.compose(this.t), res);
	}
	
	
}
