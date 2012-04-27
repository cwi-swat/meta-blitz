package nogbeter.paths;

import java.util.List;

import nogbeter.paths.simple.lines.DiagonalLine;
import nogbeter.paths.simple.lines.HorizontalLine;
import nogbeter.paths.simple.lines.VerticalLine;
import nogbeter.paths.simple.nonlinear.Curve;
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

	public Tuple<List<Double>, List<PathParam>> intersectionLCurve(
			Curve lhs){
		return intersectionLSplittable(lhs);
	}
	
	public abstract <OPathParam> Tuple<List<OPathParam>, List<PathParam>> intersectionLSplittable(
			SplittablePath<OPathParam> lhs);

	public BestProject<PathParam> project(Vec p) {;
		return project(BestProject.noBestYet,p);
	}
	
	public abstract BestProject<PathParam> project(BestProject<PathParam> best, Vec p);


	public abstract <OPathParam> BestProjectTup<PathParam, OPathParam> project(
			BestProjectTup<PathParam, OPathParam> best,
			Path<OPathParam> other);
	
	public abstract BestProjectTup<Double, PathParam> projectLDiaLine(
			BestProjectTup<Double, PathParam> best,
			DiagonalLine lhs);
	
	public abstract BestProjectTup<Double, PathParam> projectLHorLine(
			BestProjectTup<Double, PathParam> best,
			HorizontalLine lhs);
	
	public abstract BestProjectTup<Double, PathParam> projectLVerLine(
			BestProjectTup<Double, PathParam> best,
			VerticalLine lhs);
	
	public BestProjectTup<Double, PathParam> projectLCurve(
			BestProjectTup<Double, PathParam> best,
			Curve lhs){
		return projectLSplittable(best, lhs);
	}
	
	public abstract <OPathParam> BestProjectTup<OPathParam, PathParam> projectLSplittable(
			BestProjectTup<OPathParam, PathParam> best,
			SplittablePath<OPathParam> lhs);
}
