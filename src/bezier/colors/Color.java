package bezier.colors;

import bezier.util.Util;

public class Color {

	final double r,g,b,a;

	public static final Color BLACK = new Color(0,0,0,1);
	public static final Color WHITE = new Color(1,1,1,1);	
	public static final Color RED = new Color(1,0,0,1);	
	public static final Color GREEN = new Color(0,1,0,1);	
	public static final Color BLUE = new Color(0,0,1,1);
	public static final Color YELLOW = new Color(1,1,0,1);
	public static final Color PURPLE = new Color(1,0,1,1);
	public static final Color GREENBLUE = new Color(0,1,1,1);	
	public static final Color TRANSPARENT = new Color(0,0,0,0);
	public Color(double r, double g, double b, double a){
		this.r = Util.clamp(r);
		this.g = Util.clamp(g);
		this.b = Util.clamp(b);
		this.a = Util.clamp(a);
	}

	public Color interpolate(double t, Color other){
		double rt = 1.0 - t;
		return new Color(rt * r + t *other.r,rt * g + t *other.g, rt * b + t *other.b, rt * a + t *other.a );
	}
	
	public boolean isFullyTransparent(){
		return a == 0.0;
	}
	
	public java.awt.Color toAWT(){
		return new java.awt.Color((float)r, (float)g, (float)b, (float)a);
	}
}
