package nogbeter.paths.simple.lines;

import nogbeter.paths.BestProjectTup;
import nogbeter.paths.compound.SetIndex;
import nogbeter.paths.compound.ShapeSet;
import nogbeter.paths.simple.SimplePathIndex;
import nogbeter.util.Interval;
import bezier.points.Vec;

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
