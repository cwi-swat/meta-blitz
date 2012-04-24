package nogbeter.paths.simple.lines;

import bezier.points.Vec;
import bezier.util.Tuple;
import nogbeter.paths.BestProject;
import nogbeter.paths.Path;
import nogbeter.paths.simple.nonlinear.NonLinearCurve;
import nogbeter.util.BBox;
import nogbeter.util.InclusiveInterval;

public class HorizontalLRLine extends HorizontalLine{

	public HorizontalLRLine(InclusiveInterval xInterval, double y,
			InclusiveInterval tinterval) {
		super(xInterval, y, tinterval);
	}

	@Override
	double getTForX(double x) {
		return xInterval.getFactorForPoint(x);
	}

	@Override
	public Vec getAt(double t) {
		return new Vec(xInterval.getAtFactor(t),y);
	}

	@Override
	public Vec getTangentAt(double t) {
		return new Vec(0,-1);
	}
}
