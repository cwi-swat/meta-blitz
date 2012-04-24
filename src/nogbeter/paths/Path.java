package nogbeter.paths;

import java.util.List;

import nogbeter.paths.simple.lines.DiagonalLine;
import nogbeter.paths.simple.lines.HorizontalLine;
import nogbeter.paths.simple.lines.VerticalLine;
import nogbeter.paths.simple.nonlinear.NonLinearCurve;
import nogbeter.paths.simple.nonlinear.QuadCurve;
import nogbeter.util.BBox;
import bezier.points.Vec;
import bezier.util.Tuple;

public abstract class Path<PathParam> {

	protected BBox bbox;

	public abstract BBox makeBBox();

	public BBox getBBox() {
		if (bbox == null) {
			bbox = makeBBox();
		}
		return bbox;
	}

	public abstract <OPathParam> Tuple<List<PathParam>, List<OPathParam>> intersection(
			Path<OPathParam> other);

	public abstract Tuple<List<Double>, List<PathParam>> intersectionLDiaLine(
			DiagonalLine lhs);

	public abstract Tuple<List<Double>, List<PathParam>> intersectionLHorLine(
			HorizontalLine lhs);

	public abstract Tuple<List<Double>, List<PathParam>> intersectionLVerLine(
			VerticalLine lhs);

	public abstract Tuple<List<Double>, List<PathParam>> intersectionLNonLinear(
			NonLinearCurve lhs);

	public BestProject<PathParam> project(Vec p) {
		BestProject<PathParam> best = new BestProject<PathParam>();
		project(best,p);
		return best;
	}
	
	public abstract void project(BestProject<PathParam> best, Vec p);

//	public <OPathParam> BestProject<Tuple<PathParam, OPathParam>> project(
//			Path<OPathParam> other) {
//		return project(other, Double.MAX_VALUE);
//	}
//
//	public abstract <OPathParam> BestProject<Tuple<PathParam, OPathParam>> project(
//			Path<OPathParam> other, double bestDist);
//
//	public abstract BestProject<Tuple<Double, PathParam>> projectLDiaLine(
//			DiagonalLine other, double bestDist);
//
//	public abstract BestProject<Tuple<Double, PathParam>> projectLHorLine(
//			HorizontalLine other, double bestDist);
//
//	public abstract BestProject<Tuple<Double, PathParam>> projectLVerLine(
//			VerticalLine other, double bestDist);
//
//	public abstract BestProject<Tuple<Double, PathParam>> projectLNonLinear(
//			NonLinearCurve other, double bestDist);
}
