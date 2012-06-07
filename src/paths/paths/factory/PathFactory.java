package paths.paths.factory;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import paths.paths.paths.Path;
import paths.paths.paths.compound.Append;
import paths.paths.paths.compound.ClosedPath;
import paths.paths.paths.compound.NotClosedException;
import paths.paths.paths.compound.Shape;
import paths.paths.paths.compound.ShapeSet;
import paths.paths.paths.simple.Line;
import paths.paths.paths.simple.SimplePath;
import paths.paths.paths.simple.curve.CubicCurve;
import paths.paths.paths.simple.curve.Curve;
import paths.paths.paths.simple.curve.QuadCurve;
import paths.points.oned.Interval;
import paths.points.twod.Vec;


public class PathFactory {

	public static Line createLine(Vec start, Vec end) {
		return createLine(start, end, Interval.interval01);
	}

	public static Line createLine(Vec start, Vec end,
			Interval interval) {
		return new Line(start, end, interval);
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
	
	public static ClosedPath createClosedPathUnsafe(Path p) throws NotClosedException{
		Vec begin = p.getStartPoint();
		Vec end = p.getEndPoint();
		if(begin.isEqError(end)){
			if(!begin.isEq(end)){
				p = p.getWithAdjustedStartPoint(end);
			}
			ClosedPath r = new ClosedPath(p);
			if(r.isSelfCrossing()){
				return null;
			}
			return r;
		} else {
			throw new NotClosedException(p);
		}
	}
	
	public static ClosedPath createClosedPath(Path p){
		ClosedPath r;
		try {
			r = createClosedPathUnsafe(p);
			if(r == null){
				throw new Error("Path is self intersecting! Not a valid closed path:" + p );
			} else if(!r.isDefindedClockwise()){
				throw new Error("Closed path not defined clockwise:" + p );
			} else {
				return r;
			}
		} catch (NotClosedException e) {
			throw new Error("Path is not closed!");
		}
	}
	
	public static ClosedPath createClosedPathUnsafe(List<Path>  paths) throws NotClosedException{
		return createClosedPathUnsafe(createAppends(paths));
	}
	
	public static ClosedPath createClosedPath(List<Path>  paths){
		return createClosedPath(createAppends(paths));
		
	}
	
	public static ClosedPath createClosedPath(Path ... paths) {
		return createClosedPath(Arrays.asList(paths));
	}
	
	
	public static Path createSet(List<Path> paths){
		if(paths.size() == 1){
			return paths.get(0);
		}
		return new ShapeSet(paths);
	}

	
	public static Path createSet(Path ... paths){
		return createSet(Arrays.asList(paths));
	}

	public static Path createShape(Path border, Path ... inside){
		return createShape(border, Arrays.asList(inside));
	}

	public static Path createShape(Path border, List<Path> inside) {
		if(inside.size() == 0){
			return border;
		}
		return new Shape(border,createSet(inside));
	}

}

