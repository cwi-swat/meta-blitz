package bezier.composite;

import java.awt.geom.Path2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import bezier.demos.SetOperations;
import bezier.points.Matrix;
import bezier.points.Vec;
import bezier.segment.Constants;
import bezier.segment.LengthMap;
import bezier.segment.TPair;
import bezier.segment.curve.Curve;
import bezier.segment.curve.CurveOperations;
import bezier.segment.curve.QuadCurve;
import bezier.segment.curve.TInterval;
import bezier.util.BBox;
import bezier.util.Tuple;
import bezier.util.Util;

public final class Path implements Area{
	public final List<Curve> curves;
	public final BBox bbox;

	
	public Path(List<Curve> curves){
		this.curves = curves;
		BBox[] bboxs = new BBox[curves.size()];
		for(int i = 0; i < curves.size() ; i++){
			bboxs[i] = curves.get(i).getBBox();
		}
		this.bbox = BBox.merge(bboxs);
	}
	
	public Path(Curve c) {
		this(Collections.singletonList(c));
	}

	public Path transform(Matrix m){
		List<Curve> newCurves = new ArrayList<Curve>(curves.size());
		for(Curve c : curves){
			newCurves.add(c.transform(m));
		}
		return new Path(newCurves);
	}

	public Vec getArbPoint(){
		return curves.get(0).getStartPoint();
	}

	public Vec getTangentAt(double t){
		t = Math.min(curves.size(), t);
		int n = (int)t;
		if(n == curves.size() && n == t){
			n = 0; t= 1;
		}

		
		return curves.get(n).getTangentAt(t-n);
	}
	
	public Path liftAll(){
		List<Curve> result = new ArrayList<Curve>();
		for(Curve c : curves){
			result.add(c.lift());
		}
		return new Path(result);
	}
	
	public Vec getAt(double t){
//		System.out.printf("Getting %f %d\n", t, curves.size());

//		t = Math.min(curves.size(), t);
		if(t >= curves.size()){
			t -= curves.size();
		}
		int n = (int)t;
		
		return curves.get(n).getAt(t-n);
	}
	
	
	public double tBetween(double startT, double endT){
		double where ;
//		System.out.printf("%f %f ->",startT, endT);
		if(startT > endT){
			endT+=curves.size();
		} 
		where = (startT + endT)/2.0;
		if(where > curves.size()){
			where-=curves.size();
		}
//		System.out.printf("%f\n", where);
		return where;
	}
	
	public Vec getBetween(double startT, double endT){
		return getAt(tBetween(startT,endT));
	}
	
	private Tuple<Integer,Integer> getStraightInterval(int start, double x){
		int startStraight = start;
		do{
			startStraight--;
		} while(curves.get(Util.mod(startStraight, curves.size())).getEndPoint().x == x);
		startStraight++;
		int endStraight = start ;
		do{
			endStraight++;
		} while(curves.get(Util.mod(endStraight, curves.size())).getStartPoint().x == x);
		endStraight--;
		return new Tuple<Integer, Integer>(startStraight, endStraight);
	}
	
	public int nrIntersectionsBelow(Vec p){
		int nr = 0;
		int i = 0;
		while(i < curves.size()){
			Curve c = curves.get(i);
			boolean sameStart = c.getStartPoint().x == p.x;
			boolean sameEnd = c.getEndPoint().x == p.x;
			if(sameStart || sameEnd){ // border case
				if(sameEnd){
					i++;
				}
				Tuple<Integer,Integer> straight = getStraightInterval(i, p.x);
				// if tangent did not change, it is an intersection
				double yHigh = Math.max(curves.get(straight.l).getEndPoint().y, curves.get(straight.r).getStartPoint().y);
				if(yHigh < p.y && Math.signum(curves.get(straight.l).getTangentAt(1.0).x) ==
							Math.signum(curves.get(straight.l).getTangentAt(0.0).x)){
							nr++;
				}
				i = straight.r+1;
			} else {
				nr+= c.nrBelow(p);
				i++;
			}
		}
		return nr;
	}
	
	public boolean isInside(Vec p){
		return bbox.isInside(p) &&  nrIntersectionsBelow(p) % 2 == 1;
	}
	

	static final class CurveAndDist implements Comparable<CurveAndDist>{
		double dist;
		int ci;
		CurveAndDist(int ci, Curve c, Vec from){
			this.ci = ci;
			dist = c.getAt(0.5).distanceSquared(from);
		}
		@Override
		public int compareTo(CurveAndDist o) {
			return Double.compare(dist, o.dist);
		}
	}
//	
//	public DistPoint project(Vec from){
//		DistPoint res = new DistPoint();
//		project(from,res);
//		return res;
//	}
	
