package paths.paths.iterators;

import paths.paths.paths.QueryPath;
import paths.paths.paths.simple.SimplePath;

public class SimplePathIterator extends PathIterator<SimplePath> {

	public static final PathSelect select = new PathSelect() {

		@Override
		public boolean select(QueryPath p) {
			return p instanceof SimplePath;
		}
	};

	public SimplePathIterator(QueryPath root) {
		super(select, root);
	}

}
