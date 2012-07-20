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
	
	public BBox transformBBox(BBox b){
		return l.transformBBox(r.transformBBox(b));
	}

	@Override
	public Vec to(Vec from) {
		return l.to(r.to(from));
	}

	@Override
	public Vec from(Vec to) {
		return r.from(l.from(to));
	}

	@Override
	public String toString() {
		return "CompositeTransform [l=" + l + ", r=" + r + "]";
	}
	
	
}
