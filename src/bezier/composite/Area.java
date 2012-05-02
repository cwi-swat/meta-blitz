package bezier.composite;

import nogbeter.points.twod.Vec;
import bezier.util.HasBBox;

public interface Area extends HasBBox{
	
	boolean isInside(Vec p);
	
}
