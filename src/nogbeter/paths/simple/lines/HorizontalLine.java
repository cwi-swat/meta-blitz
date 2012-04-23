package nogbeter.paths.simple.lines;

import java.util.Collections;
import java.util.List;

import bezier.paths.util.BestProjection;
import bezier.points.Vec;
import bezier.util.BBox;
import bezier.util.Tuple;
import bezier.util.Util;
import nogbeter.paths.BestProject;
import nogbeter.paths.ConnectedPath;
import nogbeter.paths.Path;
import nogbeter.paths.simple.SimplePathFactory;

public class HorizontalLine extends ActualLine{

	public final Vec start; 
	public final double endX;
	public final double diff;
	public HorizontalLine(Vec start, double endX) {
		this.start = start;
		this.endX = endX;
		diff = endX - start.x;
	}
	@Override
	public Vec getStartPoint() {
		return start;
	}
	@Override
	public Vec getEndPoint() {
		return new Vec(endX,start.y);
	}
	@Override
	public Vec getAt(double t) {
		return new Vec(t * diff + start.x, start.y);
	}
	@Override
	public Vec getTangentAt(double t) {
		return diff > 0 ? new Vec(0,1) : new Vec(0,-1); 
	}
	@Override
	public ConnectedPath reverse() {
		return new HorizontalLine(getEndPoint(), start.x);
	}

	
	
	public double getTForX(double x){
		return (x - start.x) / diff;
	}
	
	@Override
	public <OPathParam> Tuple<List<Double>,List<OPathParam>>  intersection(
			Path<OPathParam> other) {
		return other.intersectionLHorLine(this);
	}
	
	@Override
	public Tuple<List<Double>,List<Double>> intersectionLDiaLine(DiagonalLine lhs) {
		return lhs.intersectionLHorLine(this).flip();
	}
	@Override
	public Tuple<List<Double>,List<Double>> intersectionLHorLine(HorizontalLine lhs) {
		return Util.emptyTupleList;
	}
	
	
	@Override
	public Tuple<List<Double>,List<Double>>  intersectionLVerLine(VerticalLine lhs) {
		if(Util.isBetweenMaybeFlip(start.y, lhs.start.y, lhs.endY)
			&& Util.isBetweenMaybeFlip(lhs.start.x, start.x, endX)){
			return Util.tupleListFromTuple(
					new Tuple<Double, Double>(lhs.getTForY(start.y), getTForX(lhs.start.x)));
		} else {
			return Util.emptyTupleList;
		}
	}
	@Override
	public BestProject<Double> project(Vec p, double bestDist) {
		double t = Util.clamp(getTForX(p.x));
		Vec v = getAt(t);
		return new BestProject<Double>(v.distanceSquared(p), t);
	}
	
	private double yMinDist(BBox b){
		switch(b.yIntervalLocation(start.y)){
		case LEFT_OF: return b.y - start.y;
		case INSIDE: return 0;
		case RIGHT_OF: return start.y -b.yd;
		}
		return 0;
	}
	
	private double xMinDist(BBox b){
		double s = Math.min(start.x, endX);
		double se = Math.max(start.x, endX);
		switch(Util.intervalIntervalLocation(s, se, b.x , b.xr)){
		case LEFT_OF: return b.x - se;
		case INSIDE: return 0;
		case RIGHT_OF: return b.xr - s;
		}
		return 0;
	}
	
	public double minDistance(BBox b){
		double yMinDist = yMinDist(b);
		double xMinDist = xMinDist(b);
		return xMinDist*xMinDist + yMinDist * yMinDist;
	}
	
	
	
	
	
	
	
}
