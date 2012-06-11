package paths.paths.iterators;

import paths.paths.paths.Path;
import paths.paths.paths.compound.ClosedPath;

public class ClosedPathIterator extends PathIterator<Path> {

	public static final PathSelect select = new PathSelect() {

		@Override
		public boolean select(Path p) {
			return p instanceof ClosedPath;
		}
	};

	public ClosedPathIterator(Path root) {
		super(select, root);
	}

}
