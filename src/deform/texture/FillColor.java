package deform.texture;

import java.awt.Paint;

import deform.Color;
import deform.Texture;
import deform.Transform;
import deform.Vec;

public class FillColor implements Texture,Java2DTexture, AffineTransformableTex{

	final Color c;
	
	public FillColor(Color c){
		this.c = c;
	}
	
	@Override
	public Paint getPaint() {
		return ConvertColor.toJava2DColor(c);
	}

	@Override
	public Color sample(Vec point) {
		return c;
	}

	@Override
	public Texture transform(Transform t) {
		return this;
	}

}
