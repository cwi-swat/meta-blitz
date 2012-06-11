package paths.paths.results.transformers;

import paths.paths.paths.PathIndex;

public interface IPathIndexTransformer {
	
	PathIndex transform(PathIndex p);
	boolean doesNothing();

}
