package textures;

import java.util.ArrayList;
import java.util.List;

import util.Tuple;

public class Util {

	public static int DistByte = Byte.MAX_VALUE - Byte.MIN_VALUE;

	public static int clamp(int i) {
		return Math.max(0, Math.min(255, i));
	}

	public static int doubleToByte(double d) {
		return clamp((int) (d * 255));
	}

	public static double byteToDouble(int d) {
		return d / 255.0;
	}
	

}
