package nogbeter.paths.simple.lines;

import java.awt.geom.PathIterator;
import java.util.List;

import nogbeter.paths.Path;
import nogbeter.paths.PathFactory;
import nogbeter.paths.PathIndex;
import nogbeter.paths.SplittablePath;
import nogbeter.paths.compound.SplitIndex;
import nogbeter.paths.compound.CompoundSplittablePath;
import nogbeter.paths.results.intersections.IIntersections;
import nogbeter.paths.results.intersections.Intersections;
import nogbeter.paths.results.project.BestProjectTup;
import nogbeter.paths.simple.SimplePath;
import nogbeter.paths.simple.SimplePathIndex;
import nogbeter.util.BBox;
import nogbeter.util.Interval;
import bezier.points.Vec;
import bezier.util.Tuple;
import bezier.util.Util;
import static nogbeter.paths.results.transformers.TupleTransformers.*;

public abstract class Line extends SimplePath {

	public Line(Interval tInterval) {
		super(tInterval);
	}
	
	@Override
	public BBox makeBBox(){
		return BBox.fromPoints(getStartPoint(), getEndPoint());
	}
	@Override
	public SimplePath getWithAdjustedStartPoint(Vec newStartPoint) {
		return PathFactory.createLine(newStartPoint, getEndPoint());
	}
	
	public abstract double minDistSquaredTo(BBox b);
	

	


	public abstract double distanceSquared(Vec v) ;
	
	public abstract boolean overlaps(BBox b) ;
	
	@Override
	public Tuple<SimplePath,SimplePath> splitSimpler() {
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
	

	@Override
	public int awtCurSeg(float[] coords) {
		Vec end = getEndPoint();
		coords[0] = (float)end.x; coords[1] = (float)end.y;
		return PathIterator.SEG_LINETO;
	}
	
}
