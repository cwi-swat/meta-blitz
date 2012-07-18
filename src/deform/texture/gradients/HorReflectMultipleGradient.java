package deform.texture.gradients;


import java.awt.MultipleGradientPaint.CycleMethod;

import deform.Color;
import deform.Library.ColorAndFraction;
import deform.Texture;
import deform.Transform;
import deform.Vec;
import deform.transform.affine.AffineTransform;

public class HorReflectMultipleGradient extends HorMultipleGradient{

	public HorReflectMultipleGradient(ColorAndFraction[] cs, AffineTransform aff) {
		super(cs, aff);
	}

	public HorReflectMultipleGradient(double[] fractions, Color[] colors,
			AffineTransform aff) {
		super(fractions,colors,aff);
	}

	@Override
	public Color sample(Vec point) {
		point = aff.from(point);
		double d = Math.abs(point.x);
		int i = (int)d;

		if(i % 2 == 0){
			return getColor(d - Math.floor(d));
		} else {
			return getColor(1.0 -(d - Math.floor(d)));
		}

	}

	@Override
	public Texture transform(Transform t) {
		AffineTransform aff2 = (AffineTransform)t;
		return new HorReflectMultipleGradient(fractions,colors,(AffineTransform)aff2.compose(aff));
	}

	@Override
	CycleMethod getCycleMethod() {
		return CycleMethod.REFLECT;
	}

}
