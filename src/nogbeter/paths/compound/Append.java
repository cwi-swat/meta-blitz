package nogbeter.paths.compound;

import java.util.List;

import bezier.points.Vec;
import bezier.util.Tuple;
import nogbeter.paths.Path;
import nogbeter.paths.results.transformers.IPathIndexTransformer;
import nogbeter.paths.simple.SimplePath;

public class Append<LSimp extends Path,RSimp extends Path> 
			extends CompoundSplittablePath<LSimp,RSimp> {

	public Append(LSimp left, RSimp right) {
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
	public Append<LSimp, RSimp> getWithAdjustedStartPoint(Vec newStartPoint) {
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
	public IPathIndexTransformer<SplitIndex> getLeftTransformer() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IPathIndexTransformer<SplitIndex> getRightTransformer() {
		// TODO Auto-generated method stub
		return null;
	}

	
	

}
