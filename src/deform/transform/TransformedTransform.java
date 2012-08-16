package deform.transform;

import deform.Transform;
import deform.Vec;


public class TransformedTransform extends Transform {

	final Transform trans, real;
	
	public TransformedTransform(Transform trans, Transform real) {
		this.trans = trans;
		this.real = real;
	}

	@Override
	public Vec to(Vec from) {
		return trans.to(real.to(trans.from(from)));
	}

	@Override
	public Vec from(Vec to) {
		return trans.to(real.from(trans.from(to)));
	}

}