	public Path reverse(){
		List<Curve> result = new ArrayList<Curve>();
		for(int i = curves.size()-1 ; i >= 0 ; i--){
			result.add(curves.get(i).reverse());
		}
		return new Path(result);
	}
	
	public Path getSubPath(double start, double end){
		List<Curve> result = new ArrayList<Curve>();
		fillCurves(start, end, result);
		return new Path(result);
	}
	
	void fillCurves(double start, double end, List<Curve> result){
		if(start == end){
			return;
		}
		if(start > end){
			fillCurves(start, curves.size(), result);
			fillCurves(0.0,end,result);
			return;
		}
		int till = (int)end;
		int from = (int)start;
		if(from == till){
			double startT = start - from;
			double endT = end - till;
			Curve between = curves.get(from);
			if(startT != 0){
				between = between.split(startT).r;
				endT = (endT - startT)/(1.0 - startT);
			}
			if(endT != 1){
				between = between.split(endT).l;
			}
			result.add(between);
			return;
		}
		double startT = start - from;
		if(startT != 0){
			result.add(curves.get(from).split(startT).r);
		} else {
			result.add(curves.get(from));
		}
		for(int i = from + 1; i < till ; i++){
			result.add(curves.get(i));
		}
		double endT = end - till;
		if(endT != 0){
			result.add(curves.get(till).split(endT).l);
		} 
	}
	
//	public LengthMap getLengthMap(){
//		LengthMap map = new LengthMap();
//		for(Curve c : curves ){
//			c.fillLengthMap(map);
//		}
//		return map;
//	}
	
//	public static List<TPair> removeDoubles(List<TPair> tps){
//
//		if(tps.size() <= 1){
//			return tps;
//		}
//		List<TPair> result = new ArrayList<TPair>();
//		int end = tps.size() - 1;
//		TPair prev = tps.get(tps.size()-1);
//		if(tps.get(0).point.isEqError(prev.point)){
//			end--;
//			while(end >= 0 && tps.get(end).point.isEqError(prev.point)){
//				end--;
//			}
//		}
//		prev = tps.get(0);
//		for(int i = 1 ; i <= end; i++){
//			TPair tp = tps.get(i);
//			if(!tp.point.isEqError(prev.point)){
//				result.add(prev);
//				
//			} 
//			prev = tp;
//		}
//		if(tps.size() > 0 || !tps.get(tps.size()-1).point.isEqError(prev.point)){
//			result.add(prev);
//		}
//		return result;
//	}
	
	
	public List<TPair> getIntersections(Path r){
		if(!bbox.overlaps(r.bbox)){
			return new ArrayList<TPair>();
		}
		List<TPair> result = new ArrayList<TPair>(30);
		for(int i = 0 ; i < curves.size() ; i++){
			for(int j = 0; j < r.curves.size() ; j++){
				CurveOperations.intersections(curves.get(i), new TInterval(i,1),
						r.curves.get(j),new TInterval(j,1), result);
			}
		}
		return result;
	}
//	
//	public void project(Vec from, DistPoint res){
//		CurveAndDist[] cdist = new CurveAndDist[curves.length];
//		for(int i = 0; i < curves.length; i++){
//			cdist[i] = new CurveAndDist(i,curves[i], from);
//		}
//		Arrays.sort(cdist);
//		for(CurveAndDist c : cdist){
//			res.changed = false;
//			curves[c.ci].project(from, res);
//			if(res.changed){
//				res.t+= c.ci;
//			}
//		}
//	}
//	
//	static class CurvePair implements Comparable<CurvePair>{
//		final int il, ir;
//		final MinMax minMax;
//		
//		
//		public CurvePair(int il, int ir, MinMax minMax) {
//			this.il = il;
//			this.ir = ir;
//			this.minMax = minMax;
//		}
//
//		@Override
//		public int compareTo(CurvePair o) {
//			return Double.compare(minMax.getMin(), o.minMax.getMin());
//		}
//	}
//	
//	public static DistTPair project(Path a, Path b){
//		DistTPair res = new DistTPair();
//		CurvePair[] pairs = new CurvePair[a.curves.length * b.curves.length];
//		for(int i = 0 ; i < a.curves.length ; i++){
//			for(int j = 0 ; j < b.curves.length ; j++){
//				MinMax mm = a.curves[i].bbox.minMaxDistSquared(b.curves[j].bbox);
//				pairs[i * b.curves.length + j] = 
//						new CurvePair(i, j,mm);
//				res.update(mm.getMax());
//				CurvePair p =pairs[i * b.curves.length + j];
//			}
//		}
////		System.out.printf("Should be at least %f\n", res.max);
//		Arrays.sort(pairs);
//		for(CurvePair p : pairs){
//			if(p.minMax.getMin() <= res.max){
////				System.out.printf("Trying %d %d %f %f\n", p.il,p.ir, p.minMax.getMin(), p.minMax.getMax());
//				OldCurve.project(a.curves[p.il],b.curves[p.ir],p.minMax,res);
//				if(res.changed){
//					res.tl += p.il;
//					res.tr += p.ir;
////					System.out.printf("Update %f %f\n",res.tl,res.tr);
//					res.changed = false;
//				}
//			}
//		}
////		System.out.printf("Done\n\n");
//		return res;
//	}
//	
//	public List<Vec> getAtX(double x){
//		List<Vec> result = new ArrayList<Vec>();
//		for(OldCurve c : curves){
//			Double t = c.findX(x);
//			if(t!= null){
//				result.add(c.getAt(t));
//			}
//		}
//		return result;
//	}
//	
//
//	public List<Vec> getAtY(double y){
//		List<Vec> result = new ArrayList<Vec>();
//		for(OldCurve c : curves){
//			Double t = c.findY(y);
//			if(t!= null){
//				result.add(c.getAt(t));
//			}
//		}
//		return result;
//	}

