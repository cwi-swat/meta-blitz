package bezier.composite;

import java.awt.geom.PathIterator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import bezier.demos.SetOperations;
import bezier.paths.simple.Line;
import bezier.paths.util.BestProjection;
import bezier.paths.util.TInterval;
import bezier.paths.util.TPair;
import bezier.points.Matrix;
import bezier.points.Transformation;
import bezier.points.Vec;
import bezier.segment.Constants;
import bezier.segment.LengthMap;
import bezier.segment.curve.Curve;
import bezier.segment.curve.CurveApproxTree;
import bezier.util.BBox;
import bezier.util.STuple;
import bezier.util.Tuple;
import bezier.util.Util;

public class Path implements Area{
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
	
	public Path transform(Transformation t){
		return transform(t.to);
	}

	public Vec getArbPoint(){
		return curves.get(0).getStartPoint();
	}
	
	
	private double getBorderTangentAngle(int n){
		if(!isClosed()){
			if(n == 0){
				return 1.0;
			} else if(n == curves.size()){
				return 1.0;
			}
		}
		Vec a =  curves.get(Util.mod(n-1,curves.size())).getTangentAt(1.0);
		Vec b = curves.get(Util.mod(n,curves.size())).getTangentAt(0.0);
		return a.dot(b);
	}

	Vec getBorderTangent(int n) {
			if(!isClosed()){
				if(n == 0){
					return curves.get(0).getTangentAt(0).normalize();
				} else if(n == curves.size()){
					return curves.get(n-1).getTangentAt(1).normalize();
				}
			}
			Vec a =  curves.get(Util.mod(n-1,curves.size())).getTangentAt(1.0).normalize();
			Vec b = curves.get(Util.mod(n,curves.size())).getTangentAt(0.0).normalize();
			double angle = a.dot(b);
			System.out.printf("Angle %f\n",  1 + angle);
			return a.add(b).div(1 + angle);
	}
	

	public void projectBorder(LengthMap lm, int n, Vec p2, Vec p3, List<Vec> result) {
		Vec a =  curves.get(Util.mod(n-1,curves.size())).getTangentAt(1.0).normalize();
		Vec b = curves.get(Util.mod(n,curves.size())).getTangentAt(0.0).normalize();
		double angle = a.dot(b);
		Vec tan = a.add(b).div(1+ angle);
		Vec pp3 = curves.get(Util.mod(n,curves.size())).getStartPoint().add(tan.mul(p3.y).perpendicularCW());
		Vec pp2 = projectVec(p2,lm); //.interpolate(1.0/3.0, pp3);
		Vec pp4 = projectVec(p2.mirror(p3),lm);//.sub(p2).mul(3).add(p3),lm).interpolate(1.0/3.0, pp3);
		System.out.printf("%f angle\n",angle);
//		Vec npp2 = pp4.mirror(pp3).interpolate(angle * 0.5, pp2);
//		Vec npp4 = pp2.mirror(pp3).interpolate(angle * 0.5, pp4);
		result.add(pp2);
		result.add(pp3);
		result.add(pp4);
		
		
	}

