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

public class GroupedIntersections<L extends PathIndex,R extends PathIndex> {

	List<Intersection<L, R>> inters;
	Path<L> l; Path<R> r;
	public GroupedIntersections(
			IIntersections<L, R> inters, Path<L> l, Path<R> r) {
		this.inters = new ArrayList<Intersection<L,R>>();
		for(Intersection<L, R> in : inters){
			this.inters.add(in);
		}
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
		this.l = l;
		this.r = r;
	}
	

	private static final int MAX_NR_INTERSECTIONS_PER_CROSSING = 4;

	List<List<Intersection<L, R>>> 
		groupIntersectionsOnLeftSegments(){
		
		List<List<Intersection<L, R>>> res =
				new ArrayList<List<Intersection<L,R>>>();
		Path<PathIndex> prev = null;
		for(Intersection<L,R> in : inters){
			Path<PathIndex> cur = l.getSegment(in.left);
			if(prev == null || cur !=prev ){
				res.add(new ArrayList<Intersection<L, R>>());
			}
			res.get(res.size()-1).add(in);
			prev = cur;
		}
		return res;
	}
	
	List<List<Intersection<L, R>>> 
	groupIntersectionsOnPointPerLeftSegment(List<Intersection<L, R>> ints){

		Stack<List<Intersection<L, R>>> res =
				new Stack<List<Intersection<L,R>>>();
		
		for(Intersection<L, R> in : ints){
			if(res.isEmpty() || !res.peek().get(0).loc.isEqError(in.loc)){
				res.push(new ArrayList<Intersection<L,R>>(MAX_NR_INTERSECTIONS_PER_CROSSING));
			}
			res.peek().add(in);
		}
		// wrap around case
		if(res.get(0).get(0).loc.isEqError(res.peek().get(0).loc)){
			res.get(0).addAll(res.pop());
		}
		return res;
	}
	
	public List<List<Crossing<L, R>>> getCrossings(){
		List<List<Crossing<L, R>>> res = new ArrayList<List<Crossing<L, R>>>();
		for(List<Intersection<L,R>> grouped : groupIntersectionsOnLeftSegments()){
			List<Crossing<L, R>> segCross = new ArrayList<Crossing<L,R>>();
			for(List<Intersection<L, R>> intersPerPoint : groupIntersectionsOnPointPerLeftSegment(grouped)){
				LineStateBeforeAndAfter type = getCrossingType(intersPerPoint);
				Crossing<L, R> n = type.toCrossing(intersPerPoint.get(0));
				if(n!= null){
					segCross.add(n);
				}
			}
			if(!segCross.isEmpty()){
				res.add(segCross);
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
