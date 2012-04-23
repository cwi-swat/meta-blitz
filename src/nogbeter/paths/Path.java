package nogbeter.paths;

import java.util.List;

import nogbeter.paths.simple.lines.DiagonalLine;
import nogbeter.paths.simple.lines.HorizontalLine;
import nogbeter.paths.simple.lines.VerticalLine;
import nogbeter.paths.simple.nonlinear.NonLinearCurve;
import nogbeter.paths.simple.nonlinear.QuadCurve;
import bezier.points.Vec;
import bezier.util.BBox;
import bezier.util.Tuple;

public abstract class Path<PathParam> {
	
	protected BBox bbox; 
	
	public abstract BBox makeBBox();
	
	public BBox getBBox(){
		if(bbox == null){
			bbox = makeBBox();
		} 
		return bbox;
	}
	

	
	public abstract <OPathParam> Tuple<List<PathParam>,List<OPathParam>> intersection(Path<OPathParam> other);
	public abstract Tuple<List<Double>,List<PathParam>>  intersectionLDiaLine(DiagonalLine lhs);
	public abstract Tuple<List<Double>,List<PathParam>> intersectionLHorLine(HorizontalLine lhs);
	public abstract Tuple<List<Double>,List<PathParam>> intersectionLVerLine(VerticalLine lhs);
	public abstract Tuple<List<Double>,List<PathParam>> intersectionLNonLinear(NonLinearCurve lhs);
	
	public BestProject<PathParam> project(Vec p){
		return project(p,Double.MAX_VALUE);
	}
	
	public abstract BestProject<PathParam> project(Vec p, double bestDist);
	
	public BestProject<PathParam> project(Path other){
		return project(other,Double.MAX_VALUE);
	}
	
	public abstract <OPathParam> BestProject<Tuple<PathParam,OPathParam>> project(Path<OPathParam> other, double bestDist);
	public abstract BestProject<Tuple<Double,PathParam>> projectLDiaLine(DiagonalLine other, double bestDist);
	public abstract BestProject<Tuple<Double,PathParam>> projectLHorLine(HorizontalLine other, double bestDist);
	public abstract BestProject<Tuple<Double,PathParam>> projectLVerLine(VerticalLine other, double bestDist);
	public abstract BestProject<Tuple<Double,PathParam>> projectLNonLinear(NonLinearCurve other, double bestDist);
}
