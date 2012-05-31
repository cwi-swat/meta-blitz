package nogbeter.paths.iterators;

import nogbeter.paths.Path;
import nogbeter.paths.compound.Append;
public class AppendIterator extends PathIterator<Append>{


		public static final PathSelect select = new PathSelect() {
			
			@Override
			public boolean select(Path p) {
				return p instanceof Append;
			}
		};
		
		public AppendIterator(Path root) {
			super(select, root);
		}

}
