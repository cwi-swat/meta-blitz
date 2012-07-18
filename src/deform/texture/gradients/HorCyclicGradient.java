package deform.texture.gradients;

import deform.Color;
import deform.Texture;
import deform.Transform;
import deform.Vec;

public class HorCyclicGradient extends HorGradient{

	public HorCyclicGradient(Color ca, Color cb, Transform aff) {
		super(ca, cb, aff);
	}

	@Override
	boolean isCyclic() {
		return true;
	}
	

	@Override
	public Color sample(Vec point) {
		point = aff.from(point);
		double x = Math.abs(point.x);
		int e = (int)x;
		if(e % 2 == 0){
			return ca.lerp(x - e,cb);
		} else {
			return cb.lerp(x - e,ca);
		}
	}

	@Override
	public Texture transform(Transform t) {
		return new HorCyclicGradient(ca,cb,t.compose(aff));
	}

}
