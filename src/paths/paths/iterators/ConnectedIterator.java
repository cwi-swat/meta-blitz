package paths.paths.iterators;

import paths.paths.paths.Path;
import paths.paths.paths.compound.Append;
import paths.paths.paths.compound.LengthBasedAppend;
import paths.paths.paths.simple.SimplePath;
public class ConnectedIterator extends PathIterator<Path>{


		public ConnectedIterator(PathSelect select, Path root) {
			super(select, root);
		}

		public static final PathSelect select = new PathSelect() {
			
			@Override
			public boolean select(Path p) {
				return p instanceof Append || p instanceof LengthBasedAppend || p instanceof SimplePath;
			}
		};
		
		public ConnectedIterator(Path root) {
			super(select, root);
		}

}
