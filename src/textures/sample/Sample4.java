package textures.sample;

import textures.interfaces.ISample;

public class Sample4 implements ISample<Sample4>{
	public final double x,y,z,p;

	public Sample4(double x, double y, double z, double p) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.p = p;
	}

	@Override
	public Sample4 mul(double d) {
		return new Sample4(x*d, y*d, z*d, p*d);
	}

	@Override
	public Sample4 add(Sample4 rhs) {
		return new Sample4(x+rhs.x, y+rhs.y, z+rhs.z, p+rhs.p);
	}

	@Override
	public Sample4 read(double[] data, int index) {
		return new Sample4(data[index] , data[index+1], data[index+2] ,data[index+3]);
	}

	@Override
	public void write(double[] data, int index) {
		data[index] = x;
		data[index+1] = y;
		data[index+2] = z;
		data[index+3] = p;
		
	}

	@Override
	public int getSize() {
		return 4;
	}
}
