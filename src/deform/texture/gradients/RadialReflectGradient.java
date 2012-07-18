package deform.texture.gradients;

import java.awt.MultipleGradientPaint.CycleMethod;

import deform.Color;
import deform.Texture;
import deform.Transform;
import deform.Vec;
import deform.Library.ColorAndFraction;
import deform.transform.affine.AffineTransform;

public class RadialReflectGradient extends RadialMultipleGradient{

	public RadialReflectGradient(ColorAndFraction[] cs, AffineTransform aff) {
		super(cs, aff);
	}

	public RadialReflectGradient(double[] fractions, Color[] colors,
			AffineTransform aff) {
		super(fractions,colors,aff);
	}

	
	@Override
	public Color sample(Vec point) {
		double d = aff.from(point).norm();
		if((int)d % 2 == 0){
			return getColor(d - Math.floor(d));
		} else {
			return getColor(1.0 -(d - Math.floor(d)));
		}
	}

	@Override
	public Texture transform(Transform t) {
		AffineTransform aff2 = (AffineTransform)t;
		return new RadialReflectGradient(fractions,colors,(AffineTransform)aff2.compose(aff));
	}

	@Override
	CycleMethod getCycleMethod() {
		return CycleMethod.REFLECT;
	}
}
