package nogbeter.paths;

import bezier.points.Vec;

public abstract class ConnectedPath extends Path<Double> {

	public abstract Vec getStartPoint();
	public abstract Vec getEndPoint();
	public abstract Vec getAt(double t);
	public abstract Vec getTangentAt(double t);
	public abstract ConnectedPath reverse();
	public abstract ConnectedPath getWithAdjustedStartPoint(Vec newStart);

}
