package bezier.paths;

import bezier.points.Vec;

public interface Path {
	Vec getStartPoint();
	Vec getEndPoint();
	Vec getAt(double t);
	Vec getTangentAt(double t);
}
