package nogbeter.paths.simple.lines;

import bezier.points.Vec;
import bezier.util.Tuple;
import nogbeter.paths.BestProject;
import nogbeter.paths.Path;
import nogbeter.paths.simple.nonlinear.Curve;
import nogbeter.util.BBox;
import nogbeter.util.Interval;

public class HorizontalLRLine extends HorizontalLine{

	public HorizontalLRLine(Interval xInterval, double y,
			Interval tinterval) {
		super(xInterval, y, tinterval);
	}

	@Override
	double getTForX(double x) {
		return xInterval.getFactorForPoint(x);
	}

	@Override
	public Vec getAtLocal(double t) {
		return new Vec(xInterval.getAtFactor(t),y);
	}

	@Override
	public Vec getTangentAtLocal(double t) {
		return new Vec(0,-1);
	}

}
