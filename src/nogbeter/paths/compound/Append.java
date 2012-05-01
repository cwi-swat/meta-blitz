package nogbeter.paths.compound;

import java.util.List;

import bezier.points.Vec;
import bezier.util.Tuple;
import nogbeter.paths.IConnectedPath;
import nogbeter.paths.Path;
import nogbeter.paths.simple.SimplePath;

public class Append<LSimp extends Path,RSimp extends Path> 
			extends SplittableCompoundPath<LSimp,RSimp> 
			implements IConnectedPath<Append<LSimp,RSimp>>{

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
		Path newL = ((IConnectedPath)left).getWithAdjustedStartPoint(newStartPoint);
		return new Append(newL,right);
	}

	
	

}
