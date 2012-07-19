package paths.paths.iterators;

import paths.paths.paths.QueryPath;
import paths.paths.paths.compound.ClosedPath;

public class ClosedPathIterator extends PathIterator<QueryPath> {

	public static final PathSelect select = new PathSelect() {

		@Override
		public boolean select(QueryPath p) {
			return p instanceof ClosedPath;
		}
	};

	public ClosedPathIterator(QueryPath root) {
		super(select, root);
	}

}