	public Vec getTangentAt(double t){
		t = Math.min(curves.size(), t);
		int n = (int)t;
		double tn = t - n;
		if(tn == 0){
			if(!isClosed()){
				if(n == 0){
					return curves.get(0).getTangentAt(0);
				} else if(n == curves.size()){
					return curves.get(n-1).getTangentAt(1);
				}
			}
			return curves.get(Util.mod(n-1,curves.size())).getTangentAt(1.0);
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
		List<TPair> localResult = new ArrayList<TPair>(10);
		
		for(int i = 0 ; i < curves.size() ; i++){
			TInterval til = new TInterval(i);
			for(int j = 0; j < r.curves.size() ; j++){
				TInterval tir = new TInterval(j);
				CurveApproxTree a = curves.get(i).getApproxTree();
				CurveApproxTree b = r.curves.get(j).getApproxTree();
				a.getIntersection(b, localResult);
				for(TPair t : localResult){
					result.add(new TPair(til.convertBack(t.tl),tir.convertBack(t.tr)));
				}
			}
		}
		return result;
	}

	public double project(final Vec p){
		return project(p, new BestProjection<Double>());
	}
	
	public Double project(final Vec p, BestProjection<Double> best){
		final List<Integer> indexesNearestFirst = Util.natListTill(curves.size());
		final List<Double> distanceMiddles = new ArrayList<Double>(curves.size());
		for(int i : indexesNearestFirst){
			double dist = curves.get(i).getAt(0.5).distanceSquared(p);
			distanceMiddles.add(dist);
			best.update(i+0.5, dist);
		}
		Collections.sort(indexesNearestFirst,new Comparator<Integer>() {
			@Override
			public int compare(Integer o1, Integer o2) {
				return Double.compare(
						distanceMiddles.get(o1), distanceMiddles.get(o2));
			}
		});
		for(int i : indexesNearestFirst){
			TInterval ti = new TInterval(i);
			BestProjection<Double> bestLocal = new BestProjection<Double>(best.distanceSquaredUpperbound);
			curves.get(i).getApproxTree().project(p, bestLocal);
			if(bestLocal.t != null){
				best.update(ti.convertBack(bestLocal.t), bestLocal.distanceSquaredUpperbound);
			}
		}
		return best.t;
	}
	
	public Double projectNoSort(final Vec p, BestProjection<Double> best){
		int j =best.t.intValue();
		TInterval ti = new TInterval(j);
		BestProjection<Double> bestLocal = new BestProjection<Double>(best.t - j,best.distanceSquaredUpperbound);
		curves.get(j).getApproxTree().project(p, bestLocal);
		if(bestLocal.t != null){
			best.update(ti.convertBack(bestLocal.t), bestLocal.distanceSquaredUpperbound);
		}
		for(int i = 0 ; i < curves.size() ; i++){
			ti = new TInterval(i);
			bestLocal = new BestProjection<Double>(best.t - i,best.distanceSquaredUpperbound);
			curves.get(i).getApproxTree().project(p, bestLocal);
			if(bestLocal.t != null){
				best.update(ti.convertBack(bestLocal.t), bestLocal.distanceSquaredUpperbound);
			}
		}
		return best.t;
	}
	
	public ProjectPath getProjectPath(){
		return new ProjectPath(curves);
	}
	
	public Double projectLength(final Vec p, BestProjection<Double> best){
		final List<Integer> indexesNearestFirst = Util.natListTill(curves.size());
		final List<Double> distanceMiddles = new ArrayList<Double>(curves.size());
		for(int i : indexesNearestFirst){
			double dist = curves.get(i).getAt(0.5).distanceSquared(p);
			distanceMiddles.add(dist);
			best.update(i+0.5, dist);
		}
		Collections.sort(indexesNearestFirst,new Comparator<Integer>() {
			@Override
			public int compare(Integer o1, Integer o2) {
				return Double.compare(
						distanceMiddles.get(o1), distanceMiddles.get(o2));
			}
		});
		for(int i : indexesNearestFirst){
			TInterval ti = new TInterval(i);
			BestProjection<Double> bestLocal = new BestProjection<Double>(best.distanceSquaredUpperbound);
			curves.get(i).getApproxTree().project(p, bestLocal);
			if(bestLocal.t != null){
				best.update(ti.convertBack(bestLocal.t), bestLocal.distanceSquaredUpperbound);
			}
		}
		return best.t;
	}
	
	public TPair project(final Path other){
		return project(other,new BestProjection<TPair>());
	}
	
	public TPair project(final Path other, BestProjection<TPair> best){
		List<STuple<Integer>> indexesNearestFirst = Util.natPairs(curves.size(),other.curves.size());
		final List<Double> distanceMiddles = new ArrayList<Double>(indexesNearestFirst.size());
		for(STuple<Integer> tpair : indexesNearestFirst){
			double dist = curves.get(tpair.l).getAt(0.5).distanceSquared(
					other.curves.get(tpair.r).getAt(0.5));
			TPair t = new TPair(tpair.l + 0.5,tpair.r + 0.5);
			best.update(t,dist);
			distanceMiddles.add(dist);
		}
		Collections.sort(indexesNearestFirst,new Comparator<STuple<Integer>>() {
			@Override
			public int compare(STuple<Integer> o1, STuple<Integer> o2) {
				return Double.compare(
						distanceMiddles.get(o1.l * other.curves.size() + o1.r)
						, distanceMiddles.get(o2.l * other.curves.size() + o2.r));
			}
		});
		for(STuple<Integer> i : indexesNearestFirst){
			TInterval til = new TInterval(i.l);
			TInterval tir = new TInterval(i.r);
			BestProjection<TPair> bestLocal = new BestProjection<TPair>(best.distanceSquaredUpperbound);
			curves.get(i.l).getApproxTree().project(other.curves.get(i.r).getApproxTree(), bestLocal);
			if(bestLocal.t != null){
				best.update(new TPair(til.convertBack(bestLocal.t.tl),tir.convertBack(bestLocal.t.tr)),
						bestLocal.distanceSquaredUpperbound,bestLocal.v);
			}
		}
		return best.t;
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
				Vec tan = getNormalizedTangent(lm.totalT());
				Vec dir = tan.perpendicularCW();
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
				Vec tan = getNormalizedTangent(0);
				Vec dir = tan.perpendicularCW();
				start = start.add(tan.mul(lm.totalLength() - toFind).negate());
				return start.add(dir.mul(v.y));  
			}
		}
		double t = lm.findT(toFind);
		Vec start = getAt(t);
		Vec dir = getNormalizedTangent(t).perpendicularCW();
		return start.add(dir.mul(v.y));
	}
	

	private Vec getNormalizedTangent(double totalT) {
		return getTangentAt(totalT).normalize();
	}


	class PathPathIterator implements PathIterator{

		int cur ;
		boolean closed;
		
		public PathPathIterator() {
			cur = -1;
			closed = isClosed();
		}
		
		@Override
		public int getWindingRule() {
			return PathIterator.WIND_EVEN_ODD;
		}

		@Override
		public boolean isDone() {
			return cur == curves.size() + (closed ? 1 : 0);
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

	
	public List<Tuple<Double,Line>> getApproxLinesAndLength(){
		double length = 0;
		List<Tuple<Double,Line>> result = new ArrayList<Tuple<Double,Line>>();
		for(Curve c : curves){
			CurveApproxTree ct = c.getFullApproxLengthTree();
			length = ct.expandFullyLength(length);
			List<CurveApproxTree> leafs = new ArrayList<CurveApproxTree>();
			ct.getLeafs(leafs);
			for(CurveApproxTree n : leafs){
				result.add(new Tuple<Double, Line>( n.ti.tLength, n.curve.getLine()));
			}
		}
		return result;
	}

	public Path repeatX(int n) {
		List<Curve> result = new ArrayList<Curve>();
		result.addAll(this.curves);
		for(int i = 1 ; i <n ; i++){
			result.addAll(transform(Transformation.id.translate(getBBox().width * i, 0)).curves);
		}
		return new Path(result);
	}

}
