package deform.transform.affine;

import deform.BBox;
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
		return new java.awt.geom.AffineTransform();
	}
	
	public Transform compose(Transform rhs){
		return rhs;
	}

	public boolean isTranslation() {
		return true;
	}
	
	public boolean isAffine(BBox b){
		return true;
	}
}
