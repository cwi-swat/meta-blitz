package nogbeter.paths.simple;


import bezier.points.Vec;
import bezier.util.BBox;
import nogbeter.paths.ConnectedPath;
import nogbeter.paths.simple.lines.DiagonalLine;
import nogbeter.paths.simple.lines.HorizontalLine;
import nogbeter.paths.simple.lines.ActualLine;
import nogbeter.paths.simple.lines.VerticalLine;
import nogbeter.paths.simple.nonlinear.CubicCurve;
import nogbeter.paths.simple.nonlinear.NonLinearCurve;
import nogbeter.paths.simple.nonlinear.QuadCurve;

public class SimplePathFactory {

	public static ActualLine createLine(Vec start, Vec end){
		if(start.x == end.x){
			return new VerticalLine(start, end.y);
		} else if(start.y == end.y){
			return new HorizontalLine(start, end.x);
		} else {
			return new DiagonalLine(start, end);
		}
	}


	public static SimplePath createQuad(Vec p0, Vec p1, Vec p2) {
		return new QuadCurve(p0, p1, p2);
	}

	public static SimplePath createCubic(Vec p0, Vec p1, Vec p2, Vec p3) {
		return new CubicCurve(p0, p1, p2, p3);
	}

}
