package paths.paths.results.transformers;

import paths.paths.paths.PathIndex;

public interface IPathIndexTransformer<PI extends PathIndex> {
	
	PI transform(PathIndex p);
	boolean doesNothing();

}
