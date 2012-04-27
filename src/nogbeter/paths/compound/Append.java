package nogbeter.paths.compound;

import java.util.List;

import bezier.points.Vec;
import bezier.util.STuple;
import bezier.util.Tuple;
import bezier.util.Util;
import nogbeter.paths.BestProject;
import nogbeter.paths.BestProjectTup;
import nogbeter.paths.Path;
import nogbeter.paths.PathIndex;
import nogbeter.paths.simple.SimplePath;
import nogbeter.paths.simple.SimplePathIndex;
import nogbeter.paths.simple.lines.DiagonalLine;
import nogbeter.paths.simple.lines.HorizontalLine;
import nogbeter.paths.simple.lines.VerticalLine;
import nogbeter.paths.simple.nonlinear.Curve;
import nogbeter.util.BBox;
import nogbeter.util.Interval;

public class Append<LSimp extends Path,RSimp extends Path> extends SplittableCompound<LSimp,RSimp>{

	public Append(LSimp left, RSimp right) {
		super(left, right);
	}
	

}
