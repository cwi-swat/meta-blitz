package nogbeter.paths.simple;

import bezier.points.Vec;
import nogbeter.paths.ConnectedPath;
import nogbeter.paths.simple.lines.DiagonalLine;
import nogbeter.paths.simple.lines.HorizontalLRLine;
import nogbeter.paths.simple.lines.HorizontalLine;
import nogbeter.paths.simple.lines.ActualLine;
import nogbeter.paths.simple.lines.HorizontalRLLine;
import nogbeter.paths.simple.lines.VerticalDULine;
import nogbeter.paths.simple.lines.VerticalLine;
import nogbeter.paths.simple.lines.VerticalUDLine;
import nogbeter.paths.simple.nonlinear.CubicCurve;
import nogbeter.paths.simple.nonlinear.NonLinearCurve;
import nogbeter.paths.simple.nonlinear.QuadCurve;
import nogbeter.util.InclusiveInterval;

public class SimplePathFactory {

	public static ActualLine createLine(Vec start, Vec end) {
		return createLine(start, end, InclusiveInterval.interval01);
	}

	public static ActualLine createLine(Vec start, Vec end,
			InclusiveInterval interval) {
		if (start.x == end.x) {
			if (start.y < end.y) {
				return new VerticalUDLine(start.x, new InclusiveInterval(
						start.y, end.y), interval);
			} else {
				return new VerticalDULine(start.x, new InclusiveInterval(end.y,
						start.y), interval);
			}
		} else if (start.y == end.y) {
			if (start.x < end.x) {
				return new HorizontalLRLine(new InclusiveInterval(start.x,
						end.x), start.y, interval);
			} else {
				return new HorizontalRLLine(new InclusiveInterval(start.x,
						end.x), start.y, interval);
			}
		} else {
			return new DiagonalLine(start, end, interval);
		}
	}

	public static SimplePath createQuad(Vec p0, Vec p1, Vec p2) {
		return createQuad(p0, p1, p2, InclusiveInterval.interval01);
	}

	public static SimplePath createQuad(Vec p0, Vec p1, Vec p2,
			InclusiveInterval interval) {
		return new QuadCurve(p0, p1, p2, interval);
	}

	public static SimplePath createCubic(Vec p0, Vec p1, Vec p2, Vec p3) {
		return createCubic(p0, p1, p2, p3, InclusiveInterval.interval01);
	}

	public static SimplePath createCubic(Vec p0, Vec p1, Vec p2, Vec p3,
			InclusiveInterval interval) {
		return new CubicCurve(p0, p1, p2, p3, interval);
	}

}
