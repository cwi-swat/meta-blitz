package nogbeter.paths.factory;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import nogbeter.paths.Path;
import nogbeter.paths.compound.Append;
import nogbeter.paths.compound.ClosedPath;
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
	
	public static Path createAppends(List<Path> paths){
		if(paths.size() == 1){
			return paths.get(0);
		}
		return Append.createAppends(paths);
	}
	
	public static Path createAppends(Path ... paths){
		return createAppends(Arrays.asList(paths));
	}
	
	public static Path createClosedPath(List<Path>  paths){
		Vec begin = paths.get(0).getStartPoint();
		Vec end = paths.get(paths.size()-1).getEndPoint();
		if(begin.isEqError(end)){
			if(!begin.isEq(end)){
				paths.set(0, (SimplePath)  paths.get(0).getWithAdjustedStartPoint(end));
			}
			return new ClosedPath(createAppends(paths));
		} else {
			throw new Error("Not closed!" + createAppends(paths));
		}
	}
	
	public static Path createClosedPath(Path ... paths){
		return createClosedPath(Arrays.asList(paths));
	}
	
	public static Path createSet(Path ... paths){
		if(paths.length == 1){
			return paths[0];
		}
		return new ShapeSet(paths);
	}

	public static Path createShape(Path border, Path ... inside){
		if(inside.length == 0){
			return border;
		}
		return new Shape(border,createSet(inside));
	}

	public static Path createSet(Set<Path> paths) {
		return createSet(paths.toArray(new Path[]{}));
	}

}

