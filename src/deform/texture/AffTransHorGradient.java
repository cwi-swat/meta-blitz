package deform.texture;

import java.awt.GradientPaint;
import java.awt.Paint;

import deform.Color;
import deform.Texture;
import deform.Transform;
import deform.Vec;

public class AffTransHorGradient implements Texture, Java2DTexture, AffineTransformableTex{
	
	final HorGradient gradient;
	final Transform aff;
	AffTransHorGradient(HorGradient gradient, Transform aff) {
		this.gradient = gradient;
		this.aff = aff;
	}
	@Override
	public Color sample(Vec point) {
		return gradient.sample(aff.from(point));
	}
	@Override
	public Texture transform(Transform t) {
		return new AffTransHorGradient(gradient,t.compose(aff));
	}
	@Override
	public Paint getPaint() {
		Vec a = aff.to(new Vec(0,0));
		Vec b = aff.to(new Vec(1,0));
//		System.out.println(a + " " + b);
		return new GradientPaint((float)a.x,(float)a.y,ConvertColor.toJava2DColor(gradient.ca),
				(float)b.x,(float)b.y,ConvertColor.toJava2DColor(gradient.cb),true);
	}
	

}
