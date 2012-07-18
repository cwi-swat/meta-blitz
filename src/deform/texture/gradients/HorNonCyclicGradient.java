package deform.texture.gradients;

import deform.Color;
import deform.Texture;
import deform.Transform;
import deform.Vec;

public class HorNonCyclicGradient extends HorGradient {

	public HorNonCyclicGradient(Color ca, Color cb, Transform aff) {
		super(ca, cb, aff);
	}


	@Override
	public Color sample(Vec point) {
		point = aff.from(point);
		if(point.x >= 0 && point.x < 1.0){
			return ca.lerp(point.x,cb);
		}
		return Color.transparent;
	}

	@Override
	public Texture transform(Transform t) {
		return new HorNonCyclicGradient(ca,cb,t.compose(aff));
	}

	@Override
	boolean isCyclic() {
		return false;
	}

}
