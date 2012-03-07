package bezier.composite;

import bezier.points.*;
import bezier.util.BBox;

public interface Area {
	
	boolean isInside(Vec p);

	BBox getBBox();
	
}
