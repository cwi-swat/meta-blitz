package nogbeter.paths.simple.lines;

import java.util.List;

import nogbeter.paths.BestProject;
import nogbeter.paths.ConnectedPath;
import nogbeter.paths.Path;
import nogbeter.paths.simple.SimplePath;
import nogbeter.paths.simple.SimplePathFactory;
import nogbeter.paths.simple.nonlinear.NonLinearCurve;
import nogbeter.util.InclusiveInterval;
import bezier.points.Vec;
import bezier.util.STuple;
import bezier.util.Tuple;
import bezier.util.Util;

public abstract class VerticalLine extends ActualLine {


	public final InclusiveInterval yInterval;
	public final double x;

	public VerticalLine(double x, InclusiveInterval yInterval,  InclusiveInterval tinterval) {
		super(tinterval);
		this.yInterval = yInterval;
		this.x = x;
	}

	abstract double getTForY(double y);
	
	@Override
	public <OPathParam> Tuple<List<Double>, List<OPathParam>> intersection(
			Path<OPathParam> other) {
		return other.intersectionLVerLine(this);
	}

	@Override
	public Tuple<List<Double>, List<Double>> intersectionLDiaLine(
			DiagonalLine lhs) {
		return lhs.intersectionLVerLine(this).flip();
	}

	@Override
	public Tuple<List<Double>, List<Double>> intersectionLHorLine(
			HorizontalLine lhs) {
		if(yInterval.isInside(lhs.y) && lhs.xInterval.isInside(x)){
			return makeIntersectionResult(lhs,lhs.getTForX(x), getTForY(lhs.y));
		} else {
			return Util.emptyTupleList;
		}
	}

	@Override
	public Tuple<List<Double>, List<Double>> intersectionLVerLine(
			VerticalLine lhs) {
		return Util.emptyTupleList;
	}
	
	@Override
	public Tuple<List<Double>, List<Double>> intersectionLNonLinear(
			NonLinearCurve lhs) {
		if(lhs.getBBox().xInterval.isInside(x) 
			&& lhs.getBBox().yInterval.overlapsWith(yInterval)){
			double lt = lhs.findTForX(x);
			if(InclusiveInterval.interval01.isInside(lt)){
				double rt = getTForY(lhs.getAt(lt).y);
				if(InclusiveInterval.interval01.isInside(rt)){
					return makeIntersectionResult(lhs, lt, rt);
				}
			}
		} 
		return Util.emptyTupleList;
	}
	
	@Override
	public void project(BestProject<Double> best, Vec p) {
		double y = yInterval.getClosestPoint(p.y);
		double dist = new Vec(x,y).distanceSquared(p);
		updateBestProject(best,dist,getTForY(x));
		
	}
}
