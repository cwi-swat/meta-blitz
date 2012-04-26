package nogbeter.paths.simple.lines;

import bezier.points.Vec;
import nogbeter.util.Interval;

public class VerticalUDLine extends VerticalLine{

	public VerticalUDLine(double x, Interval yInterval,
			Interval tinterval) {
		super(x, yInterval, tinterval);
	}

	@Override
	double getTForY(double y) {
		return yInterval.getFactorForPoint(x);
	}

	@Override
	public Vec getAt(double t) {
		return new Vec(x,yInterval.getAtFactor(t));
	}

	@Override
	public Vec getTangentAt(double t) {
		return new Vec(-1,0);
	}
	
}
