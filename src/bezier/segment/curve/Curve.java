package bezier.segment.curve;

import java.awt.Shape;
import java.util.List;

import bezier.composite.Path;
import bezier.points.Matrix;
import bezier.points.Vec;
import bezier.segment.LengthMap;
import bezier.util.BBox;
import bezier.util.Tuple;

public interface Curve {

	abstract boolean isLine();
	abstract Line getLine();
	abstract BBox getBBox();
	abstract boolean overlapsWith(BBox r);
	abstract Tuple<Curve,Curve> split(double t);
	abstract Tuple<Curve,Curve> splitSimpler();
	abstract boolean fastIntersectionCheck(Curve other);
	abstract Curve getWithAdjustedStartPoint(Vec newStartPoint);
	abstract Vec getStartPoint();
	abstract Vec getEndPoint();
	abstract Vec getAt(double t);
	abstract Vec getTangentAt(double t);
	abstract List<Curve> makeMonotomous();
	abstract void fillLengthMap(LengthMap map, double samplesDirect);
	abstract List<Curve> projectOn(Path p, LengthMap lm);
	
	abstract Curve transform(Matrix m);
	abstract Curve lift();
	abstract int nrBelow(Vec p);
	
	abstract Curve reverse();
	
	abstract Shape toAWT() ;
	abstract String toString();
}
