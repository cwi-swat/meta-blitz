package nogbeter.paths;

import bezier.points.Vec;
import nogbeter.paths.compound.Append;
import nogbeter.paths.simple.SimplePath;
import nogbeter.paths.simple.lines.DiagonalLine;
import nogbeter.paths.simple.lines.HorizontalLRLine;
import nogbeter.paths.simple.lines.HorizontalLine;
import nogbeter.paths.simple.lines.Line;
import nogbeter.paths.simple.lines.HorizontalRLLine;
import nogbeter.paths.simple.lines.VerticalDULine;
import nogbeter.paths.simple.lines.VerticalLine;
import nogbeter.paths.simple.lines.VerticalUDLine;
import nogbeter.paths.simple.nonlinear.CubicCurve;
import nogbeter.paths.simple.nonlinear.Curve;
import nogbeter.paths.simple.nonlinear.QuadCurve;
import nogbeter.util.Interval;

public class PathFactory {

	public static Line createLine(Vec start, Vec end) {
		return createLine(start, end, Interval.interval01);
	}

	public static Line createLine(Vec start, Vec end,
			Interval interval) {
		return Line.createLine(start, end, interval);
	}

	public static QuadCurve createQuad(Vec p0, Vec p1, Vec p2) {
		return createQuad(p0, p1, p2, Interval.interval01);
	}

	public static QuadCurve createQuad(Vec p0, Vec p1, Vec p2,
			Interval interval) {
		return new QuadCurve(p0, p1, p2, interval);
	}

	public static CubicCurve createCubic(Vec p0, Vec p1, Vec p2, Vec p3) {
		return createCubic(p0, p1, p2, p3, Interval.interval01);
	}

	public static CubicCurve createCubic(Vec p0, Vec p1, Vec p2, Vec p3,
			Interval interval) {
		return new CubicCurve(p0, p1, p2, p3, interval);
	}
	
	public static Path createAppends(SimplePath ... paths){
		return Append.createAppends(paths);
	}

}

