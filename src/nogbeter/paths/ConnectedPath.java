package nogbeter.paths;

import bezier.points.Vec;

public abstract class ConnectedPath extends Path<Double> {

	public Vec getStartPoint(){
		return getAt(0.0);
	}
	
	public Vec getEndPoint(){
		return getAt(1.0);
	}
	
	public abstract Vec getAt(double t);
	public abstract Vec getTangentAt(double t);
	public abstract ConnectedPath getWithAdjustedStartPoint(Vec newStart);

}
