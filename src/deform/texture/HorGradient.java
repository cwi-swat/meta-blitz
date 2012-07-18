package deform.texture;

import java.awt.GradientPaint;
import java.awt.Paint;

import deform.Color;
import deform.Texture;
import deform.Transform;
import deform.Vec;

public class HorGradient implements Texture, Java2DTexture, AffineTransformableTex{

	final Color ca, cb;
	
	public HorGradient(Color ca,Color cb){
		this.ca = ca;
		this.cb = cb;
	}
	
	@Override
	public Paint getPaint() {
		return new GradientPaint(0.f,0.f, ConvertColor.toJava2DColor(ca),
				1.f,0.f, ConvertColor.toJava2DColor(cb));
	}


	@Override
	public Color sample(Vec point) {
		double x = Math.abs(point.x);
		int e = (int)x;
		if(e % 2 == 0){
			return ca.lerp(x - e,cb);
		} else {
			return cb.lerp(x - e,ca);
		}
	}

	@Override
	public Texture transform(Transform t) {
		return new AffTransHorGradient(this, t);
	}
	

}
