package textures.sample;

import textures.interfaces.ISample;

public class Sample1 implements ISample<Sample1>{
	public final double x;


	public Sample1(double x) {
		this.x = x;
	}
	
	@Override
	public Sample1 mul(double d) {
		return new Sample1(x*d);
	}


	@Override
	public Sample1 add(Sample1 rhs) {
		return new Sample1(x+rhs.x);
	}

	@Override
	public Sample1 read(double[] data, int index) {
		return new Sample1(data[index]);
	}

	@Override
	public void write(double[] data, int index) {
		data[index] = x;
		
	}

	@Override
	public int getSize() {
		return 1;
	}

}
