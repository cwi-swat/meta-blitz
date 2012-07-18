package deform.texture;

import deform.Color;

public class ConvertColor {
	public static java.awt.Color toJava2DColor(Color c){
		return new java.awt.Color(c.r,c.g,c.b,c.a);
	}
}
