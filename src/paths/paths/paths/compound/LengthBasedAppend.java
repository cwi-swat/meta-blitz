package paths.paths.paths.compound;

import java.util.ArrayList;
import java.util.List;

import paths.paths.paths.Path;
import paths.paths.paths.PathIndex;
import paths.paths.paths.SimplyIndexedPath;
import paths.paths.paths.compound.SplitIndex.SplitChoice;
import paths.paths.paths.simple.SimplePath;
import paths.paths.paths.simple.SimplePathIndex;
import paths.points.angles.AngularInterval;
import paths.points.angles.AngularIntervalFactory;
import paths.points.oned.Interval;
import paths.points.twod.BBox;
import paths.points.twod.Vec;
import paths.transform.IToTransform;
import paths.transform.nonlinear.IDeform;
import paths.transform.nonlinear.ILineTransformer;
import paths.transform.nonlinear.pathdeform.PathDeform;

import util.Tuple;


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

	private boolean isLeft(double t){
		return t < left.tInterval.high;
	}
	
	private boolean isLeft(SimplePathIndex t){
		return isLeft(t.t);
	}
	

	@Override
	public Vec getAtSimply(double t) {
		if(isLeft(t) ){
			return left.getAtSimply(t);
		} else {
			return right.getAtSimply(t);
		}
	}


	@Override
	public Vec getTangentAtSimply(double t) {
		if(isLeft(t) ){
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
	public LengthBasedAppend transform(IToTransform t) {
		return new LengthBasedAppend(left.transform(t), right.transform(t), tInterval);
	}


	@Override
	public Tuple<Path<SimplePathIndex>, Double> normaliseToLength(
			double prevLength) {
		return new Tuple<Path<SimplePathIndex>, Double>(this, right.tInterval.high);
	}


	@Override
	public void getSubPath(SimplePathIndex from, SimplePathIndex to,
			List<Path> result) {
		if(isLeft(from) == isLeft(to)){
			if(isLeft(from)){
				left.getSubPath(from, to, result);
			} else {
				right.getSubPath(from, to, result);
			}
		} else {
			left.getSubPathFrom(from, result);
			right.getSubPathTo(to, result);
		}
	}


	@Override
	public void getSubPathFrom(SimplePathIndex from, List<Path> result) {
		if(isLeft(from)){
			left.getSubPathFrom(from, result);
			result.add(right);
		} else {
			right.getSubPathFrom(from, result);
		}
	}


	@Override
	public void getSubPathTo(SimplePathIndex to, List<Path> result) {
		if(isLeft(to)){
			left.getSubPathTo(to, result);
		} else {
			result.add(left);
			right.getSubPathTo(to, result);
		}
	}


	@Override
	public AngularInterval getAngularInsideInterval(SimplePathIndex t) {
		if(t.t == left.tInterval.high){
				return AngularIntervalFactory.
					createAngularIntervalSingleIfEq(right.getStartTan(),left.getEndTan().negate());
		} else if(t.t < left.tInterval.high){
			return left.getAngularInsideInterval(t);
		} else {
			return right.getAngularInsideInterval(t);
		}
	}


	@Override
	public Vec getStartTan() {
		return left.getStartTan();
	}


	@Override
	public Vec getEndTan() {
		return right.getEndTan();
	}

	@Override
	public Path<PathIndex> reverse() {
		return (Path)new Append(right.reverse(), left.reverse());
	}

	public Vec getArbPoint(){ return getStartPoint();}
	public Vec getArbPointTan(){ return getStartTan();}


	@Override
	public List<Vec> getTangents(SimplePathIndex t) {
		if(t.t == left.tInterval.high|| t.t == right.tInterval.low ){
			List<Vec> res = new ArrayList<Vec>();
			res.addAll(left.getTangents(new SimplePathIndex(left.tInterval.high)));
			res.addAll(right.getTangents(new SimplePathIndex(right.tInterval.low)));
			return res;
		} else if(t.t < left.tInterval.high){
			return left.getTangents(t);
		} else {
			return right.getTangents(t);
		}
	}


	@Override
	public Path deformActual(IDeform p) {
		return new Append(left.deform(p), right.deform(p));
	}


	@Override
	public Path transformApproxLines(ILineTransformer t) {
		return new Append(left.transformApproxLines(t), right.transformApproxLines(t));
	}
	
}
