package deform.texture.gradients;

import java.awt.MultipleGradientPaint.CycleMethod;

import deform.Color;
import deform.Texture;
import deform.Transform;
import deform.Vec;
import deform.Library.ColorAndFraction;
import deform.transform.affine.AffineTransform;

public class RadialCyclicGradient extends RadialMultipleGradient {

	public RadialCyclicGradient(ColorAndFraction[] cs, AffineTransform aff) {
		super(cs, aff);
	}

	public RadialCyclicGradient(double[] fractions, Color[] colors,
			AffineTransform aff) {
		super(fractions,colors,aff);
	}

	
	@Override
	public Color sample(Vec point) {
		double d = aff.from(point).norm();
		return getColor(d - (int)d);
	}

	@Override
	public Texture transform(Transform t) {
		AffineTransform aff2 = (AffineTransform)t;
		return new RadialCyclicGradient(fractions,colors,(AffineTransform)aff2.compose(aff));
	}

	@Override
	CycleMethod getCycleMethod() {
		return CycleMethod.REPEAT;
	}

}
