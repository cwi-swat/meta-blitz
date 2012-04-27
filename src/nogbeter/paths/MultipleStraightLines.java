package nogbeter.paths;

import bezier.util.STuple;
import nogbeter.paths.simple.SimplePath;
import nogbeter.util.Interval;

public abstract class MultipleStraightLines extends ConnectedPath {

	public MultipleStraightLines(Interval tInterval) {
		super(tInterval);
	}

	public abstract STuple<SimplePath> splitSimpler() ;
}
