package textures.interfaces;

import static textures.Util.clamp;

import java.awt.image.DataBuffer;


public class Sample {
	public static final int SampleSize = 4;
	public static final Sample zero = new Sample(0,0,0,0);
	public final int r, g, b, a;

	public Sample(int r, int g, int b, int a) {
		this.r = r;
		this.g = g;
		this.b = b;
		this.a = a;
	}

	public Sample mul(int d) {
		d = clamp(d);
		return new Sample((r * d >> 8), (g * d >> 8), (b * d >> 8),
				(a * d) >> 8);
	}

	public Sample add(Sample rhs) {
		return new Sample(clamp(r + rhs.r), clamp(g + rhs.g), clamp(b
				+ rhs.b), clamp(a + rhs.a));
	}

	public Sample read(DataBuffer data, int index) {
		return new Sample(data.getElem(index + 3), data.getElem(index + 2),
				data.getElem(index + 1), data.getElem(index));
	}

	public void write(DataBuffer data, int index) {
		data.setElem(index + 3, a);
		data.setElem(index + 2, b);
		data.setElem(index + 1, g);
		data.setElem(index, r);
	}
	public Sample lerp(double frac, Sample other){
		return lerp((int)(frac * 255.0),other);
	}
	
	public Sample lerp(int d, Sample other){
		d = clamp(d);
		int rd = 255 - d;
		return new Sample((r * rd >> 8) + (other.r * d >> 8),
				(g * rd >> 8) + (other.g * d >> 8),
				(b * rd >> 8) + (other.b * d >> 8),
				(a * rd >> 8) + (other.a * d >> 8));
	}

}
