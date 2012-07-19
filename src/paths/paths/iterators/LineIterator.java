package paths.paths.iterators;

import paths.paths.paths.QueryPath;
import paths.paths.paths.simple.Line;

public class LineIterator extends PathIterator<Line> {
	public static final PathSelect select = new PathSelect() {

		@Override
		public boolean select(QueryPath p) {
			return p instanceof Line;
		}
	};

	public LineIterator(QueryPath root) {
		super(select, root);
	}
}
