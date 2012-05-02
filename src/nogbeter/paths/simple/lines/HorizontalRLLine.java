package nogbeter.paths.simple.lines;

import nogbeter.points.oned.Interval;
import nogbeter.points.twod.Vec;

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
	
	@Override
	public String toString() {
		return String.format("HorRLLine %s -> %s", getStartPoint().toString(), getEndPoint().toString());
	}
	
}
