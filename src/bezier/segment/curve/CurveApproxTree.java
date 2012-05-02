package bezier.segment.curve;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;

import nogbeter.points.twod.Vec;

import bezier.paths.util.BestProjection;
import bezier.paths.util.TInterval;
import bezier.paths.util.TPair;
import bezier.util.BBox;
import bezier.util.MinMax;
import bezier.util.STuple;

public class CurveApproxTree {
	
	public final Curve curve;
	public TInterval ti;
	CurveApproxTree l , r;
	
	CurveApproxTree(Curve curve){
		this.curve = curve;
		this.l = this.r = null;
	}
	
	CurveApproxTree(Curve curve,TInterval ti){
		this.curve = curve;
		this.l = this.r = null;
		this.ti = ti;
	}
	
	void expandLength(){
		if(l == null && !curve.isLine()){
			STuple<Curve> split = curve.splitSimpler();
			l = new CurveApproxTree(split.l);
			r = new CurveApproxTree(split.r);
		}
	}
	
	void expand() {
		if(l == null && !curve.isLine()){
			STuple<Curve> split = curve.splitSimpler();
			STuple<TInterval> sti = ti.split();
			l = new CurveApproxTree(split.l,sti.l);
			r = new CurveApproxTree(split.r,sti.r);
		}
	}
	
	public double expandFullyLength(double prevLength){
		expandLength();
		if(curve.isLine()){
			double lLength = curve.getLine().length();
			ti = new TInterval(prevLength,lLength);
			prevLength+=lLength;
			return prevLength;
		} 
		prevLength = l.expandFullyLength(prevLength);
		return r.expandFullyLength(prevLength);
	}

	double convertBack(double tl) {
		return ti.convertBack(tl);
	}
	
	
	public void getIntersection(CurveApproxTree other, List<TPair> result){
		if(curve.isLine() && other.curve.isLine()){
			TPair res = curve.getLine().intersection(curve.getLine());
			if(res != null){
				result.add(new TPair(convertBack(res.tl),other.convertBack(res.tr)));
			}
		} else {
			if(curve.fastIntersectionCheck(other.curve)){
				if(preferSplitMe(other)){
					expand();
					l.getIntersection(other,result);
					r.getIntersection(other,result);
				} else {
					other.expand();
					getIntersection(other.l,result);
					getIntersection(other.r,result);
				}
			}
		}
	}

	public boolean preferSplitMe(CurveApproxTree other){
		return 	other.curve.isLine() || (!curve.isLine() && 
				curve.getBBox().diagonalLengthSquared() > 
				other.curve.getBBox().diagonalLengthSquared());
	}
	
	public void project(Vec p, BestProjection<Double> best){
		if(curve.isLine()){
			double t = curve.getLine().closestT(p);
			Vec v = curve.getAt(t);
			double dist = v.distanceSquared(p);
			best.update(convertBack(t), dist,v);
		} else {
			BBox b = curve.getBBox();
			double distSquaredLowerBound = b.getNearestPoint(p).distanceSquared(p);
			if(distSquaredLowerBound >= best.distanceSquaredUpperbound){
				return;
			}
			double distanceSquaredUpperBound = b.getFarthestPoint(p).distanceSquared(p);
			if(distanceSquaredUpperBound < best.distanceSquaredUpperbound){
				best.distanceSquaredUpperbound = distanceSquaredUpperBound;
			}
			expand();
			if(fastDistance(l,p) <=
					fastDistance(r,p)){
				l.project(p, best);
				r.project(p, best);
			} else {
				r.project(p, best);
				l.project(p, best);
			}
		}
	}
	
	public static double fastDistance(CurveApproxTree l , Vec p){
		return l.curve.getBBox().getMiddle().distanceSquared(p);
	}

	public static double fastDistance(CurveApproxTree l, CurveApproxTree r){
		return l.curve.getBBox().getMiddle().
				distanceSquared(r.curve.getBBox().getMiddle());
	}
	
	public void project(CurveApproxTree other, BestProjection<TPair> best) {
		if(curve.isLine() && other.curve.isLine()){
			TPair res = curve.getLine().closestTs(other.curve.getLine());
			double dist = curve.getAt(res.tl).distanceSquared(other.curve.getAt(res.tr)) ;
			res.tl = convertBack(res.tl); res.tr = other.convertBack(res.tr);
			best.update(res, dist);
		} else {
			MinMax mm = curve.getBBox().minMaxDistSquared(other.curve.getBBox());
			if(mm.getMin() > best.distanceSquaredUpperbound){
				return;
			}
			if(mm.getMax() < best.distanceSquaredUpperbound){
				best.distanceSquaredUpperbound = mm.getMax();
			}
			if(preferSplitMe(other)){
				expand();
				if(fastDistance(l, other) < fastDistance(r, other)){
					l.project(other,best);
					r.project(other,best);
				} else {
					r.project(other,best);
					l.project(other,best);
				}
			} else{
				other.expand();
				if(fastDistance(this, other.l) < fastDistance(this, other.r)){
					project(other.l,best);
					project(other.r,best);
				} else {
					project(other.r,best);
					project(other.l,best);
				}
			}
		}
	
	}
	
	boolean isLeaf(){
		return l == null;
	}
	
	public void getLeafs(List<CurveApproxTree> leafs){
		if(isLeaf()){
			leafs.add(this);
		} else {
			l.getLeafs(leafs);
			r.getLeafs(leafs);
		}
	}
	
}
