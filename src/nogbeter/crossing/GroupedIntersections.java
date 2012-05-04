package nogbeter.crossing;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import nogbeter.paths.Path;
import nogbeter.paths.PathIndex;
import nogbeter.paths.results.intersections.IIntersections;
import nogbeter.paths.results.intersections.Intersection;
import nogbeter.points.twod.Vec;
import bezier.paths.Constants;
import bezier.util.Tuple;

public class GroupedIntersections<L extends PathIndex,R extends PathIndex> {
	private final Map<Tuple<Path,Path>,List<Intersection<L, R>>> map;

	public GroupedIntersections(
			IIntersections<L, R> inters, Path<L> l, Path<R> r) {
		map = new HashMap<Tuple<Path,Path>, List<Intersection<L,R>>>();
		for(Intersection<L,R> i : inters){
			add(l.getSegment(i.left),r.getSegment(i.right),i);
		}
	}
	
	void add(Path l,Path r, Intersection<L,R> inter){
		Tuple<Path,Path> key = new Tuple<Path, Path>(l, r);
		if(!map.containsKey(key)){
			map.put(key, new ArrayList<Intersection<L,R>>());
		} 
		map.get(key).add(inter);
	}
	
	public Crossings<L,R> toCrossings(){
		List<Crossing<L,R>> res = new ArrayList<Crossing<L,R>>();
		for(List<Intersection<L, R>> inters : map.values()){
			Collections.sort(inters,new Comparator<Intersection<L, R>>() {
				@Override
				public int compare(Intersection<L, R> o1, Intersection<L, R> o2) {
					return Double.compare(o1.loc.x,o2.loc.x);
				}
			});
			for(List<Intersection<L,R>> intersPerPoint :
				groupPerIntersectionPoint(inters)){
				Crossing<L, R> n = makeCrossing(intersPerPoint.get(0),getCrossingType(intersPerPoint));
				if(n != null){
					res.add(n);
				}
			}
		}
		return new Crossings<L, R>(res);
	}

	private Crossing<L, R> makeCrossing(Intersection<L,R> inter, CrossingType crossingType) {
		return new Crossing<L, R>(inter.left, inter.right, inter.loc, crossingType);
	}

	private CrossingType getCrossingType(List<Intersection<L, R>> intersPerPoint) {
		if(intersPerPoint.size() == 1){
			Intersection<L,R> i = intersPerPoint.get(0);
			return GetCrossingType.singleIntersectionType(i.tanl, i.tanr);
		} else if(intersPerPoint.size() == 2){
			Intersection<L,R> l = intersPerPoint.get(0);
			Intersection<L,R> r = intersPerPoint.get(1);
			return GetCrossingType.doubleIntersectionType(
					l.tanl,r.tanl,l.tanr,r.tanr);
		} else if(intersPerPoint.size() == 4){
			Intersection<L,R> a = intersPerPoint.get(0);
			Intersection<L,R> b = intersPerPoint.get(1);
			Intersection<L,R> c = intersPerPoint.get(2);
			Intersection<L,R> d = intersPerPoint.get(3);
			return GetCrossingType.quadIntersectionType(
					a.tanl, b.tanl, c.tanl, d.tanl,
					a.tanr, b.tanr, c.tanr, d.tanr);
		} else {
			throw new Error("Weird number of intersections per point " + intersPerPoint.size());
		}
	}

	private List<List<Intersection<L,R>>> groupPerIntersectionPoint(List<Intersection<L, R>> inters){
		List<List<Intersection<L,R>>> res = new ArrayList<List<Intersection<L,R>>>();
		Queue<List<Intersection<L, R>>> xqueue = new LinkedList<List<Intersection<L,R>>>();
		for(Intersection<L,R> in : inters){
			moveFarAwayFromQueueToRes(xqueue,res, in.loc);
			addToCorrectList(xqueue,in);
		}
		res.addAll(xqueue);
		return res;
	}

	private void moveFarAwayFromQueueToRes(
			Queue<List<Intersection<L, R>>> xqueue,
			List<List<Intersection<L, R>>> res, Vec loc) {
		while(!xqueue.isEmpty() 
				&& Math.abs(xqueue.peek().get(0).loc.x - loc.x) > Constants.MAX_ERROR){
			res.add(xqueue.poll());
		}
	}
	

	private void addToCorrectList(Queue<List<Intersection<L, R>>> res,
			Intersection<L, R> in) {
		for(List<Intersection<L, R>> l : res){
			if(l.get(0).loc.isEqError(in.loc)){
				l.add(in);
				return;
			}
		}
		List<Intersection<L, R>> newL = new ArrayList<Intersection<L,R>>(4);
		newL.add(in);
		res.offer(newL);
		
	}
}
