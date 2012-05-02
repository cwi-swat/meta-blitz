package nogbeter.paths.results.transformers;

import nogbeter.paths.PathIndex;

public interface IPathIndexTransformer<PI extends PathIndex> {
	
	PI transform(PathIndex p);
	boolean doesNothing();

}
