package nogbeter.paths.compound;

import bezier.util.Tuple;
import nogbeter.paths.Path;
import nogbeter.paths.SimplyIndexedPath;
import nogbeter.paths.simple.SimplePath;
import nogbeter.paths.simple.SimplePathIndex;
import nogbeter.points.oned.Interval;
import nogbeter.points.twod.BBox;
import nogbeter.points.twod.Vec;
import nogbeter.transform.AffineTransformation;

public class LengthBasedAppend extends SimplyIndexedPath{

	public final SimplyIndexedPath left;
	public final SimplyIndexedPath right;

	
	public LengthBasedAppend(SimplyIndexedPath left, SimplyIndexedPath right,Interval tInterval) {
		super(tInterval);
		this.left = left;
		this.right = right;
	}


	@Override
	public BBox makeBBox() {
		return left.getBBox().union(right.getBBox());
	}


	@Override
	public Vec getAtSimply(double t) {
		if(t < left.tInterval.high ){
			return left.getAtSimply(t);
		} else {
			return right.getAtSimply(t);
		}
	}


	@Override
	public Vec getTangentAtSimply(double t) {
		if(t < left.tInterval.high ){
			return left.getTangentAtSimply(t);
		} else {
			return right.getTangentAtSimply(t);
		}
	}


	@Override
	public Tuple<Path, Path> splitSimpler() {
		return new Tuple<Path,Path>(left,right);
	}


	@Override
	public int nrChildren() {
		return 2;
	}


	@Override
	public Path getChild(int i) {
		return i == 0 ? left : right;
	}


	@Override
	public LengthBasedAppend getWithAdjustedStartPoint(Vec newStartPoint) {
		return new LengthBasedAppend(left.getWithAdjustedStartPoint(newStartPoint), right,tInterval);
	}


	@Override
	public Vec getStartPoint() {
		return left.getStartPoint();
	}


	@Override
	public Vec getEndPoint() {
		return right.getStartPoint();
	}


	@Override
	public LengthBasedAppend transform(AffineTransformation t) {
		return new LengthBasedAppend(left.transform(t), right.transform(t), tInterval);
	}


	@Override
	public Tuple<Path<SimplePathIndex>, Double> normaliseToLength(
			double prevLength) {
		return new Tuple<Path<SimplePathIndex>, Double>(this, right.tInterval.high);
	}


	
}
