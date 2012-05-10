package nogbeter.paths.simple.lines;

import java.awt.geom.PathIterator;
import java.util.List;

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
		return BBox.from2Points(getStartPoint(), getEndPoint());
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
			return new DiagonalLine(start, end, interval);
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
	

	@Override
	public void getSubPath(SimplePathIndex from, SimplePathIndex to,List<Path> result) {
		result.add(PathFactory.createLine(getAt(from), getAt(to)));
	}
	

	@Override
	public void getSubPathFrom(SimplePathIndex from, List<Path> result) {
		result.add(PathFactory.createLine(getAt(from), getEndPoint()));
		
	}

	@Override
	public void getSubPathTo(SimplePathIndex to, List<Path> result) {
		result.add(PathFactory.createLine(getStartPoint(), getAt(to)));
		
	}
}
