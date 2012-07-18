package deform;

import static textures.Util.clamp;




public class Color {
	public final static int SampleSize = 4;
	public static Color transparent = new Color(0,0,0,0);
	public final int r, g, b, a;

	public Color(int r, int g, int b, int a) {
		this.r = r;
		this.g = g;
		this.b = b;
		this.a = a;
	}

	
	public Color(int i, int j, int k) {
		this(i,j,k,255);
	}


	public Color mul(int d) {
		d = clamp(d);
		return new Color((r * d >> 8), (g * d >> 8), (b * d >> 8),
				(a * d) >> 8);
	}

	public Color add(Color rhs) {
		return new Color(clamp(r + rhs.r), clamp(g + rhs.g), clamp(b
				+ rhs.b), clamp(a + rhs.a));
	}

	public Color lerp(double frac, Color other){
		return lerp((int)(frac * 255.0),other);
	}
	
	public Color lerp(int d, Color other){
		d = clamp(d);
		int rd = 255 - d;
		return new Color((r * rd >> 8) + (other.r * d >> 8),
				(g * rd >> 8) + (other.g * d >> 8),
				(b * rd >> 8) + (other.b * d >> 8),
				(a * rd >> 8) + (other.a * d >> 8));
	}

}
