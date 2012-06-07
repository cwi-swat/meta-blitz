package textures.sample;

import textures.interfaces.ISample;

public class Sample2 implements ISample<Sample2>{
	public final double x, y;


	public Sample2(double x, double y) {
		this.x = x;
		this.y = y;
	}

	
	@Override
	public Sample2 mul(double d) {
		return new Sample2(x*d, y*d);
	}

	@Override
	public Sample2 add(Sample2 rhs) {
		return new Sample2(x+rhs.x, y + rhs.y);
	}


	@Override
	public Sample2 read(double[] data, int index) {
		return new Sample2(data[index], data[index]+1);
	}


	@Override
	public void write(double[] data, int index) {
		data[index] = x;
		data[index+1] = y;
	}


	@Override
	public int getSize() {
		return 2;
	}
}
