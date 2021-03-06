package paths.paths.iterators;

import paths.paths.paths.QueryPath;
import paths.paths.paths.compound.Append;
import paths.paths.paths.compound.LengthBasedAppend;
import paths.paths.paths.simple.SimplePath;

public class ConnectedIterator extends PathIterator<QueryPath> {

	public ConnectedIterator(PathSelect select, QueryPath root) {
		super(select, root);
	}

	public static final PathSelect select = new PathSelect() {

		@Override
		public boolean select(QueryPath p) {
			return p instanceof Append || p instanceof LengthBasedAppend
					|| p instanceof SimplePath;
		}
	};

	public ConnectedIterator(QueryPath root) {
		super(select, root);
	}

}
