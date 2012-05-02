package nogbeter.paths.factory;

import java.util.Set;

import nogbeter.paths.Path;
import nogbeter.paths.compound.Append;
import nogbeter.paths.compound.Shape;
import nogbeter.paths.compound.ShapeSet;
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
import nogbeter.points.oned.Interval;
import nogbeter.points.twod.Vec;

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
	
	public static Path createSet(Path ... paths){
		return new ShapeSet(paths);
	}

	public static Path createShape(Path border, Path ... inside){
		return new Shape(border,createSet(inside));
	}

	public static Path createSet(Set<Path> paths) {
		return createSet(paths.toArray(new Path[]{}));
	}
	
}
