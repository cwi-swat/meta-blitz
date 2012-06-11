package paths.paths.results.transformers;

import paths.paths.paths.PathIndex;
import paths.paths.results.transformers.PITransformers;
public class PathIndexTupleTransformer{

	
	 public final IPathIndexTransformer left;
	 public final IPathIndexTransformer right;
	 public final boolean doesNothing;
	 
	public PathIndexTupleTransformer(IPathIndexTransformer left,
			IPathIndexTransformer right) {
		this.left = left;
		this.right = right;
		doesNothing = left.doesNothing() && right.doesNothing();
	}
	

	 
	 
	
	
}
