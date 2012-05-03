package nogbeter.paths.simple.lines;

import java.awt.geom.PathIterator;

import nogbeter.paths.Path;
import nogbeter.paths.factory.PathFactory;
import nogbeter.paths.simple.SimplePath;
import nogbeter.paths.simple.SimplePathIndex;
import nogbeter.points.oned.Interval;
import nogbeter.points.twod.BBox;
import nogbeter.points.twod.Vec;
import nogbeter.transform.AffineTransformation;
import bezier.util.Tuple;

public abstract class Line extends SimplePath {

	public Line(Interval tInterval) {
		super(tInterval);
	}
	
	@Override
	public BBox makeBBox(){
		return BBox.fromPoints(getStartPoint(), getEndPoint());
	}
	@Override
	public Line getWithAdjustedStartPoint(Vec newStartPoint) {
		return PathFactory.createLine(newStartPoint, getEndPoint(),tInterval);
	}
	
	public abstract double minDistSquaredTo(BBox b);
	
	public abstract double distanceSquared(Vec v) ;
	
	public abstract boolean overlaps(BBox b) ;
	
	@Override
	public Tuple<Path,Path> splitSimpler() {
		throw new Error("Cannot make" + this + "simpler!");
	}
	
	public static Line createLine(Vec start, Vec end, Interval interval){
		if (start.x == end.x) {
			if (start.y < end.y) {
				return new VerticalUDLine(start.x, new Interval(
						start.y, end.y), interval);
			} else {
				return new VerticalDULine(start.x, new Interval(end.y,
						start.y), interval);
			}
		} else if (start.y == end.y) {
			if (start.x < end.x) {
				return new HorizontalLRLine(new Interval(start.x,
						end.x), start.y, interval);
			} else {
				return new HorizontalRLLine(new Interval(start.x,
						end.x), start.y, interval);
			}
		} else {
			return new DiagonalLine(start, end, interval);
		}
	}
	

	public double length(){
		return getStartPoint().distance(getEndPoint());
	}
	
	@Override
	public int awtCurSeg(float[] coords) {
		Vec end = getEndPoint();
		coords[0] = (float)end.x; coords[1] = (float)end.y;
		return PathIterator.SEG_LINETO;
	}
	

	@Override
	public Line transform(
			AffineTransformation t) {
		return PathFactory.createLine(t.to(getStartPoint()), t.to(getEndPoint()));
	}
	
	@Override
	public Tuple<Path<SimplePathIndex>,Double> normaliseToLength(double prevLength) {
		double nl = prevLength + length();
		return new Tuple<Path<SimplePathIndex>, Double>(
			PathFactory.createLine(getStartPoint(),getEndPoint(),new Interval(prevLength,nl)),
			nl);
	}
}
