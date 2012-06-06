package paths.paths.iterators;

import paths.paths.paths.Path;
import paths.paths.paths.compound.Shape;

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
