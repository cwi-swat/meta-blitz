package nogbeter.paths.iterators;

import nogbeter.paths.Path;

public class ClosedPathIterator extends PathIterator<Path>{

	public static final PathSelect select = new PathSelect() {
		
		@Override
		public boolean select(Path p) {
			return p.isClosed();
		}
	};
	
	public ClosedPathIterator(Path root) {
		super(select, root);
	}

}
