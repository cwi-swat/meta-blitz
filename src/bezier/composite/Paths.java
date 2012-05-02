package bezier.composite;

import java.awt.geom.PathIterator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import nogbeter.points.twod.Vec;
import nogbeter.transform.Matrix;
import nogbeter.transform.AffineTransformation;


import bezier.MutableGraph;
import bezier.paths.util.BestProjection;
import bezier.paths.util.TPair;
import bezier.segment.LengthMap;
import bezier.util.BBox;
import bezier.util.STuple;
import bezier.util.Tuple;
import bezier.util.Util;

public class Paths implements Area{
	
	public final List<Path> paths;
	public final BBox bbox;

	public Paths(List<Path> paths) {
		this.paths = paths;
		this.bbox = new BBox(paths.toArray(new Path[]{}));
	}
	
	public Paths(Path p){
		this(Collections.singletonList(p));
	}

	
	public Paths(Set<Path> done) {
		this(new ArrayList<Path>(done));
	}


	public Paths transform(Matrix m){
		List<Path> newCurves = new ArrayList<Path>(paths.size());
		for(Path c : paths){
			newCurves.add(c.transform(m));
		}
		return new Paths(newCurves);
	}
	
	public Vec getAt(TPaths t){
		return paths.get(t.pathIndex).getAt(t.t);
	}

	@Override
	public boolean isInside(Vec p) {
		if(bbox.isInside(p)){
			int nrInside = 0;
			for(Path path : paths){
				nrInside+= path.isInside(p) ? 1 : 0;
			}
			return nrInside % 2 == 1;
		} else {
			return false;
		}
	}

	@Override
	public BBox getBBox() {
		return bbox;
	}
	
	public List<Vec> getIntersectionPoints(Paths r){
		Tuple<Intersections,Intersections> inter = getIntersections(r);
		List<Vec> result = new ArrayList<Vec>();
		for(int i = 0 ; i < paths.size() ; i++){
			for(double d : inter.l.intersectionsPerPath.get(i)){
				result.add(paths.get(i).getAt(d));
			}
		}
		return result;
	}
	
	public Tuple<Intersections, Intersections> getIntersections(Paths r){
		Intersections interl = new Intersections(paths.size());
		Intersections interr = new Intersections(r.paths.size());
		if(!bbox.overlaps(r.bbox)){
			return new Tuple<Intersections, Intersections> (interl,interr);
		}
		for(int i = 0 ; i < paths.size() ; i++){
			for(int j = 0; j < r.paths.size() ; j++){
				List<TPair> localIntersections = paths.get(i).getIntersections(r.paths.get(j));
				for(TPair tp : localIntersections){
					interl.add(i, tp.tl);
					interr.add(j, tp.tr);
				}
			}
		}
		return new Tuple<Intersections, Intersections> (interl,interr);
	}
	
	
	List<Path> getSegments(Intersections inter, Paths other, boolean shouldBeInside){
		List<Path> result = new ArrayList<Path>();
		for(int i = 0 ; i < paths.size() ; i++){
			result.addAll(paths.get(i).getSegments(inter.getFor(i), other, shouldBeInside));
		}
		return result;
	}
	
	private Path findUniqueConnecting(Path p , Set<Path> others){
		Path connecting = null;
		boolean choice = false;
		for(Path q : others){
			if(p == q){
				continue;
			}
			if(p.canAppend(q) || p.canAppendReversed(q)){
				if(connecting != null){
					choice = true;
				} else{
					connecting = q;
					if(p.willMakeClosed(q) || p.willMakeClosedReversed(q)){
						return connecting;
					}
				}
			}
		}
		if(!choice){
			return connecting;
		} else {
			return null;
		}
	}
	
	private Tuple<Path,Path> findUniquelyConnectingPair(Set<Path> segments){
		for(Path p : segments){
			Path connect = findUniqueConnecting(p,segments);
			if(connect != null){
				return new Tuple<Path, Path>
					(p,connect);
			}
		}
		return null;
	}
	
	public Set<Path> mergeSegments(Set<Path> segments){
		Set<Path> closedPaths = new HashSet<Path>();
		for(Path p : segments){
			if(p.isClosed()){
				closedPaths.add(p);
			}
		}
		segments.removeAll(closedPaths);
		Tuple<Path,Path> cp;
		while((cp = findUniquelyConnectingPair(segments)) != null){
			segments.remove(cp.l); segments.remove(cp.r);
			Path merged = cp.l.append(cp.r);
			if(merged.isClosed()){
				closedPaths.add(merged);
			} else {
				segments.add(merged);
			}
		} 
		if(!segments.isEmpty()){
			System.out.println("Left over segments:");
			for(Path p : segments){
				System.out.println(p);
			}
		}
		return closedPaths;
	}
	
