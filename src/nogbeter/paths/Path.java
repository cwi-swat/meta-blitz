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

	public BestProject<PathParam> project(Vec p) {;
		return project(BestProject.noBestYet,p);
	}
	
	public abstract BestProject<PathParam> project(BestProject best, Vec p);

	public <OPathParam> BestProject<Tuple<PathParam, OPathParam>> project(
			Path<OPathParam> other) {
		return project(BestProject.noBestYet,other);
	}

	public abstract <OPathParam> BestProject<Tuple<PathParam, OPathParam>> project(
			BestProject best,
			Path<OPathParam> other);
	
	public abstract BestProject<Tuple<Double, PathParam>> projectLDiaLine(
			BestProject best,
			DiagonalLine lhs);
	
	public abstract BestProject<Tuple<Double, PathParam>> projectLHorLine(
			BestProject best,
			HorizontalLine lhs);
	
	public abstract BestProject<Tuple<Double, PathParam>> projectLVerLine(
			BestProject best,
			VerticalLine lhs);
	
	public abstract BestProject<Tuple<Double, PathParam>> projectLNonLinear(
			BestProject best,
			NonLinearCurve lhs);
}
