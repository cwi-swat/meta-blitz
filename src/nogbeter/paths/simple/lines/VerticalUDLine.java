package nogbeter.paths.simple.lines;

import nogbeter.util.Interval;
import bezier.points.Vec;

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
	public Vec getAtLocal(double t) {
		return new Vec(x,yInterval.getAtFactor(t));
	}

	@Override
	public Vec getTangentAtLocal(double t) {
		return new Vec(-1,0);
	}
	
}
