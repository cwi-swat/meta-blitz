package nogbeter.paths.simple.lines;

import nogbeter.util.Interval;
import bezier.points.Vec;

public class VerticalDULine extends VerticalLine{

	public VerticalDULine(double x, Interval yInterval,
			Interval tinterval) {
		super(x, yInterval, tinterval);
	}


	@Override
	double getTForY(double y) {
		return 1.0 - yInterval.getFactorForPoint(x);
	}

	@Override
	public Vec getAtLocal(double t) {
		return new Vec(x,yInterval.getAtFactor(1.0 -t));
	}

	@Override
	public Vec getTangentAtLocal(double t) {
		return new Vec(1,0);
	}
	
}
