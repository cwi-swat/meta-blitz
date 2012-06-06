package paths.paths.iterators;

import paths.paths.paths.Path;
import paths.paths.paths.simple.Line;

public class LineIterator extends PathIterator<Line>{
	public static final PathSelect select = new PathSelect() {
		
		@Override
		public boolean select(Path p) {
			return p instanceof Line;
		}
	};
	
	
	public LineIterator(Path root) {
		super(select, root);
	}
}
