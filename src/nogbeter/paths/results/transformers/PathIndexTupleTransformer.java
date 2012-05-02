package nogbeter.paths.results.transformers;

import nogbeter.paths.PathIndex;

public class PathIndexTupleTransformer<LI extends PathIndex,RI extends PathIndex>{

	 public final IPathIndexTransformer<LI> left;
	 public final IPathIndexTransformer<RI> right;
	 public final boolean doesNothing;
	 
	public PathIndexTupleTransformer(IPathIndexTransformer<LI> left,
			IPathIndexTransformer<RI> right) {
		this.left = left;
		this.right = right;
		doesNothing = left.doesNothing() && right.doesNothing();
	}
	

	 
	 
	
	
}
