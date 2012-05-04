package nogbeter.paths.simple.lines;

import nogbeter.paths.compound.SetIndex;
import nogbeter.paths.compound.ShapeSet;
import nogbeter.paths.results.project.BestProjectTup;
import nogbeter.paths.simple.SimplePathIndex;
import nogbeter.points.oned.Interval;
import nogbeter.points.twod.Vec;

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
		return new Vec(1,0);
	}
	
	@Override
	public String toString() {
		return String.format("HorLRLine %s -> %s", getStartPoint().toString(), getEndPoint().toString());
	}




}
