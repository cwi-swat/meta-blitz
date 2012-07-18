package deform.transform;

import deform.BBox;
import deform.Transform;
import deform.Vec;

public class CompositeTransform extends Transform {

	final Transform l, r;
	
	public CompositeTransform(Transform l, Transform r) {
		this.l = l;
		this.r = r;
	}

	@Override
	public Vec to(Vec from) {
		return r.to(l.to(from));
	}

	@Override
	public Vec from(Vec to) {
		return l.from(r.from(to));
	}
	
	
}
