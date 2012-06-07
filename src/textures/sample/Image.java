package textures.sample;

import textures.interfaces.ISample;

public class Image<Sample extends ISample<Sample>> {
	public final double[] samples;
	public final int width , height, sampleSize, rowLength;
	public final Sample bootstrap;
	
	@SuppressWarnings("unchecked")
	public Image(int width, int height, int sampleSize, Sample bootstrap, double[] samples){
		this.width = width;
		this.height = height;
		this.sampleSize = sampleSize;
		this.bootstrap = bootstrap;
		this.samples= samples;
		this.rowLength = width * sampleSize;
	}
	
	public Image(int width, int height, Sample bootstrap){
		this.bootstrap = bootstrap;
		this.sampleSize = bootstrap.getSize();
		samples = new double[width * height * sampleSize];
		this.width = width;
		this.height = height;
		this.rowLength = width * sampleSize;
	}
	
	public int getIndex(int x, int y){
		return y * rowLength + x * sampleSize;
	}
	
	public void set(int x, int y, Sample s){
		s.write(samples, getIndex(x, y));
	}
	
	public void set(int i, Sample s){
		s.write(samples, i);
	}
	
	

	public Sample get(int x, int y){
		return bootstrap.read(samples,getIndex(x,y));
	}

}
