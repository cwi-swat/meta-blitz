package nogbeter.paths;

import nogbeter.util.BBox;
import bezier.util.STuple;

public interface SplittablePath<PathParam> {
	
	public BBox getBBox();
	public STuple<Path<PathParam>> splitSimpler() ;
	public Path<PathParam> getPath();
}
