package deform.transform;

import deform.Transform;
import deform.Vec;

public class Translate extends Transform{
	
	final Vec trans;

	public Translate(Vec trans) {
		this.trans = trans;
	}

	@Override
	public Vec to(Vec from) {
		return from.add(trans);
	}

	@Override
	public Vec from(Vec to) {
		return to.sub(trans);
	}
	
	public boolean isAffine(){
		return true;
	}

}
