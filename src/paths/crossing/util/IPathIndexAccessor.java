package paths.crossing.util;

import paths.crossing.Crossing;
import paths.paths.paths.PathIndex;

public interface IPathIndexAccessor<P extends PathIndex> {
	 P getPathIndex(Crossing cross);
}
