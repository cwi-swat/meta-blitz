package nogbeter.paths.compound;

import java.util.List;

import bezier.util.Tuple;
import nogbeter.paths.Path;
import nogbeter.paths.PathIndex;
import nogbeter.paths.results.transformers.IPathIndexTransformer;
import nogbeter.paths.results.transformers.PathIndexTupleTransformer;
import nogbeter.paths.simple.SimplePath;
import nogbeter.points.twod.Vec;
import nogbeter.transform.AffineTransformation;

public class Append 
			extends CompoundSplittablePath {

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


}
