package paths.paths.iterators;

import paths.paths.paths.Path;
import paths.paths.paths.simple.SimplePath;

public class SimplePathIterator extends PathIterator<SimplePath>{

	public static final PathSelect select = new PathSelect() {
		
		@Override
		public boolean select(Path p) {
			return p instanceof SimplePath;
		}
	};
	
	public SimplePathIterator(Path root) {
		super(select, root);
	}

}
