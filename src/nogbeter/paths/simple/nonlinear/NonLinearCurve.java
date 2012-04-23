package nogbeter.paths.simple.nonlinear;

import java.util.Collections;
import java.util.List;

import javax.print.attribute.standard.MediaSize.Other;

import bezier.points.Vec;
import bezier.util.BBox;
import bezier.util.STuple;
import bezier.util.Tuple;
import bezier.util.Util;

import nogbeter.paths.BestProject;
import nogbeter.paths.ConnectedPath;
import nogbeter.paths.Path;
import nogbeter.paths.simple.SimplePath;
import nogbeter.paths.simple.lines.DiagonalLine;
import nogbeter.paths.simple.lines.HorizontalLine;
import nogbeter.paths.simple.lines.VerticalLine;

public abstract class NonLinearCurve extends SimplePath{
	
	STuple<SimplePath> simpler;
	
	public NonLinearCurve(double tStart, double tEnd) {
		this.tStart = tStart;
		this.tEnd = tEnd;
	}

	final double tStart, tEnd;
	
	protected List<Double> xyRoots;
	
	abstract List<Double> getXYRoots();
	
	public void setXYRoots(){
		if(xyRoots == null){
			xyRoots = getXYRoots();
			Collections.sort(xyRoots);
		}
	}
	
	@SuppressWarnings("unchecked")
	public STuple<SimplePath> makeMonotomous(){
		STuple<NonLinearCurve> result = split(xyRoots.get(0));
		result.l.xyRoots = Collections.EMPTY_LIST;
		result.r.xyRoots = xyRoots.subList(1, xyRoots.size());
		return (STuple)result;
	}
	
	abstract Double findX(double x) ;
	abstract Double findY(double y) ;
	
	public double findTForX(double x) {
		Double d =  findX(x);
		if(d == null){
			System.out.printf("Cannot find %f %s\n",x,this);
		}
		return d;
	}
	
	public double findTForY(double y) {
		Double d =  findY(y);
		if(d == null){
			System.out.printf("Cannot find %f %s\n",y,this);
		}
		return d;
	}
	
	public boolean isMonotomous(){
		setXYRoots();
		return xyRoots.size() == 0;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public STuple<SimplePath> splitSimpler() {
		if(simpler == null){
			setXYRoots();
			if(!isMonotomous()){
				return (STuple)makeMonotomous();
			} else {
				STuple<NonLinearCurve> sp = split();
				simpler = new STuple<SimplePath>(
						sp.l.getSimplerApproximation(),
						sp.r.getSimplerApproximation());
				}
		} 
		return simpler;
	}
	
	abstract SimplePath getSimplerApproximation() ;


	abstract STuple<NonLinearCurve> split(double t);

	STuple<NonLinearCurve> split(){
		return split(0.5);
	}
	
	@Override
	public <OPathParam> Tuple<List<Double>, List<OPathParam>> intersection(
			Path<OPathParam> other) {
		return other.intersectionLNonLinear(this);
	}

	@Override
	public Tuple<List<Double>, List<Double>> intersectionLDiaLine(
			DiagonalLine lhs) {
		return lhs.intersectionLNonLinear(this).flip();
	}

	@Override
	public Tuple<List<Double>, List<Double>> intersectionLHorLine(
			HorizontalLine lhs) {
		double t = findTForY(lhs.start.y);
		if(Util.isBetween(t, 0, 1)){
			double rt = lhs.getTForX(getAt(t).x);
			if(Util.isBetween(rt, 0, 1)){
				return Util.tupleListFromArgs(convertBack(t),rt);
			}
		}
		return Util.emptyTupleList;
	}

	@Override
	public Tuple<List<Double>, List<Double>> intersectionLVerLine(
			VerticalLine lhs) {
		double t = findTForX(lhs.start.x);
		if(Util.isBetween(t, 0, 1)){
			double rt = lhs.getTForY(getAt(t).y);
			if(Util.isBetween(rt, 0, 1)){
				return Util.tupleListFromArgs(convertBack(t),rt);
			}
		}
		return Util.emptyTupleList;
	}

	@Override
	public Tuple<List<Double>, List<Double>> intersectionLNonLinear(
			NonLinearCurve lhs) {
		if(getBBox().diagonalLengthSquared() > lhs.getBBox().diagonalLengthSquared()){
			STuple<SimplePath> simp = splitSimpler();
			return Util.appendTupList(lhs.intersection(simp.l), lhs.intersection(simp.r));
		} else {
			STuple<SimplePath> simp = lhs.splitSimpler();
			return Util.appendTupList(simp.l.intersection(this), simp.r.intersection(this));
		}
	}

	
	public BestProject<Double> project(Vec p, double bestDist){
		BBox bb = getBBox();
		if(bb.getNearestPoint(p).distanceSquared(p) < bestDist){
			STuple<SimplePath> sp = splitSimpler();
			SimplePath first;
			SimplePath second;
			if(sp.l.getBBox().getMiddle().distanceSquared(p) < 
				sp.r.getBBox().getMiddle().distanceSquared(p)){
				first = sp.l; second = sp.r;
			} else {
				first = sp.r; second = sp.l;
			}
			BestProject<Double> bp1 = first.project(p, bestDist);
			BestProject<Double> bp2 = second.project(p,Math.min(bp1.distSquared,bestDist));
			return bp1.merge(bp2);
		} else {
			return BestProject.noResult;
		}
	}
	
	double tMid(double t) {
		return (tEnd - tStart) * t + tStart;
	}
	
	double convertBack(double t){
		return t * (tEnd - tStart) + tStart;
	}
}
