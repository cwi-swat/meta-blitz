package deform.texture;

import java.awt.Paint;

import deform.Color;
import deform.Texture;
import deform.Vec;

public class FillColor implements Texture,Java2DTexture{

	final Color c;
	
	public FillColor(Color c){
		this.c = c;
	}
	
	@Override
	public Paint getPaint() {
		return new java.awt.Color(c.r,c.g,c.b,c.a);
	}

	@Override
	public Color sample(Vec point) {
		return c;
	}

}
