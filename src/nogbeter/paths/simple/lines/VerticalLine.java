package nogbeter.paths.simple.lines;

import java.util.List;

import nogbeter.paths.BestProject;
import nogbeter.paths.ConnectedPath;
import nogbeter.paths.Path;
import nogbeter.paths.simple.SimplePathFactory;
import bezier.points.Vec;
import bezier.util.BBox;
import bezier.util.Tuple;
import bezier.util.Util;

public class VerticalLine extends ActualLine {

	public final Vec start; 
	public final double endY;
	public final double diff;
	public VerticalLine(Vec start, double endY) {
		this.start = start;
		this.endY = endY;
		diff = endY - start.y;
	}
	@Override
	public Vec getStartPoint() {
		return start;
	}
	@Override
	public Vec getEndPoint() {
		return new Vec(start.x, endY);
	}
	@Override
	public Vec getAt(double t) {
		return new Vec(start.x, t * diff + start.y);
	}
	@Override
	public Vec getTangentAt(double t) {
		return diff > 0 ? new Vec(1,0) : new Vec(-1,0); 
	}
	@Override
	public ConnectedPath reverse() {
		return new VerticalLine(getEndPoint(), start.x);
	}
	public double getTForY(double y){
		return (y - start.y) / diff;
	}
	@Override
	public <OPathParam> Tuple<List<Double>,List<OPathParam>>  intersection(
			Path<OPathParam> other) {
		return other.intersectionLVerLine(this);
	}
	@Override
	public Tuple<List<Double>,List<Double>> intersectionLDiaLine(DiagonalLine lhs) {
		return lhs.intersectionLVerLine(this).flip();
	}
	@Override
	public Tuple<List<Double>,List<Double>> intersectionLHorLine(HorizontalLine lhs) {
		return lhs.intersectionLVerLine(this).flip();
	}
	@Override
	public Tuple<List<Double>,List<Double>> intersectionLVerLine(VerticalLine lhs) {
		return Util.emptyTupleList;
	}
	
	@Override
	public BestProject<Double> project(Vec p, double bestDist) {
		double t = Util.clamp(getTForY(p.y));
		Vec v = getAt(t);
		return new BestProject<Double>(v.distanceSquared(p), t);
	}
	

	private double xMinDist(BBox b){
		switch(b.yIntervalLocation(start.x)){
		case LEFT_OF: return b.x - start.x;
		case INSIDE: return 0;
		case RIGHT_OF: return start.x -b.xr;
		}
		return 0;
	}
	
	private double yMinDist(BBox b){
		double s = Math.min(start.y, endY);
		double se = Math.max(start.y, endY);
		switch(Util.intervalIntervalLocation(s, se, b.y , b.yd)){
		case LEFT_OF: return b.y - se;
		case INSIDE: return 0;
		case RIGHT_OF: return b.yd - s;
		}
		return 0;
	}
	
	public double minDistance(BBox b){
		double yMinDist = yMinDist(b);
		double xMinDist = xMinDist(b);
		return xMinDist*xMinDist + yMinDist * yMinDist;
	}
}
