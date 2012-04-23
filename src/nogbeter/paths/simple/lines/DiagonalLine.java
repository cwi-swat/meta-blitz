package nogbeter.paths.simple.lines;

import java.util.Collections;
import java.util.List;

import nogbeter.paths.BestProject;
import nogbeter.paths.ConnectedPath;
import nogbeter.paths.Path;
import nogbeter.paths.simple.nonlinear.NonLinearCurve;
import nogbeter.paths.simple.nonlinear.QuadCurve;
import bezier.paths.simple.SimplePath;
import bezier.points.Vec;
import bezier.util.BBox;
import bezier.util.STuple;
import bezier.util.Tuple;
import bezier.util.Util;

public class DiagonalLine extends ActualLine {

	public final Vec start, dir, end;
	
	public DiagonalLine(Vec start,Vec end) {
		this.start = start;
		this.dir = end.sub(start);
		this.end = end;
	}
	
	public Tuple<Double,Double> intersection(DiagonalLine r){
		Vec cross = start.sub(r.start);
		double solDiv = (r.dir.y * dir.x - r.dir.x * dir.y);
		double sol =
			(r.dir.x * cross.y - r.dir.y * cross.x) /
			solDiv;
		if(sol>= 0 && sol <= 1){
			double sol2 =
					(dir.x * cross.y - dir.y * cross.x) /
					solDiv;
			if(sol2>= 0 && sol2 <= 1){
				return new Tuple<Double,Double>(sol, sol2);
			}
		} 
		return null;
	}
	
	public double distanceSquared(Vec p){
		return getAt(closestT(p)).distanceSquared(p);
	}
	
	public double closestT(Vec p){
		return Util.clamp(closestTAll(p));
	}

	public double closestTAll(Vec p) {
		return -(dir.y * start.y + dir.x * start.x - dir.y * p.y - dir.x * p.x)/ (dir.x* dir.x + dir.y * dir.y);
	}
	
	public Double closestTNormal(Vec p){
		double t = closestTAll(p);
		if(t < 0 || t >= 1){
			return null;
		} else {
			return t;
		}
		
	}
	
	public double distanceSquared(DiagonalLine r){
		Tuple<Double,Double> cl = closestTs(r);
		return getAt(cl.l).distanceSquared(r.getAt(cl.r));
	}
	
	public Tuple<Double,Double> closestTs(DiagonalLine r){
		Tuple<Double,Double> res = intersection(r);
		if(res == null){
			double prs = r.closestT(start);
			double pre = r.closestT(end);
			double pls = closestT(r.start);
			double ple = closestT(r.end);
			double drs = start.distanceSquared(r.getAt(prs));
			double dre = end.distanceSquared(r.getAt(pre));
			double dls = r.start.distanceSquared(getAt(pls));
			double dle = r.end.distanceSquared(getAt(ple));
			
			double min = Util.min4(drs, dre, dls, dle);
			if(min == drs){
				return new Tuple<Double,Double>(0.0,prs);
			} else if(min == dre){
				return new Tuple<Double,Double>(1.0,prs);
			} else if(min == dls){
				return new Tuple<Double,Double>(pls, 0.0);
			} else {
				return new Tuple<Double,Double>(ple,1.0);
			}
		} else {
			return res;
		}
	}
	
	public boolean isAboveLine(Vec p){
		double t = (p.x - start.x) / (dir.x);
		return t >= 0 || t < 1 && start.y + dir.y * t < p.y;
	}
	
	public Vec getAt(double t){
		return start.add(dir.mul(t));
	}

	public Vec getTangentAt(double t) {
		return dir;
	}

	
	private Double find(double sx,double dx, double xf){
		if(dx == 0){
			return null;
		} else {
			double res = (xf - sx)/dx;
			if(res >= 0 && res <= 1){
				return res;
			} else {
				return null;
			}
		}
	}
	

	public double getTAtY(double y){
		return (y - start.y)/dir.y;
	}
	
	public double getTAtX(double x){
		return (x - start.x)/dir.x;
	}
	
	public Double findX(double x) {
		return find(start.x, dir.x, x);
	}

	public Double findY(double y) {
		return find(start.y, dir.y, y);
	}

	public int nrBelow(Vec p) {
		if(p.x == end.x){
			return 0;
		}
		Double fx = findX(p.x);
		return fx != null &&  getAt(fx).y < p.y ? 1 : 0 ;
	}

	public ConnectedPath reverse() {
		return new DiagonalLine(end,start);
	}

	public Vec getStartPoint() {
		return start;
	}

	public Vec getEndPoint() {
		return end;
	}

	@Override
	public String toString(){
		return String.format("Line %s -> %s",start.toString(),end.toString());
	}



	public double length() {
		return end.distance(start);
	}


	public Vec getNormal() {
		return dir.perpendicularCCW().normalize();
	}

	public double findTForX(double x) {
		return getTAtX(x);
	}


	
	@Override
	public <OPathParam> Tuple<List<Double>,List<OPathParam>> intersection(
			Path<OPathParam> other) {
		return other.intersectionLDiaLine(this);
	}

	@Override
	public Tuple<List<Double>,List<Double>> intersectionLDiaLine(DiagonalLine lhs) {
		Tuple<Double,Double> res =  lhs.intersection(this);
		if(res != null){
			return Util.tupleListFromTuple(res);
		} else {
			return Util.emptyTupleList;
		}
	}

	@Override
	public Tuple<List<Double>,List<Double>>intersectionLHorLine(HorizontalLine lhs) {
		double t = getTAtY(lhs.start.y);
		Vec res = getAt(t);
		if(Util.isBetweenMaybeFlip(res.x, lhs.start.x, lhs.endX)){
			return Util.tupleListFromTuple(new Tuple<Double, Double>(lhs.getTForX(res.x), t));
		} else {
			return Util.emptyTupleList;
		}
	}

	@Override
	public Tuple<List<Double>,List<Double>> intersectionLVerLine(VerticalLine lhs) {
		double t = getTAtX(lhs.start.x);
		Vec res = getAt(t);
		if(Util.isBetweenMaybeFlip(res.x, lhs.start.y, lhs.endY)){
			return Util.tupleListFromTuple(new Tuple<Double, Double>(lhs.getTForY(res.y), t));
		} else {
			return Util.emptyTupleList;
		}
	}

	@Override
	public BestProject<Double> project(Vec p, double bestDist) {
		double t = closestT(p);
		return new BestProject<Double>(getAt(t).distanceSquared(p), t);
	}

	public double minDistance(BBox b){
		b.
	}



}
