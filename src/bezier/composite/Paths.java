package bezier.composite;

import java.awt.geom.Path2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.text.Segment;

import bezier.points.Matrix;
import bezier.points.Vec;
import bezier.segment.LengthMap;
import bezier.segment.TPair;
import bezier.segment.curve.Curve;
import bezier.util.BBox;
import bezier.util.Tuple;

public class Paths implements Area{
	
	public final List<Path> paths;
	public final BBox bbox;

	public Paths(List<Path> paths) {
		this.paths = paths;
		BBox[] bboxs = new BBox[paths.size()];
		for(int i = 0; i < paths.size() ; i++){
			bboxs[i] = paths.get(i).getBBox();
		}
		this.bbox = BBox.merge(bboxs);
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
	
	public Path2D.Double toAWT(){
		Path2D.Double res = new Path2D.Double();
		for(Path c: paths){
			res.append(c.toAWT(), false);
		}
		return res;
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
}
