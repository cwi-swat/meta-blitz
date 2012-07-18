package deform.texture.gradients;

import java.awt.MultipleGradientPaint.CycleMethod;

import deform.Color;
import deform.Library.ColorAndFraction;
import deform.Texture;
import deform.Transform;
import deform.Vec;
import deform.transform.affine.AffineTransform;

public class HorNoCycleMultipleGradient extends HorMultipleGradient{

	public HorNoCycleMultipleGradient(ColorAndFraction[] cs, AffineTransform aff) {
		super(cs, aff);
	}

	public HorNoCycleMultipleGradient(double[] fractions, Color[] colors,
			AffineTransform aff) {
		super(fractions,colors,aff);
	}

	@Override
	public Color sample(Vec point) {
		point = aff.from(point);
		if(point.x <= 0 ){
			return colors[0];
		} else if(point.x >= 1){
			return colors[colors.length-1];
		} else {
			return getColor(point.x);
		}
	}

	@Override
	public Texture transform(Transform t) {
		AffineTransform aff2 = (AffineTransform)t;
		return new HorNoCycleMultipleGradient(fractions,colors,(AffineTransform)aff2.compose(aff));
	}

	@Override
	CycleMethod getCycleMethod() {
		return CycleMethod.NO_CYCLE;
	}

}
