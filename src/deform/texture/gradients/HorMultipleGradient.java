package deform.texture.gradients;

import java.awt.LinearGradientPaint;
import java.awt.Paint;
import java.awt.MultipleGradientPaint.CycleMethod;
import java.awt.geom.Point2D;

import deform.Color;
import deform.Library.ColorAndFraction;
import deform.Texture;
import deform.Transform;
import deform.Vec;
import deform.texture.ConvertColor;
import deform.transform.affine.AffineTransform;

public abstract class HorMultipleGradient extends MultipleGradient{
	HorMultipleGradient(double[] fractions, Color[] colors, AffineTransform aff) {
		super(fractions, colors, aff);
	}
	
	HorMultipleGradient(ColorAndFraction[] cs, AffineTransform aff) {
		super(cs, aff);
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
		return new LinearGradientPaint(new Point2D.Double(a.x,a.y), new Point2D.Double(b.x, b.y), 
				fracf, colorj, getCycleMethod());
	}

}
