package nogbeter.paths.simple.lines;

import nogbeter.points.oned.Interval;
import nogbeter.points.twod.Vec;

public class VerticalDULine extends VerticalLine{

	public VerticalDULine(double x, Interval yInterval,
			Interval tinterval) {
		super(x, yInterval, tinterval);
	}


	@Override
	double getTForY(double y) {
		return 1.0 - yInterval.getFactorForPoint(y);
	}

	@Override
	public Vec getAtLocal(double t) {
		return new Vec(x,yInterval.getAtFactor(1.0 -t));
	}

	@Override
	public Vec getTangentAtLocal(double t) {
		return new Vec(0,-1);
	}
	
	@Override
	public String toString() {
		return String.format("VerDULine %s -> %s", getStartPoint().toString(), getEndPoint().toString());
	}
	
}