	public Paths merge(boolean shouldBeInside, Paths r, boolean shouldBeInsideR){
		Tuple<Intersections, Intersections> intersect = getIntersections(r);
		if(intersect.l.isEmpty()){
			List<Path> result = new ArrayList<Path>();
			if(!shouldBeInside){
				result.addAll(paths);
			}
			if(!shouldBeInsideR){
				result.addAll(r.paths);
			}
			return new Paths(result);
		}
		List<Path> segmentsl = getSegments(intersect.l, r, shouldBeInside);
		
		
		List<Path> segmentsr = r.getSegments(intersect.r, this, shouldBeInsideR);
//		if((segmentsl.size() + segmentsr.size()) % 2 != 0){
//			for(Path seg : segmentsl){
//				System.out.print(seg);
//			}
//			System.out.printf("NOT ENOUGH SEGMENT %d!!!",(segmentsl.size() + segmentsr.size()));
//		}
		Set<Path> both = new HashSet<Path>(segmentsl);
		both.addAll(segmentsr);
		return new Paths(mergeSegments(both));
	}
	

	public Paths union(Paths r){
		return merge(false,r,false);
	}

	public Paths substract(Paths r){
		return merge(false,r,true);
	}
	
	public Paths intersection(Paths r){
		return merge(true,r,true);
	}
	
	
	public Paths makeMonotomous(){
		List<Path> result = new ArrayList<Path>();
		for(Path c : paths){
			result.add(c.makeMonotomous());
		}
		return new Paths(result);
	}
	

	public Paths projectOn(Path guide, LengthMap lm){
		List<Path> result = new ArrayList<Path>(paths.size());
		for(Path p : paths){
			result.add(p.projectOn(guide, lm));
		}
		return new Paths(result);
	}
	
	public String toString(){
		StringBuffer b = new StringBuffer();
		b.append("Paths:\n");
		for(Path c : paths){
			b.append(c.toString());
			b.append('\n');
		}
		b.append('\n');
		return b.toString();
	}
	
	public Paths liftAll(){
		List<Path> result = new ArrayList<Path>();
		for(Path p : paths){
			result.add(p.liftAll());
		}
		return new Paths(result);
	}

	public int nr() {
		return paths.size();
	}

	public Path get(int i) {
		return paths.get(i);
	}

	public Paths transform(AffineTransformation translate) {
		return transform(translate.to);
	}
	
	public MutableGraph<Path> containsGraph(){
		MutableGraph<Path> containsGraph = new MutableGraph<Path>(paths);
		for(int i = 0 ; i < paths.size() ; i++){
			for(int j = 0 ; j < paths.size() ; j++){
				if(i != j && paths.get(j).isInside(paths.get(i).getArbPoint())){
					containsGraph.addEdge(new STuple<Path>(paths.get(j), paths.get(i)));
//					System.out.println("Contains!!");
				}
			}
		}

		return containsGraph;
	}
	
	class PathsIterator implements PathIterator{

		int cur;
		PathIterator curP; 
		
		public PathsIterator() {
			cur = 0;
			setCurP();
		}
		
		@Override
		public int getWindingRule() {
			return PathIterator.WIND_EVEN_ODD;
		}

		@Override
		public boolean isDone() {
			return curP == null || curP.isDone() && cur == paths.size()-1;
		}

		@Override
		public void next() {

			curP.next();
			if(curP.isDone()){
				cur++;
				setCurP();
			}
		}

		public void setCurP() {
			if(cur != paths.size()){
				curP = paths.get(cur).getPathIterator();
			} else {
				curP = null;
			}
		}

		@Override
		public int currentSegment(float[] coords) {
//			int res = curP.currentSegment(coords);
//			switch(res){
//			case PathIterator.SEG_CLOSE: System.out.printf("Close\n"); break;
//			case PathIterator.SEG_MOVETO: System.out.printf("Move %f %f\n", coords[0],coords[1]); break;
//			case PathIterator.SEG_LINETO: System.out.printf("Line %f %f\n", coords[0],coords[1]); break;
//			case PathIterator.SEG_QUADTO: System.out.printf("Quad %f %f %f %f\n", coords[0],coords[1],coords[2],coords[3]); break;
//			case PathIterator.SEG_CUBICTO: System.out.printf("Quad %f %f %f %f %f %f\n", coords[0],coords[1],coords[2],coords[3],coords[4],coords[5]); break;
//			default: System.out.println("HUH?");
//			}
//			return res;
			return curP.currentSegment(coords);
		}

