package bezier.image;

public class Sample {

	private final double[] samples;
	
	Sample(double ... samples){
		this.samples = samples;
	}
	
	double getChannel(int c){
		return samples[c];
	}
	
}
