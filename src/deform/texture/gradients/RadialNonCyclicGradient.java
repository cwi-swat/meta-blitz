package deform.texture.gradients;

import java.awt.LinearGradientPaint;
import java.awt.MultipleGradientPaint.CycleMethod;
import java.awt.Paint;
import java.awt.RadialGradientPaint;
import java.awt.geom.Point2D;

import deform.Color;
import deform.Texture;
import deform.Transform;
import deform.Vec;
import deform.Library.ColorAndFraction;
import deform.texture.ConvertColor;
import deform.transform.affine.AffineTransform;

public class RadialNonCyclicGradient extends RadialMultipleGradient{

	public RadialNonCyclicGradient(ColorAndFraction[] cs, AffineTransform aff) {
		super(cs, aff);
	}

	public RadialNonCyclicGradient(double[] fractions, Color[] colors,
			AffineTransform aff) {
		super(fractions,colors,aff);
	}




	@Override
	public Color sample(Vec point) {
		double d = aff.from(point).norm();
		if(d >= 1){
			return colors[colors.length-1];
		} else {
			return getColor(d);
		}
	}

	@Override
	public Texture transform(Transform t) {
		AffineTransform aff2 = (AffineTransform)t;
		return new RadialNonCyclicGradient(fractions,colors,(AffineTransform)aff2.compose(aff));
	}

	@Override
	CycleMethod getCycleMethod() {
		return CycleMethod.NO_CYCLE;
	}
	
}
