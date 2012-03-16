package bezier.segment.curve;

import bezier.util.STuple;

public class TInterval {
	public final double tStart, tLength;
	
	public TInterval(){
		this.tStart = 0;
		this.tLength = 1.0;
	}
	
	public TInterval(double tStart, double tLength) {
		this.tStart = tStart;
		this.tLength = tLength;
	}

	public TInterval(int i) {
		this.tStart = (double)i;
		this.tLength = 1;
	}

	public STuple<TInterval> split(){
		double halfLength = 0.5 * tLength;
		return new STuple<TInterval>(
				new TInterval(tStart, halfLength),
				new TInterval(tStart + halfLength, halfLength));
	}

	public double convertBack(double t){
		return tStart + t * tLength;
	}
}
