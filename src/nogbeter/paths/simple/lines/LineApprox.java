package nogbeter.paths.simple.lines;

import java.util.ArrayList;
import java.util.List;

import nogbeter.paths.BestProject;
import nogbeter.paths.ConnectedPath;
import nogbeter.paths.Path;
import nogbeter.paths.simple.SimplePath;
import nogbeter.paths.simple.nonlinear.NonLinearCurve;
import bezier.points.Vec;
import bezier.util.BBox;
import bezier.util.STuple;
import bezier.util.Tuple;

public class LineApprox extends Line{

	public LineApprox(ActualLine approx, double startT, double endT) {
		this.approx = approx;
		this.tStart = startT;
		this.tEnd = endT;
	}

	final ActualLine approx;
	final double tStart, tEnd;
	
	@Override
	public Vec getStartPoint() {
		return approx.getStartPoint();
	}

	@Override
	public Vec getEndPoint() {
		return approx.getEndPoint();
	}

	@Override
	public Vec getAt(double t) {
		return approx.getAt(t);
	}

	@Override
	public Vec getTangentAt(double t) {
		return approx.getAt(t);
	}

	@Override
	public ConnectedPath reverse() {
		return new LineApprox((ActualLine)approx.reverse(),tEnd,tStart);
	}

	private double convertBack(double t){
		return t * (tEnd - tStart) + tStart;
	}
	
	private List<Double> convertBack(List<Double> rr){
		List<Double> res = new ArrayList<Double>(rr.size());
		for(double d : rr){
			res.add(convertBack(d));
		}
		return res;
	}
	
	private  <OPathParam> Tuple<List<Double>, List<OPathParam>> convertBackLeft(Tuple<List<Double>, List<OPathParam>> arg){
		return new Tuple<List<Double>,List<OPathParam>>(convertBack(arg.l),arg.r);
	}
	
	private  <OPathParam> Tuple<List<OPathParam>, List<Double>> convertBackRight(Tuple<List<OPathParam>, List<Double>> arg){
		return new Tuple<List<OPathParam>,List<Double>>(arg.l,convertBack(arg.r));
	}
	
	@Override
	public <OPathParam> Tuple<List<Double>, List<OPathParam>> intersection(
			Path<OPathParam> other) {
		return convertBackLeft(approx.intersection(other));
	}

	@Override
	public Tuple<List<Double>, List<Double>> intersectionLDiaLine(
			DiagonalLine lhs) {
		return convertBackRight(approx.intersectionLDiaLine(lhs));
	}

	@Override
	public Tuple<List<Double>, List<Double>> intersectionLHorLine(
			HorizontalLine lhs) {
		return convertBackRight(approx.intersectionLHorLine(lhs));
	}

	@Override
	public Tuple<List<Double>, List<Double>> intersectionLVerLine(
			VerticalLine lhs) {
		return convertBackRight(approx.intersectionLVerLine(lhs));
	}
	
	public Tuple<List<Double>,List<Double>> intersectionLNonLinear(NonLinearCurve lhs){
		return convertBackRight(approx.intersectionLNonLinear(lhs));
	}

	@Override
	public STuple<SimplePath> splitSimpler() {
		STuple<SimplePath> insp = approx.splitSimpler();
		double tMid = (tStart + tEnd)*0.5;
		return new STuple<SimplePath>(new LineApprox((ActualLine)insp.l,tStart,tMid),
				new LineApprox((ActualLine)insp.r,tMid,tEnd));
	}

	@Override
	public ConnectedPath getWithAdjustedStartPoint(Vec newStart) {
		return new LineApprox((ActualLine)approx.getWithAdjustedStartPoint(newStart),tStart,tEnd);
	}

	@Override
	public BBox makeBBox() {
		return approx.makeBBox();
	}

	@Override
	public BestProject<Double> project(Vec p, double bestDist) {
		BestProject<Double> in = approx.project(p,bestDist);
		if(in != BestProject.noResult){
			return new BestProject(in.distSquared,convertBack(in.t));
		} else {
			return in;
		}
	}

}
