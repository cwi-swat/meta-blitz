package nogbeter.paths;

import bezier.points.Vec;

public interface IConnectedPath<P extends Path> {
	P getWithAdjustedStartPoint(Vec newStartPoint);
}
