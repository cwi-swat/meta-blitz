package nogbeter.crossing;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Stack;

import nogbeter.paths.Path;
import nogbeter.paths.PathIndex;
import nogbeter.paths.results.intersections.IIntersections;
import nogbeter.paths.results.intersections.Intersection;
import nogbeter.points.twod.Vec;
import bezier.util.Tuple;

public class IntersectionsToCrossings<L extends PathIndex,R extends PathIndex> {

	List<Intersection<L, R>> inters;
	Path<L> l; Path<R> r;
	public IntersectionsToCrossings(
			IIntersections<L, R> inters, Path<L> l, Path<R> r) {
		this.inters = getIntersectionList(inters);
		sortIntersections();
		this.l = l;
		this.r = r;
	}

	private void sortIntersections() {
		// sorts on left path index first, then on right path index
		// The closed path index transformers makes sure that maximum 
		// pathindexes inside closed are mapped to minimum.
		Collections.sort(this.inters, new Comparator<Intersection<L,R>>() {
			@Override
			public int compare(Intersection<L, R> o1, Intersection<L, R> o2) {
				int cmp = o1.left.compareTo(o2.left);
				if(cmp == 0){
					return o1.right.compareTo(o2.right);
				} else {
					return cmp;
				}
			}
		});
	}

	private List<Intersection<L,R>> getIntersectionList(IIntersections<L, R> inters) {
		List<Intersection<L,R>> res = new ArrayList<Intersection<L,R>>();
		for(Intersection<L, R> in : inters){
			res.add(in);
		}
		return res;
	}
	

	private static final int MAX_NR_INTERSECTIONS_PER_CROSSING = 4;

	
	List<List<Intersection<L, R>>> groupIntersectionsOnPoint(List<Intersection<L, R>> ints){

		Stack<List<Intersection<L, R>>> res =
				new Stack<List<Intersection<L,R>>>();
		
		for(Intersection<L, R> in : ints){
			if(res.isEmpty() || !res.peek().get(0).left.isEq(in.left)){
				res.push(new ArrayList<Intersection<L,R>>(MAX_NR_INTERSECTIONS_PER_CROSSING));
			}
			res.peek().add(in);
		}
		
		return res;
	}
	
	public List<Crossing<L, R>> getCrossings(){
		List<Crossing<L, R>> res = new ArrayList<Crossing<L, R>>();
		for(List<Intersection<L,R>> intersPerPoint : groupIntersectionsOnPoint(inters)){
			LineStateBeforeAndAfter type = getCrossingType(intersPerPoint);
			Crossing<L, R> n = type.toCrossing(intersPerPoint.get(0));
			if(n!= null){
				res.add(n);
			}
		}	
		return res;
	}

	
	private Tuple<Vec,Vec> getOrderedTangents(PathIndex l, Vec tanl, PathIndex r, Vec tanr){
		if(l.isAdjacentOrderRight(r)){
			return new Tuple<Vec, Vec>(tanl, tanr);
		} else {
			return new  Tuple<Vec, Vec>(tanr, tanl);
		}
	}
	
	private Tuple<Vec,Vec> getOrderedTangentsLeft(Intersection<L,R> l, Intersection<L, R> r){
		return getOrderedTangents(l.left, l.tanl, r.left, r.tanl);
	}
	
	private Tuple<Vec,Vec> getOrderedTangentsRight(Intersection<L,R> l, Intersection<L, R> r){
		return getOrderedTangents(l.right, l.tanr, r.right, r.tanr);
	}
	
	private LineStateBeforeAndAfter getCrossingTypeDouble(Intersection<L,R> l, Intersection<L,R> r){
		if(l.tanl.isEq(r.tanl)){

			Tuple<Vec,Vec> orderTanR = getOrderedTangentsRight(l, r);
			return GetCrossingType.doubleIntersectionTypeL(l.tanl, orderTanR.l, orderTanR.r);
		} else {
			Tuple<Vec,Vec> orderTanL = getOrderedTangentsLeft(l, r);
			return GetCrossingType.doubleIntersectionTypeR(orderTanL.l, orderTanL.r, l.tanr);
		}
	}
	
	private LineStateBeforeAndAfter getCrossingTypeQuad(Intersection<L,R> a, Intersection<L,R> b,  
			Intersection<L,R> c,  Intersection<L,R> d){
		Tuple<Vec,Vec> orderTanL = getOrderedTangentsLeft(a, c);
		Tuple<Vec,Vec> orderTanR = getOrderedTangentsRight(a, b);
		return GetCrossingType.quadIntersectionType(orderTanL.l, orderTanL.r,
				orderTanR.l, orderTanR.r);
	}

	private LineStateBeforeAndAfter getCrossingType(List<Intersection<L, R>> intersPerPoint) {
		if(intersPerPoint.size() == 1){
			Intersection<L,R> i = intersPerPoint.get(0);
			return GetCrossingType.singleCrossingType(i.tanl, i.tanr);
		} else if(intersPerPoint.size() == 2){
			return getCrossingTypeDouble(intersPerPoint.get(0), intersPerPoint.get(1));
		} else if(intersPerPoint.size() == 4){
			return getCrossingTypeQuad(intersPerPoint.get(0),intersPerPoint.get(1),
					intersPerPoint.get(2),intersPerPoint.get(3));
		} else {
			throw new Error("Weird number of intersections per point " + intersPerPoint.size());
		}
	}
}
