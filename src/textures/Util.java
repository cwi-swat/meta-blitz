package textures;

import textures.interfaces.ISample;
import static java.lang.Math.*;

public class Util {
	
	public static int DistByte = Byte.MAX_VALUE - Byte.MIN_VALUE;
	
	public static <Sample extends ISample<Sample>> Sample lerp(Sample a, double d, Sample b){
		return lerp(a,doubleToByte(d), b);
	}
	
	public static <Sample extends ISample<Sample>> Sample lerp(Sample a, int d, Sample b){
		int rd = Util.clamp(255 - d);
		return a.mul(rd).add(b.mul(d));
	}
	
	public static int clamp(int i){
		return Math.max(0, Math.min(255,i));
	}
	
	
	public static int doubleToByte(double d){
		return clamp((int)(d * 255));
	}
	
	public static double byteToDouble(int d){
		return d /255.0;
	}

}
