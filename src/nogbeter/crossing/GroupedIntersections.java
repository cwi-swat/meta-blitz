package nogbeter.crossing;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Stack;

import nogbeter.paths.Path;
import nogbeter.paths.PathIndex;
import nogbeter.paths.results.intersections.IIntersections;
import nogbeter.paths.results.intersections.Intersection;
import nogbeter.points.twod.Vec;
import bezier.paths.Constants;
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
	
	
	
	// ints = intersections per point
	public Crossings<L,R> getCrossings(){
		
		List<Crossing<L, R>> res = new ArrayList<Crossing<L,R>>();
		for(List<Intersection<L,R>> grouped : groupIntersectionsOnLeftSegments()){
			for(List<Intersection<L, R>> intersPerPoint : groupIntersectionsOnPointPerLeftSegment(grouped)){
				CrossType type = getCrossingType(intersPerPoint);
				Crossing<L, R> n = makeCrossing(intersPerPoint.get(0), type);
				res.add(n);
			}
		}	
		return new Crossings<L, R>(res);
		
	}

	private Crossing<L, R> makeCrossing(Intersection<L,R> inter, CrossType crossingType) {
		return new Crossing<L, R>(inter.left, inter.right, inter.loc, crossingType);
	}

	private CrossType getCrossingType(List<Intersection<L, R>> intersPerPoint) {
		System.out.printf("%s %s\n",intersPerPoint.get(0).loc,intersPerPoint.size());
		if(intersPerPoint.size() == 1){
			Intersection<L,R> i = intersPerPoint.get(0);
			return GetCrossingType.singleIntersectionType(i.tanl, i.tanr);
		} else if(intersPerPoint.size() == 2){
			Intersection<L,R> l = intersPerPoint.get(0);
			Intersection<L,R> r = intersPerPoint.get(1);
			System.out.println(l);
			System.out.println(r);
			if(l.tanl.isEq(r.tanl)){
				if(l.right.isAdjacentOrderRight(r.right)){
					return GetCrossingType.doubleIntersectionTypeL(
							l.tanl,l.tanr,r.tanr);
				} else {
					return GetCrossingType.doubleIntersectionTypeL(
							l.tanl,r.tanr,l.tanr);
				}
			} else {
				if(l.left.isAdjacentOrderRight(r.left)){
					return GetCrossingType.doubleIntersectionTypeR(
							l.tanl,r.tanl,l.tanr);
				} else {
					return GetCrossingType.doubleIntersectionTypeR(
							r.tanl,l.tanl,l.tanr);
				}

			}
		} else if(intersPerPoint.size() == 4){
			
			Intersection<L,R> a = intersPerPoint.get(0);
			Intersection<L,R> b = intersPerPoint.get(1);
			Intersection<L,R> c = intersPerPoint.get(2);
			Intersection<L,R> d = intersPerPoint.get(3);
			Vec la, lb;
			if(a.left.isAdjacentOrderRight(c.left)){
				la = a.tanl; lb = c.tanl;
			} else {
				lb = a.tanl; la = c.tanl;
			}
			Vec ra, rb;
			if(a.right.isAdjacentOrderRight(b.right)){
				ra = a.tanr; rb = b.tanr;
			} else {
				ra = b.tanr; rb = a.tanr;
			}
			return GetCrossingType.quadIntersectionType(la,lb,ra,rb);
		} else {
			throw new Error("Weird number of intersections per point " + intersPerPoint.size());
		}
	}
}
