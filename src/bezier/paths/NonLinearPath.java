package bezier.paths;

import bezier.util.BBox;
import bezier.util.STuple;

public interface NonLinearPath extends Path{
	STuple<Path> split(double t);
	STuple<Path> splitSimpler();
	BBox getBBox();
}
