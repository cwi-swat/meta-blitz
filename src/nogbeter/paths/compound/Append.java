package nogbeter.paths.compound;

import java.util.List;

import bezier.points.Vec;
import bezier.util.Tuple;
import nogbeter.paths.BestProject;
import nogbeter.paths.BestProjectTup;
import nogbeter.paths.ConnectedPath;
import nogbeter.paths.Path;
import nogbeter.paths.simple.lines.DiagonalLine;
import nogbeter.paths.simple.lines.HorizontalLine;
import nogbeter.paths.simple.lines.VerticalLine;
import nogbeter.paths.simple.nonlinear.Curve;
import nogbeter.util.BBox;
import nogbeter.util.Interval;

public class Append extends ConnectedPath{
	
	public final ConnectedPath left, right;

	public Append(ConnectedPath left, ConnectedPath right, Interval tInterval) {
		super(tInterval);
		this.left = left;
		this.right = right;
	}
	

	@Override
	public Vec getAt(double t) {
		if(t <= left.tInterval.high){
			return left.getAt(t);
		} else {
			return right.getAt(t);
		}
	}

	@Override
	public Vec getTangentAt(double t) {
		if(t <= left.tInterval.high){
			return left.getTangentAt(t);
		} else {
			return right.getTangentAt(t);
		}
	}






}
