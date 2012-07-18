package deform.texture.gradients;



import java.awt.MultipleGradientPaint.CycleMethod;

import deform.Color;
import deform.Library.ColorAndFraction;
import deform.Texture;
import deform.Transform;
import deform.Vec;
import deform.transform.affine.AffineTransform;

public class HorCycleMultipleGradient extends HorMultipleGradient{

	public HorCycleMultipleGradient(ColorAndFraction[] cs, AffineTransform aff) {
		super(cs, aff);
	}

	public HorCycleMultipleGradient(double[] fractions, Color[] colors,
			AffineTransform aff) {
		super(fractions,colors,aff);
	}

	@Override
	public Color sample(Vec point) {
		point = aff.from(point);
		return getColor(point.x - (int)point.x);
	}

	@Override
	public Texture transform(Transform t) {
		AffineTransform aff2 = (AffineTransform)t;
		return new HorCycleMultipleGradient(fractions,colors,(AffineTransform)aff2.compose(aff));
	}

	@Override
	CycleMethod getCycleMethod() {
		return CycleMethod.REPEAT;
	}

}
