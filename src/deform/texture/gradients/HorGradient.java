package deform.texture.gradients;

import java.awt.GradientPaint;
import java.awt.MultipleGradientPaint.CycleMethod;
import java.awt.Paint;

import deform.Color;
import deform.Library;
import deform.Library.GradientCycleMethod;
import deform.texture.AffineTransformableTex;
import deform.texture.ConvertColor;
import deform.texture.Java2DTexture;
import deform.Texture;
import deform.Transform;
import deform.Vec;

public abstract class HorGradient implements Texture, Java2DTexture, AffineTransformableTex{

	final Color ca, cb;
	final Transform aff;
	
	public HorGradient(Color ca,Color cb,Transform aff){
		this.ca = ca;
		this.cb = cb;
		this.aff = aff;
	}
	
	@Override
	public Paint getPaint() {
		Vec a = aff.to(new Vec(0,0));
		Vec b = aff.to(new Vec(1,0));
//		System.out.println(a + " " + b);
		return new GradientPaint((float)a.x,(float)a.y,ConvertColor.toJava2DColor(ca),
				(float)b.x,(float)b.y,ConvertColor.toJava2DColor(cb),isCyclic());
	}


	abstract boolean isCyclic();
	
	

}
