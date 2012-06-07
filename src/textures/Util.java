package textures;

import textures.interfaces.ISample;

public class Util {
	
	public static <Sample extends ISample<Sample>> Sample lerp(Sample a, double d, Sample b){
		double rd = 1.0 - d;
		return a.mul(rd).add(b.mul(d));
	}

}
