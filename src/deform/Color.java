package deform;



public class Color {
	public final static int SampleSize = 4;
	public static Color transparent = new Color(0,0,0,0);
	public final int r, g, b, a;

	Color(int r, int g, int b, int a) {
		this.r = r;
		this.g = g;
		this.b = b;
		this.a = a;
	}
	
	public static Color color(int r, int g, int b){
		return new Color(r, g, b, 255);
	}
	
	public static Color color(int r, int g, int b , int a){
		return new Color(r, g, b, a);
	}

	public Color mul(int d) {
		d = clamp(d);
		return new Color((r * d >> 8), (g * d >> 8), (b * d >> 8),
				(a * d) >> 8);
	}
	
	private int clamp(int d) {
		if(d > 255){
			return 255;
		} else if(d < 0){
			return 0;
		} else {
			return d;
		}
	}

	public Color mulMulAlpha(int a) {
		return new Color((r * a >> 8), (g * a >> 8), (b * a >> 8),
				a);
	}
	

	public Color add(Color rhs) {
		if(rhs.a == 255) return rhs;
		if(a==0){
			return rhs;
		}
		if(a + rhs.a > 255){
			int na = 255 - rhs.a;
			return color((r * na >> 8) + (rhs.r* rhs.a >> 8) , 
					(g * na >> 8) + (rhs.g* rhs.a >> 8), 
					(b * na >> 8) + (rhs.b* rhs.a >> 8));
		}
		return new Color(clamp(r + rhs.r), clamp(g + rhs.g), clamp(b
				+ rhs.b), clamp(a + rhs.a));
	}

	public Color lerp(double frac, Color other){
		return lerp((int)(frac * 255.0),other);
	}
	
	public Color lerpNoAlpha(double frac,Color other){
		int d = clamp((int)(frac*255));
		int rd = 255 - d;
		return new Color((r * rd >> 8) + (other.r * d >> 8),
				(g * rd >> 8) + (other.g * d >> 8),
				(b * rd >> 8) + (other.b * d >> 8),
				a);
	}
	
	public Color lerp(int d, Color other){
		d = clamp(d);
		int rd = 255 - d;
		return new Color((r * rd >> 8) + (other.r * d >> 8),
				(g * rd >> 8) + (other.g * d >> 8),
				(b * rd >> 8) + (other.b * d >> 8),
				(a * rd >> 8) + (other.a * d >> 8));
	}
	
	public String toString(){
		return String.format("Color(%d,%d,%d,%d)",r,g,b,a);
	}

}
