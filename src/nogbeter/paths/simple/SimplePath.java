package nogbeter.paths.simple;

import bezier.util.STuple;
import nogbeter.paths.ConnectedPath;

public abstract class SimplePath extends ConnectedPath{
	public abstract STuple<SimplePath> splitSimpler();
}
