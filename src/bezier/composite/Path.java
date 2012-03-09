package bezier.composite;

import java.awt.geom.Path2D;
import java.awt.geom.PathIterator;
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
import bezier.util.DummySWTSHape;
import bezier.util.Tuple;
import bezier.util.Util;

public final class Path implements Area{
	public final List<Curve> curves;
	public final BBox bbox;

	
	public Path(List<Curve> curves){
		this.curves = curves;
		this.bbox = new BBox(curves.toArray(new Curve[]{}));
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
		int end = curves.size();
		while(i < end){
			Curve c = curves.get(i);
			boolean sameStart = c.getStartPoint().x == p.x;
			boolean sameEnd = c.getEndPoint().x == p.x;
			if(sameStart || sameEnd){ // border case
				Tuple<Integer,Integer> straight = getStraightInterval(i, p.x);
				// if tangent did not change, it is an intersection
				int s = Util.mod(straight.l, curves.size());
				int e = Util.mod(straight.r, curves.size());
				if(straight.l < 0){
					end = Math.min(end, -straight.l);
				}
				double yHigh = Math.max(curves.get(s).getEndPoint().y, curves.get(e).getStartPoint().y);
				
				if(yHigh < p.y && Math.signum(curves.get(s).getTangentAt(1.0).x) ==
							Math.signum(curves.get(e).getTangentAt(0.0).x)){
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
			if(!p.curves.isEmpty() && p.getStartPoint().distanceSquared(p.getEndPoint()) <= Constants.MAX_ERROR_POW2){
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
	
	class PathPathIterator implements PathIterator{

		int cur ;
		
		public PathPathIterator() {
			cur = -1;
		}
		
		@Override
		public int getWindingRule() {
			return PathIterator.WIND_EVEN_ODD;
		}

		@Override
		public boolean isDone() {
			return cur == curves.size() + 1;
		}

		@Override
		public void next() {
			cur++;
		}

		@Override
		public int currentSegment(double[] coords) {
			System.out.println("USING DOUBLE!!");
			double[] coordd = new double[6];
			int res = currentSegment(coordd);
			for(int i = 0 ; i < coordd.length ; i++){
				coords[i] = (float)coordd[i];
			}
			return res;
		}

		@Override
		public int currentSegment(float[] coords) {
			if(cur == -1){
				Vec start = getStartPoint();
				coords[0] = (float)start.x;
				coords[1] = (float)start.y;
				return PathIterator.SEG_MOVETO;
			} else if(cur == curves.size()){
				return PathIterator.SEG_CLOSE;
			} else {
				return curves.get(cur).currentSegment(coords);
			}
		}
		
	}
	
	public PathIterator getPathIterator(){
		return new PathPathIterator();
	}
}
