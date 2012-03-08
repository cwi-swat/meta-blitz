package bezier.segment.curve;

import java.awt.Shape;
import java.util.List;

import bezier.composite.Path;
import bezier.points.Matrix;
import bezier.points.Vec;
import bezier.segment.LengthMap;
import bezier.util.BBox;
import bezier.util.HasBBox;
import bezier.util.Tuple;

public interface Curve extends HasBBox{

	boolean isLine();
	Line getLine();
	boolean overlapsWith(BBox r);
	Tuple<Curve,Curve> split(double t);
	Tuple<Curve,Curve> splitSimpler();
	boolean fastIntersectionCheck(Curve other);
	Curve getWithAdjustedStartPoint(Vec newStartPoint);
	Vec getStartPoint();
	Vec getEndPoint();
	Vec getAt(double t);
	Vec getTangentAt(double t);
	List<Curve> makeMonotomous();
	void fillLengthMap(LengthMap map, double samplesDirect);
	List<Curve> projectOn(Path p, LengthMap lm);
	
	Curve transform(Matrix m);
	Curve lift();
	int nrBelow(Vec p);
	
	Curve reverse();
	
	String toString();
	int currentSegment(float[] coords);
}
