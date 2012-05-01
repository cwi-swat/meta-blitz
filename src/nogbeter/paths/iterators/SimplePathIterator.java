package nogbeter.paths.iterators;

import nogbeter.paths.Path;
import nogbeter.paths.simple.SimplePath;

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
