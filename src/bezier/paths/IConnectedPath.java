package bezier.paths;

import bezier.points.Vec;

public interface IConnectedPath{
	
	public abstract Vec getStartPoint();
	public abstract Vec getEndPoint();
	public Vec getAt(double t);
	public Vec getTangentAt(double t);
	public abstract IConnectedPath reverse();
	public abstract IConnectedPath getWithAdjustedStartPoint(Vec newStart);
	public abstract boolean isClosed();
	
}
