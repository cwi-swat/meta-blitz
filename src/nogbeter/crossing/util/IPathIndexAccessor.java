package nogbeter.crossing.util;

import nogbeter.crossing.Crossing;
import nogbeter.paths.PathIndex;

public interface IPathIndexAccessor<P extends PathIndex> {
	 P getPathIndex(Crossing cross);
}
