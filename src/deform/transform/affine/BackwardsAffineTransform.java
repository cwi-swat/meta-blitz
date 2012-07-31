package deform.transform.affine;

import deform.BBox;
import deform.Transform;
import deform.Vec;
import deform.transform.CompositeTransform;

public class BackwardsAffineTransform {
	public static Transform backwards(AffineTransform t){
		if(t instanceof IdentityTransform){
			return t;
		} else {
			return new AffineTransform(t.from, t.to);
		}
	}
}
