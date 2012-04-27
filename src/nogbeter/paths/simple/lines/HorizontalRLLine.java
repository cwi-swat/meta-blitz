package nogbeter.paths.simple.lines;

import bezier.points.Vec;
import nogbeter.util.Interval;

public class HorizontalRLLine extends HorizontalLine{

	public HorizontalRLLine(Interval xInterval, double y,
			Interval tinterval) {
		super(xInterval, y, tinterval);
	}


	@Override
	double getTForX(double x) {
		return 1.0 - xInterval.getFactorForPoint(x);
	}

	@Override
	public Vec getAtLocal(double t) {
		return new Vec(xInterval.getAtFactor(1.0 - t),y);
	}

	@Override
	public Vec getTangentAtLocal(double t) {
		return new Vec(0,1);
	}
	
}