		@Override
		public int currentSegment(double[] coords) {
			return curP.currentSegment(coords);
		
		}
		
	}
	
	public PathIterator getPathIterator(){
		return new PathsIterator();
	}
	

	public TPaths project(final Vec p){
		final BestProjection<TPaths> best = new BestProjection<TPaths>();
		return project(p, best);
	}

	public TPaths project(final Vec p, final BestProjection<TPaths> best) {
		if(paths.isEmpty()){
			return null;
		}
		final List<Integer> indexesNearestFirst = Util.natListTill(paths.size());
		final List<Double> distanceMiddles = new ArrayList<Double>(paths.size());
		for(int i : indexesNearestFirst){
			double dist = paths.get(i).getStartPoint().distanceSquared(p);
			distanceMiddles.add(dist);
			best.update(new TPaths(i,0), dist);
		}
		Collections.sort(indexesNearestFirst,new Comparator<Integer>() {
			@Override
			public int compare(Integer o1, Integer o2) {
				return Double.compare(
						distanceMiddles.get(o1), distanceMiddles.get(o2));
			}
		});
		for(int i : indexesNearestFirst){
			BestProjection<Double> bestLocal = new BestProjection<Double>(best.distanceSquaredUpperbound);
			paths.get(i).project(p,bestLocal);
			if(bestLocal.t != null){
				best.update(new TPaths(i,bestLocal.t) , bestLocal.distanceSquaredUpperbound);
			}
		}
		return best.t;
	}
	
	public TPaths projectNoSort(final Vec p, final BestProjection<TPaths> best) {
		if(paths.isEmpty()){
			return null;
		}
		BestProjection<Double> bestLocal = new BestProjection<Double>(best.t.t,best.distanceSquaredUpperbound);
		paths.get(best.t.pathIndex).projectNoSort(p,bestLocal);
		if(bestLocal.t != null){
			best.update(new TPaths(best.t.pathIndex,bestLocal.t) , bestLocal.distanceSquaredUpperbound);
		}
		for(int i = 0 ; i < paths.size() ; i++){
			if(i == best.t.pathIndex) continue;
			bestLocal = new BestProjection<Double>(0.0,best.distanceSquaredUpperbound);
			paths.get(i).projectNoSort(p,bestLocal);
			if(bestLocal.t != null){
				best.update(new TPaths(i,bestLocal.t) , bestLocal.distanceSquaredUpperbound);
			}
		}
		return best.t;
	}
	
	public STuple<TPaths> project(final Paths other){
		if(paths.isEmpty() || other.paths.isEmpty()){
			return null;
		}
		BestProjection<STuple<TPaths>> best = new BestProjection<STuple<TPaths>>();
		List<STuple<Integer>> indexesNearestFirst = Util.natPairs(paths.size(),other.paths.size());
		final List<Double> distanceMiddles = new ArrayList<Double>(indexesNearestFirst.size());
		for(STuple<Integer> tpair : indexesNearestFirst){
			double dist = paths.get(tpair.l).getStartPoint().distanceSquared(
					other.paths.get(tpair.r).getStartPoint());
			STuple<TPaths> t = new STuple<TPaths>(new TPaths(tpair.l, 0), new TPaths(tpair.r, 0));
			best.update(t,dist);
			distanceMiddles.add(dist);
		}
		Collections.sort(indexesNearestFirst,new Comparator<STuple<Integer>>() {
			@Override
			public int compare(STuple<Integer> o1, STuple<Integer> o2) {
				return Double.compare(
						distanceMiddles.get(o1.l * other.paths.size() + o1.r)
						, distanceMiddles.get(o2.l * other.paths.size() + o2.r));
			}
		});
		for(STuple<Integer> i : indexesNearestFirst){
			BestProjection<TPair> bestLocal = new BestProjection<TPair>(best.distanceSquaredUpperbound);
			paths.get(i.l).project(other.paths.get(i.r),bestLocal);
			if(bestLocal.t != null){
				best.update(new STuple<TPaths>(new TPaths(i.l,bestLocal.t.tl), new TPaths(i.r,bestLocal.t.tr)) , bestLocal.distanceSquaredUpperbound);
			}
		}
		return best.t;
	}
}
