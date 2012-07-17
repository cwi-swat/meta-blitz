package deform.transform;

import deform.Transform;
import deform.Vec;



public class IdentityTransform extends Transform {

	public static final IdentityTransform Instance = new IdentityTransform();
	@Override
	public Vec to(Vec from) {
		return from;
	}

	@Override
	public Vec from(Vec to) {
		return to;
	}
	
	public boolean isAffine(){
		return true;
	}
	
	

	public Transform compose(Transform rhs){
		return rhs;
	}

}
