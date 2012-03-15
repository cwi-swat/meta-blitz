package bezier.segment.curve;

import java.util.ArrayList;
import java.util.List;

import bezier.demos.ProjectPointTest;
import bezier.points.Vec;
import bezier.segment.BestProjection;
import bezier.segment.TPair;
import bezier.util.BBox;
import bezier.util.MinMax;
import bezier.util.STuple;

public final class CurveOperations {

	
	public static void intersections(Curve l, TInterval li, Curve r, TInterval ri, List<TPair> result){
		if(l.isLine() && r.isLine()){
			TPair res = l.getLine().intersection(r.getLine());
			if(res != null){
				result.add(new TPair(li.convertBack(res.tl),ri.convertBack(res.tr)));
			}
		} else if(l.isLine() || (!l.isLine() && !r.isLine() 
				&& l.getBBox().diagonalLengthSquared() > r.getBBox().diagonalLengthSquared())){
			if(l.fastIntersectionCheck(r)){
				STuple<Curve> sr = r.splitSimpler();
				STuple<TInterval> sri = ri.split();
				intersections(l, li, sr.l, sri.l, result);
				intersections(l, li, sr.r, sri.r, result);
			}
		} else{
			if(l.fastIntersectionCheck(r)){
				STuple<Curve> sl = l.splitSimpler();
				STuple<TInterval> sli = li.split();
				intersections(sl.l, sli.l,r,ri, result);
				intersections(sl.r, sli.r,r,ri, result);
			}
		}
	}

	public static List<TPair> intersections(Curve p, Curve q) {
		List<TPair> result = new ArrayList<TPair>();
		intersections(p, new TInterval(), q, new TInterval(),result);
		return result;
	}
	

	public static void project(Vec p, Curve c, TInterval ti, BestProjection<Double> best){
		if(c.isLine()){
			Line cl = (Line)c;
			double t = cl.closestT(p);
			double distSquared = c.getAt(t).distanceSquared(p);
			t = ti.convertBack(t);
			best.update(t, distSquared);
			if(ProjectPointTest.dump) System.out.printf("Updated %f %f\n",distSquared, best.distanceSquaredUpperbound );
		} else {
			BBox b = c.getBBox();
			double distSquaredLowerBound = b.getNearestPoint(p).distanceSquared(p);
			if(distSquaredLowerBound >= best.distanceSquaredUpperbound){
				if(ProjectPointTest.dump){
					System.out.printf("Cutting off %f %f!\n", distSquaredLowerBound, best.distanceSquaredUpperbound);
				}
				return;
			} else {
				if(ProjectPointTest.dump) System.out.printf("Continuing %f %f!\n", distSquaredLowerBound, best.distanceSquaredUpperbound);
			}
			double distanceSquaredUpperBound = b.getFarthestPoint(p).distanceSquared(p);
			if(distanceSquaredUpperBound < best.distanceSquaredUpperbound){
				best.distanceSquaredUpperbound = distanceSquaredUpperBound;
			}
			STuple<Curve> s = c.splitSimpler();
			STuple<TInterval> tis = ti.split();
			Curve l = s.l; Curve r = s.r; TInterval til = tis.l; TInterval tir = tis.r;
			if(l.getBBox().getMiddle().distanceSquared(p) <=
					r.getBBox().getMiddle().distanceSquared(p)){
				project(p, l,til, best);
				project(p, r,tir, best);
			} else {
				project(p, r,tir, best);
				project(p, l,til, best);
			}
		}
		
	}
	
	public static double project(Vec p, Curve c){
		BestProjection<Double> best = new BestProjection<Double>(
				0.5,c.getAt(0.5).distanceSquared(p));
		project(p,c,new TInterval(),best);
		return best.t;
	}
	

	public static void project(Curve l, TInterval li, Curve r,TInterval ri, BestProjection<TPair> best) {
		if(l.isLine() && r.isLine()){
			Line ll = (Line)l; Line rr = (Line) r;
			TPair res = ll.closestTs(rr);
			double dist = ll.getAt(res.tl).distanceSquared(rr.getAt(res.tr)) ;
			res.tl = li.convertBack(res.tl); res.tr = ri.convertBack(res.tr);
			best.update(res, dist);
		} else {
			MinMax mm = l.getBBox().minMaxDistSquared(r.getBBox());
			if(mm.getMin() > best.distanceSquaredUpperbound){
				return;
			}
			if(mm.getMax() < best.distanceSquaredUpperbound){
				best.distanceSquaredUpperbound = mm.getMax();
			}
			if(l.isLine() || (!l.isLine() && !r.isLine()
				&& l.getBBox().diagonalLengthSquared() > r.getBBox().diagonalLengthSquared())){
				STuple<Curve> sr = r.splitSimpler();
				STuple<TInterval> sri = ri.split();
				project(l, li, sr.l, sri.l, best);
				project(l, li, sr.r, sri.r, best);
			} else{
				STuple<Curve> sl = l.splitSimpler();
				STuple<TInterval> sli = li.split();
				project(sl.l, sli.l,r,ri, best);
				project(sl.r, sli.r,r,ri, best);
			}
		}
	
	}
	
	public static TPair project(Curve a, Curve b){
		BestProjection<TPair> best = new BestProjection<TPair>(
				new TPair(0.5,0.5), a.getAt(0.5).distanceSquared(b.getAt(0.5)));
		project(a,new TInterval(),b,new TInterval(), best);
		return best.t;
	}


	public static void projectNormal(Vec p, Curve c, TInterval ti, BestProjection<Double> best){
		if(c.isLine()){
			Line cl = (Line)c;
			Double t = cl.closestTNormal(p);
			if(t == null) return;
			double distSquared = c.getAt(t).distanceSquared(p);
			t = ti.convertBack(t);
			best.update(t, distSquared);
			if(ProjectPointTest.dump) System.out.printf("Updated %f %f\n",distSquared, best.distanceSquaredUpperbound );
		} else {
			BBox b = c.getBBox();
			double distSquaredLowerBound = b.getNearestPoint(p).distanceSquared(p);
			if(distSquaredLowerBound >= best.distanceSquaredUpperbound){
				if(ProjectPointTest.dump){
					System.out.printf("Cutting off %f %f!\n", distSquaredLowerBound, best.distanceSquaredUpperbound);
				}
				return;
			} else {
				if(ProjectPointTest.dump) System.out.printf("Continuing %f %f!\n", distSquaredLowerBound, best.distanceSquaredUpperbound);
			}
			STuple<Curve> s = c.splitSimpler();
			STuple<TInterval> tis = ti.split();
			Curve l = s.l; Curve r = s.r; TInterval til = tis.l; TInterval tir = tis.r;
			if(l.getBBox().getMiddle().distanceSquared(p) <=
					r.getBBox().getMiddle().distanceSquared(p)){
				project(p, l,til, best);
				project(p, r,tir, best);
			} else {
				project(p, r,tir, best);
				project(p, l,til, best);
			}
		}
		
	}
	
	public static double projectNormal(Vec p, Curve c){
		BestProjection<Double> best = new BestProjection<Double>(
				0.5,c.getAt(0.5).distanceSquared(p));
		projectNormal(p,c,new TInterval(),best);
		return best.t;
	}
	
}
