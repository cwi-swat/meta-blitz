package nogbeter.paths.simple.lines;

import java.util.List;

import nogbeter.paths.BestProject;
import nogbeter.paths.Path;
import nogbeter.paths.simple.nonlinear.NonLinearCurve;
import nogbeter.util.InclusiveInterval;
import bezier.points.Vec;
import bezier.util.Tuple;
import bezier.util.Util;

public abstract class HorizontalLine extends ActualLine {

	public final InclusiveInterval xInterval;
	public final double y;

	public HorizontalLine(InclusiveInterval xInterval, double y, InclusiveInterval tinterval) {
		super(tinterval);
		this.xInterval = xInterval;
		this.y = y;
	}

	abstract double getTForX(double x);
	
	@Override
	public <OPathParam> Tuple<List<Double>, List<OPathParam>> intersection(
			Path<OPathParam> other) {
		return other.intersectionLHorLine(this);
	}

	@Override
	public Tuple<List<Double>, List<Double>> intersectionLDiaLine(
			DiagonalLine lhs) {
		return lhs.intersectionLHorLine(this).flip();
	}

	@Override
	public Tuple<List<Double>, List<Double>> intersectionLHorLine(
			HorizontalLine lhs) {
		return Util.emptyTupleList;
	}

	@Override
	public Tuple<List<Double>, List<Double>> intersectionLVerLine(
			VerticalLine lhs) {
		if(xInterval.isInside(lhs.x) && lhs.yInterval.isInside(y)){
			return makeIntersectionResult(lhs,lhs.getTForY(y), getTForX(lhs.x));
		} else {
			return Util.emptyTupleList;
		}
	}
	

	@Override
	public Tuple<List<Double>, List<Double>> intersectionLNonLinear(
			NonLinearCurve lhs) {
		if(lhs.getBBox().yInterval.isInside(y) 
			&& lhs.getBBox().xInterval.overlapsWith(xInterval)){
			double lt = lhs.findTForY(y);
			if(InclusiveInterval.interval01.isInside(lt)){
				double rt = getTForX(lhs.getAt(lt).x);
				if(InclusiveInterval.interval01.isInside(rt)){
					return makeIntersectionResult(lhs,lt,rt);
				}
			}
		} 
		return Util.emptyTupleList;
	}
	
	@Override
	public void project(BestProject<Double> best, Vec p) {
		double x = xInterval.getClosestPoint(p.x);
		double dist = new Vec(x,y).distanceSquared(p);
		updateBestProject(best,dist,getTForX(x));
		
	}
	

}
