package nogbeter.paths.simple.lines;

import bezier.points.Vec;
import nogbeter.util.InclusiveInterval;

public class VerticalDULine extends VerticalLine{

	public VerticalDULine(double x, InclusiveInterval yInterval,
			InclusiveInterval tinterval) {
		super(x, yInterval, tinterval);
	}


	@Override
	double getTForY(double y) {
		return 1.0 - yInterval.getFactorForPoint(x);
	}

	@Override
	public Vec getAt(double t) {
		return new Vec(x,yInterval.getAtFactor(1.0 -t));
	}

	@Override
	public Vec getTangentAt(double t) {
		return new Vec(1,0);
	}
	
}