	public Path2D.Double toAWT(){
		Path2D.Double res = new Path2D.Double();
		for(Curve c: curves){
			res.append(c.toAWT(), true);
		}
		return res;
	}
	
	public String toString(){
		StringBuffer b = new StringBuffer();
		b.append("Path:\n");
		for(Curve c : curves){
			b.append(c.toString());
			b.append('\n');
		}
		b.append('\n');
		return b.toString();
	}
//	
//	public List<Curve> projectOnto(double startT, double endT, LengthMap map, Curve curve){
//		int from = (int) startT;
//		int till = (int) endT;
//		if(till == curves.length){
//			till--;
//		}
//		
//		
//	}
	
	public Vec getStartPoint(){
		return curves.get(0).getStartPoint();
	}
	
	public Vec getEndPoint(){
		return curves.get(curves.size()-1).getEndPoint();
	}
	
	public boolean canAppendReversed(Path other){
		return  getEndPoint().isEqError(other.getEndPoint());
	}
	
	public boolean canAppend(Path other){
		return getEndPoint().isEqError(other.getStartPoint());
	}
	
	public boolean willMakeClosed(Path other){
		return getStartPoint().isEqError(other.getEndPoint());
	}
	
	public boolean willMakeClosedReversed(Path other){
		return getStartPoint().isEqError(other.getStartPoint());
	}
	
	public Path append(Path other){
		if(getEndPoint().isEqError(other.getEndPoint())){
			other = other.reverse();
		}
		
		List<Curve> res = new ArrayList<Curve>(curves);
		res.add(other.curves.get(0).getWithAdjustedStartPoint(getEndPoint()));
		for(int i = 1 ; i < other.curves.size() ; i++){
			res.add(other.curves.get(i));
		}
		Curve first = res.get(0);
		Curve last = res.get(res.size()-1);
		
		if(first.getStartPoint().isEqError(last.getEndPoint())){
			res.set(0, first.getWithAdjustedStartPoint(last.getEndPoint()));
		}
		return new Path(res);
	}
	
	public boolean isClosed(){
		Vec start = getStartPoint();
		Vec end = getEndPoint();
		return start.isEqError(end);
	}
	
	private enum Inside{
		INSIDE,
		OUTSIDE,
		UNSURE;
		
		Inside flip(){
			if(this == INSIDE){
				return OUTSIDE;
			} else {
				return INSIDE;
			}
		}
	}
	
	private Inside isInside(double tl, double tr, Paths other){
		Vec l = getAt(tl);
		Vec r = getAt(tr);
		Vec between = getBetween(tl,tr);
		if(l.distanceSquared(between) <= Constants.MAX_ERROR_POW2 ||
				r.distanceSquared(between) <= Constants.MAX_ERROR_POW2){
			return Inside.UNSURE;
		} else {
			return other.isInside(between) ? Inside.INSIDE : Inside.OUTSIDE;
		}
	}
	
	private Inside segmentsStartInside(List<Double> ts, Paths other){
		double prev = ts.get(ts.size()-1);
		boolean flip = true;
		for(double cur : ts){
			Inside inside = isInside(prev, cur, other);
			if(inside != Inside.UNSURE){
				if(flip){
					return inside.flip();
				} else {
					return inside;
				}
			}
			flip = !flip;
		}
		return Inside.UNSURE;
	}
	
