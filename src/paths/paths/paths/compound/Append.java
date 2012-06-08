package paths.paths.paths.compound;


import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import paths.paths.paths.Path;
import paths.paths.paths.PathIndex;
import paths.paths.paths.compound.SplitIndex.SplitChoice;
import paths.paths.paths.simple.SimplePath;
import paths.paths.paths.simple.SimplePathIndex;
import paths.paths.results.transformers.IPathIndexTransformer;
import paths.paths.results.transformers.PITransformers;
import paths.paths.results.transformers.PathIndexTupleTransformer;
import paths.paths.results.transformers.TupleTransformers;
import paths.points.angles.AngularInterval;
import paths.points.angles.AngularIntervalFactory;
import paths.points.twod.BBox;
import paths.points.twod.Vec;

import transform.IToTransform;
import transform.nonlinear.IDeform;
import transform.nonlinear.ILineTransformer;
import transform.nonlinear.pathdeform.PathDeform;
import util.Tuple;


public class Append 
			extends CompoundSplittablePath<AppendIndex> {

	final PathIndex borderLeft, borderRight;
	
	public Append(Path left, Path right) {
		super(left, right);
		borderLeft = new AppendIndex(SplitChoice.Left, left.maxPathIndex());
		borderRight = new AppendIndex(SplitChoice.Right, right.minPathIndex());
	}

	private static Path createAppend(List<Path> paths, int start, int end){
		if(start == end -1){
			return paths.get(start);
		} else {
			int mid = (start + end)/2;
			return new Append(
						createAppend(paths,start,mid),
						createAppend(paths,mid,end));
		}
	}
	
	public static Path createAppends(List<Path> paths) {
		return createAppend(paths, 0, paths.size());
	}

	@Override
	public Append getWithAdjustedStartPoint(Vec newStartPoint) {
		Path newL = left.getWithAdjustedStartPoint(newStartPoint);
		return new Append(newL,right);
	}

	@Override
	public Vec getStartPoint() {
		return left.getStartPoint();
	}

	@Override
	public Vec getEndPoint() {
		return right.getEndPoint();
	}


	@Override
	public Append transform(IToTransform t) {
		return new Append(left.transform(t),right.transform(t));
	}
	
	@Override
	public String toString() {
		return String.format("(%s + %s)", left,right);
	}

	@Override
	public Tuple<Path<AppendIndex>, Double> normaliseToLength(double prevLength) {
		Tuple<Path, Double> ln = left.normaliseToLength(prevLength);
		Tuple<Path, Double> rn = right.normaliseToLength(ln.r);
		return new Tuple<Path<AppendIndex>, Double>(new Append(ln.l, rn.l),rn.r);
	}

	@Override
	public IPathIndexTransformer<AppendIndex> getLeftTransformer() {
		return PITransformers.appendLeft;
	}

	@Override
	public IPathIndexTransformer<AppendIndex> getRightTransformer() {
		return PITransformers.appendRight;
	}

	@Override
	public <B extends PathIndex> PathIndexTupleTransformer<AppendIndex, B> getLeftLeftTransformer() {
		return (PathIndexTupleTransformer<AppendIndex, B>) TupleTransformers.aleftLeft;
	}

	@Override
	public <B extends PathIndex> PathIndexTupleTransformer<AppendIndex, B> getLeftRightTransformer() {
		return (PathIndexTupleTransformer<AppendIndex, B>) TupleTransformers.aleftRight;
	}

	@Override
	public <B extends PathIndex> PathIndexTupleTransformer<B, AppendIndex> getRightLeftTransformer() {
		return  (PathIndexTupleTransformer<B, AppendIndex>) TupleTransformers.arightLeft;
	}

	@Override
	public <B extends PathIndex> PathIndexTupleTransformer<B, AppendIndex> getRightRightTransformer() {
		return  (PathIndexTupleTransformer<B, AppendIndex>) TupleTransformers.arightRight;
	}

	@Override
	public void getSubPath(AppendIndex from, AppendIndex to, List<Path> res) {
		if(from.choice == to.choice){
			if(from.choice == SplitChoice.Left){
				left.getSubPath(from.next, to.next,res);
			} else {
				right.getSubPath(from.next, to.next,res);
			}
		} else {
			left.getSubPathFrom(from.next,res);
			right.getSubPathTo(to.next, res);
		}
	}
	
	@Override
	public void getSubPathFrom(AppendIndex from, List<Path> result) {
		if(from.choice == SplitChoice.Left){
			left.getSubPathFrom(from.next,result);
			result.add(right);
		} else {
			right.getSubPathFrom(from.next,result);
		}
		
	}

	@Override
	public void getSubPathTo(AppendIndex to, List<Path> result) {
		if(to.choice == SplitChoice.Left){
			left.getSubPathTo(to.next,result);
		} else {
			result.add(left);
			right.getSubPathTo(to.next,result);
		}
	}


	@Override
	public AngularInterval getAngularInsideInterval(AppendIndex t) {
		if(t.isEq(borderLeft) || t.isEq(borderRight)){
			return AngularIntervalFactory.
					createAngularIntervalSingleIfEq(right.getStartTan(), 
							left.getEndTan().negate()); 
		} else if(t.choice == SplitChoice.Left){
			return left.getAngularInsideInterval(t.next);
		} else {
			return right.getAngularInsideInterval(t.next);
		}
	}
	

	@Override
	public List<Vec> getTangents(AppendIndex t) {
		if(t.isEq(borderLeft) || t.isEq(borderRight)){
			List<Vec> res = new ArrayList<Vec>();
			res.addAll(left.getTangents(borderLeft.next));
			res.addAll(right.getTangents(borderRight.next));
			return res;
		} else if(t.choice == SplitChoice.Left){
			return left.getTangents(t.next);
		} else {
			return right.getTangents(t.next);
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
	public void getClosedSegmentsNotInSet(Set<Path> segments, List<ClosedPath> res){
		throw new Error("Cannot contain segment");
	}

	@Override
	public Path<PathIndex> reverse() {
		return (Path)new Append(right.reverse(), left.reverse());
	}
	
	public Vec getArbPoint(){ return getStartPoint();}

	public Vec getArbPointTan(){ return getStartTan();}

	@Override
	public Append deformActual(IDeform p) {
		return new Append(left.deform(p), right.deform(p));
	}



	@Override
	public Path transformApproxLines(ILineTransformer t) {
		return new Append(left.transformApproxLines(t), right.transformApproxLines(t));
	}


}
