package textures.sample;

import textures.interfaces.ISample;
// alias Sample3
public class Color implements ISample<Color>{
	public final double r,g,b;

	public Color(double x, double y, double z) {
		this.r = x;
		this.g = y;
		this.b = z;
	}

	@Override
	public Color mul(double d) {
		return new Color(r*d, g*d, b*d);
	}

	@Override
	public Color add(Color rhs) {
		return new Color(r + rhs.r, g + rhs.g , b + rhs.b);
	}

	@Override
	public Color read(double[] data, int index) {
		return new Color(data[index+2], data[index+1], data[index]);
	}

	@Override
	public void write(double[] data, int index) {
		data[index+2] = r;
		data[index+1] = g;
		data[index] = g;
	}

	@Override
	public int getSize() {
		return 3;
	}
	

}
