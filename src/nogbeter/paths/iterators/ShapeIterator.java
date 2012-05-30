package nogbeter.paths.iterators;

import nogbeter.paths.Path;
import nogbeter.paths.compound.Shape;

public class ShapeIterator extends PathIterator<Path>{

	public static final PathSelect select = new PathSelect() {
		
		@Override
		public boolean select(Path p) {
			return p instanceof Shape;
		}
	};
	
	public ShapeIterator(Path root) {
		super(select, root);
	}

}
