package bezier.image;

public class Sample {


	public static final Sample BLACK = new Sample(0,0,0,1);
	public static final Sample WHITE = new Sample(1,1,1,1);	
	public static final Sample RED = new Sample(0,0,1,1);	
	public static final Sample GREEN = new Sample(0,1,0,1);	
	public static final Sample BLUE = new Sample(1,1,0,0);
	public static final Sample YELLOW = new Sample(1,0,1,1);
	public static final Sample PURPLE = new Sample(1,1,0,1);
	public static final Sample GREENBLUE = new Sample(1,1,1,0);	
	public static final Sample TRANSPARENT = new Sample(0,0,0,0);
	public static final int NR_CHANNELS = 4;
	public static final int R = 0, G= 1, B = 2, A = 3;
	
	
	private final double[] samples;
	
	public Sample(double ... samples){
		this.samples = samples;
	}
	
	public Sample(int nrChannels){
		samples = new double[nrChannels];
	}
	
	double getChannel(int c){
		return samples[c];
	}
	
	public int nrChannels(){
		return samples.length;
	}
	
	public Sample interpolate(double t, Sample other){
		double ot = 1.0 - t;
		double[] result = new double[samples.length];
		for(int i = 0 ; i < samples.length ;i++){
			result[i] = samples[i] * ot + other.samples[i] * t;
		}
		return new Sample(result);
	}
	

	public double getRed(){
		return getChannel(R);
	}
	
	public double getGreen(){
		return getChannel(G);
	}
	
	public double getBlue(){
		return getChannel(B);
	}
	
	public double getAlpha(){
		return getChannel(A);
	}
	
	
	public boolean isFullyTransparent(){
		return getAlpha() == 0.0;
	}
	
	public java.awt.Color toAWT(){
		return new java.awt.Color((float)getRed(), (float)getGreen(), (float)getBlue(), (float)getAlpha() );
	}
	
	public Sample div(double d){
		return mul(1.0/d);
	}
	
	public Sample mul(double d){
		double[] newVals = new double[samples.length];
		for(int i = 0; i < newVals.length ; i++){
			newVals[i] = samples[i]*d;
		}
		return new Sample(newVals);
	}
	
	public Sample add(Sample other){
		double[] newVals = new double[samples.length];
		for(int i = 0; i < newVals.length ; i++){
			newVals[i] = samples[i]+other.samples[i];
		}
		return new Sample(newVals);
	}
	
	public String toString(){
		StringBuffer b = new StringBuffer();
		b.append("Sample(");
		for(double d : samples){
			b.append(d);
			b.append(' ');
		}
		b.append(")\n");
		return b.toString();
	}
}
