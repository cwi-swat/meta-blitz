package nogbeter.paths.simple.lines;

import nogbeter.points.oned.Interval;
import nogbeter.points.twod.Vec;

public class VerticalUDLine extends VerticalLine{

	public VerticalUDLine(double x, Interval yInterval,
			Interval tinterval) {
		super(x, yInterval, tinterval);
	}

	@Override
	double getTForY(double y) {
		return yInterval.getFactorForPoint(y);
	}

	@Override
	public Vec getAtLocal(double t) {
		return new Vec(x,yInterval.getAtFactor(t));
	}

	@Override
	public Vec getTangentAtLocal(double t) {
		return new Vec(0,1);
	}
	
	@Override
	public String toString() {
		return String.format("VerUDLine %s -> %s", getStartPoint().toString(), getEndPoint().toString());
	}
}
