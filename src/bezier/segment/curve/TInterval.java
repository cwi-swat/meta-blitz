package bezier.segment.curve;

import bezier.util.Tuple;

public class TInterval {
	public final double tStart, tLength;
	
	public TInterval(double tStart, double tLength) {
		this.tStart = tStart;
		this.tLength = tLength;
	}

	Tuple<TInterval,TInterval> split(){
		double halfLength = 0.5 * tLength;
		return new Tuple<TInterval, TInterval>(
				new TInterval(tStart, halfLength),
				new TInterval(tStart + halfLength, halfLength));
	}

	double convertBack(double t){
		return tStart + t * tLength;
	}
}
