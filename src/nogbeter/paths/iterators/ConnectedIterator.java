package nogbeter.paths.iterators;

import nogbeter.paths.Path;
import nogbeter.paths.compound.Append;
import nogbeter.paths.compound.LengthBasedAppend;
import nogbeter.paths.simple.SimplePath;
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
