package deform.transform.affine;

import deform.Transform;
import deform.Vec;



public class IdentityTransform extends AffineTransform {

	IdentityTransform() {
		super(null,null);
	}



	public static final IdentityTransform Instance = new IdentityTransform();
	@Override
	public Vec to(Vec from) {
		return from;
	}

	@Override
	public Vec from(Vec to) {
		return to;
	}
	

	public java.awt.geom.AffineTransform toJava2DTransform(){
		return null;
	}
	
	public Transform compose(Transform rhs){
		return rhs;
	}

}
