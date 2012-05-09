package nogbeter.paths.compound;

import java.util.List;

import nogbeter.paths.Path;
import nogbeter.paths.PathIndex;
import nogbeter.paths.compound.SplitIndex.SplitChoice;
import nogbeter.paths.results.transformers.IPathIndexTransformer;
import nogbeter.paths.results.transformers.PITransformers;
import nogbeter.paths.results.transformers.PathIndexTupleTransformer;
import nogbeter.paths.results.transformers.TupleTransformers;
import nogbeter.paths.simple.SimplePath;
import nogbeter.points.twod.Vec;
import nogbeter.transform.AffineTransformation;
import bezier.util.Tuple;

public class Append 
			extends CompoundSplittablePath<AppendIndex> {

	public Append(Path left, Path right) {
		super(left, right);
	}

	private static Path createAppend(SimplePath[] paths, int start, int end){
		if(start == end -1){
			return paths[start];
		} else {
			int mid = (start + end)/2;
			return new Append(
						createAppend(paths,start,mid),
						createAppend(paths,mid,end));
		}
	}
	
	public static Path createAppends(SimplePath[] paths) {
		return createAppend(paths, 0, paths.length);
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
	public Append transform(AffineTransformation t) {
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
	public Path getSegment(AppendIndex p) {
		return this;
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




}
