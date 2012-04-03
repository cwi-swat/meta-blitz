package bezier.paths.util;

import bezier.paths.Path;

public interface IPathSelector<T> {
	T select(Path p);
}
