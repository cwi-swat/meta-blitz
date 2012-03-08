package bezier.composite;

import bezier.points.*;
import bezier.util.BBox;
import bezier.util.HasBBox;

public interface Area extends HasBBox{
	
	boolean isInside(Vec p);
	
}
