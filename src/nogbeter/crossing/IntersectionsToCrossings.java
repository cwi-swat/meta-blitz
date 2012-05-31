package nogbeter.crossing;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Stack;

import nogbeter.paths.Path;
import nogbeter.paths.PathIndex;
import nogbeter.paths.compound.ClosedPath;
import nogbeter.paths.results.intersections.IIntersections;
import nogbeter.paths.results.intersections.Intersection;
import nogbeter.paths.results.intersections.IntersectionType;
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
					cmp = o1.typel.compareTo(o2.typel);
					if(cmp == 0){
						cmp = o1.right.compareTo(o2.right);
						if(cmp == 0){
							return o1.typer.compareTo(o2.typer);
						}
					} 
				} 
				return cmp;
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
	
	public CrossingsInfo<L,R> getCrossingsInfo(){
		List<Crossing<L, R>> crossings = new ArrayList<Crossing<L,R>>();
		Set<Tuple<Path,Path>> parallels = new HashSet<Tuple<Path,Path>>();
		Set<Tuple<Path,Path>> antiParallels = new HashSet<Tuple<Path,Path>>();
		for(int i = 0 ; i < inters.size() ; ){
			Tuple<CrossingType, Integer> crossPlusNr = getCrossingType(inters, i);
			CrossingType t = crossPlusNr.l;
			switch(t){
			case Enter: crossings.add(makeCrossing(inters.get(i), true)); break;
			case Exit: crossings.add(makeCrossing(inters.get(i), false)); break;
			case Parallel: parallels.add(makeClosePathTuple(inters.get(i))); break;
			case AntiParallel: antiParallels.add(makeClosePathTuple(inters.get(i))); break;
			case Touch: break;
			}
			i+=crossPlusNr.r;
		}
		return new CrossingsInfo<L, R>(l,r,crossings, parallels, antiParallels);
	}

	private Tuple<Path, Path> makeClosePathTuple(Intersection<L, R> intersection) {
		return new Tuple<Path, Path>(l.getClosedPath(intersection.left),r.getClosedPath(intersection.right));
	}

	private Crossing<L, R> makeCrossing(Intersection<L, R> intersection,
			boolean leftAfterInside) {
		return new Crossing<L, R>(intersection.left, intersection.right, intersection.locl, leftAfterInside);
	}

	public Tuple<CrossingType, Integer> getCrossingType(List<Intersection<L, R>> ints, int i) {
		CrossingType type;
		Intersection<L, R> in = ints.get(i);
		int nr;
		if(in.typel == IntersectionType.Normal){
			if(in.typer == IntersectionType.Normal){
				type= GetCrossingType.singleType(in.tanl, in.tanr);
				nr = 1;
			} else {
				Intersection<L, R> in2 = ints.get(i+1);
				type = GetCrossingType.doubleTypeL(in.tanl, in.tanr, in2.tanr);
				nr =2 ;
			}
		} else {
			if(in.typer == IntersectionType.Normal){
				Intersection<L, R> in2 = ints.get(i+1);
				type = GetCrossingType.doubleTypeR(in.tanl, in2.tanl, in.tanr);
				nr =2 ;
			} else {
				Intersection<L, R> in2 = ints.get(i+1);
				Intersection<L, R> in3 = ints.get(i+2);
				type = GetCrossingType.quadTypeL(in.tanl, in3.tanl, in.tanr, in2.tanr);
				nr =4;
			}
		}
		return new Tuple<CrossingType, Integer>(type, nr);
	}

	
}
