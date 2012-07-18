package deform.texture.gradients;

import java.awt.Paint;
import java.awt.RadialGradientPaint;
import java.awt.geom.Point2D;

import deform.Color;
import deform.Vec;
import deform.Library.ColorAndFraction;
import deform.texture.ConvertColor;
import deform.transform.affine.AffineTransform;

public abstract class RadialMultipleGradient extends MultipleGradient {
	public RadialMultipleGradient(ColorAndFraction[] cs, AffineTransform aff) {
		super(cs, aff);
	}

	public RadialMultipleGradient(double[] fractions, Color[] colors,
			AffineTransform aff) {
		super(fractions,colors,aff);
	}


	@Override
	public Paint getPaint() {
		float[] fracf = new float[fractions.length];
		java.awt.Color[] colorj = new java.awt.Color[colors.length];
		for(int i = 0 ; i < fracf.length ; i++){
			fracf[i] = (float)fractions[i];
			colorj[i] = ConvertColor.toJava2DColor(colors[i]);
		}
		Vec a = aff.to(new Vec(0,0));
		Vec b = aff.to(new Vec(1,0));
		float rad = (float)b.distance(a);
		return new RadialGradientPaint(new Point2D.Double(a.x,a.y), rad,  
				fracf, colorj, getCycleMethod());
	}
}