	List<Path> getSegments(List<Double> ts, Paths other, boolean shouldBeInside){
		if(ts.isEmpty()){
			List<Path> p =  new ArrayList<Path>();
			if(!shouldBeInside){
				p.add(this);
			}
			return p;
		}
//		assert ts.size() % 2 == 0;
//		double prev = ts.get(ts.size()-1);

		boolean startInside =false ;
		switch(segmentsStartInside(ts, other)){
		case INSIDE: startInside = true; break;
		case OUTSIDE: startInside = false ; break;
		case UNSURE: System.out.printf("ALL UNSURE!!!\n"); break;
		}
		int first; 
		int last; 
		if(startInside == shouldBeInside){
			first = 0;
			last = ts.size()-2;
		} else {
			first = 1;
			last = ts.size()-1;
		}
		
		List<Path> segments = new ArrayList<Path>();
		for(int i = first; i <= last; i+=2){
			
			Path p = getSubPath(ts.get(i), ts.get((i+1) % ts.size()));
			if(SetOperations.dump){
				System.out.printf("Seg %f %f %d\n", ts.get(i), ts.get((i+1) % ts.size()),curves.size());
				System.out.print(p);
				System.out.println("this");
				System.out.print(this);
			}
			if(p.curves.isEmpty() && p.getStartPoint().distanceSquared(p.getEndPoint()) <= Constants.MAX_ERROR_POW2){
				if(p.getLengthMap().totalLength() > Constants.MAX_ERROR){
					segments.add(p);
				} else {
//					System.out.println("Not adding insignificant path");
				}
			} else if(!p.curves.isEmpty()){
				segments.add(p);
			} else {
//				System.out.println("Not adding empty path!!");
			}
		}
		if(SetOperations.dump){System.out.println();}
//		for(double intersect : ts){
//			if(other.isInside(getBetween(prev,intersect)) == shouldBeInside){
//				Path p = getSubPath(prev, intersect);
//				if(!p.curves.isEmpty()){
//					segments.add(p);
//				} else {
//					System.out.println("Not adding empty path!!");
//				}
//			}
//			prev = intersect;
//		}
//		System.out.println();
//		System.out.println();
		return segments;
	}

	public Path makeMonotomous(){
		List<Curve> result = new ArrayList<Curve>();
		for(Curve c : curves){
			result.addAll(c.makeMonotomous());
		}
		return new Path(result);
	}
	
	public LengthMap getLengthMap(){
		return getLengthMap(Constants.DEFAULT_SAMPLES_PER_DIRECT);
	}
	
	public LengthMap getLengthMap(double samplesPerDirect){
		LengthMap m = new LengthMap();
		for(Curve c : curves){
			c.fillLengthMap(m, samplesPerDirect);
		}
		return m;
	}

	public static Path makeFromSubPaths(List<Path> paths) {
		List<Curve> allCurves = new ArrayList<Curve>();
		for(Path p : paths){
			allCurves.addAll(p.curves);
		}
		return new Path(allCurves);
	}
	
	@Override
	public BBox getBBox() {
		return bbox;
	}
	
	public Path projectOn(Path guide, LengthMap lm){
		List<Curve> result = new ArrayList<Curve>();
		for(Curve c : curves){
			result.addAll(c.projectOn(guide, lm));
		}
		return new Path(result);
	}
	
	public Vec projectVec(Vec v, LengthMap lm){
		double toFind = v.x;
		if(toFind > lm.totalLength()){
			if(isClosed()){
				while(toFind > lm.totalLength()){
					toFind-=lm.totalLength();
				}
			} else {
				toFind = lm.totalLength();
				Vec start = getAt(lm.totalT());
				Vec tan = getTangentAt(lm.totalT()).normalize();
				Vec dir = tan.perpendicularCCW();
				start = start.add(tan.mul(lm.totalLength() - toFind));
				return start.add(dir.mul(v.y));  
			}
		} else if(toFind < 0){
			if(isClosed()){
				while(toFind < 0){
					toFind+=lm.totalLength();
				}
			} else {
				toFind = 0;
				Vec start = getAt(0);
				Vec tan = getTangentAt(0).normalize();
				Vec dir = tan.perpendicularCCW();
				start = start.add(tan.mul(lm.totalLength() - toFind).negate());
				return start.add(dir.mul(v.y));  
			}
		}
		double t = lm.findT(toFind);
		Vec start = getAt(t);
		Vec dir = getTangentAt(t).normalize().perpendicularCCW();
//		System.out.printf("Norm %f\n",dir.norm());
		return start.add(dir.mul(v.y));
	}
}
