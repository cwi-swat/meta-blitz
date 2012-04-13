package bezier.paths;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import bezier.paths.util.PathParameter;
import bezier.points.Vec;

public class Intersections {
	final List<Intersection> inters;
	
	Intersections(){
		inters  = new ArrayList<Intersection>();
	}

	void add(Intersection s){
		inters.add(s);
	}
	
	private boolean isBorder(double tl) {
		return tl == 0.0 || tl == 1.0;
	}
	
	enum IntersectionType{
		Touch,
		Cross,
		Enter,
		Exit,
		Parallel;
	}
	

	public IntersectionType getIntersectionType(double sig1, double sig2) {
		if(sig1 != 0 && sig2 != 0){
			if(sig1 == sig2){
				return IntersectionType.Cross;
			} else {
				return IntersectionType.Touch;
			}
		} else {
			return intersectionTypePar(sig1, sig2);
		}
	}

	public IntersectionType intersectionTypePar(double sig1, double sig2) {
		if(sig1 == 0 && sig2 == 0){
			return IntersectionType.Parallel;
		} else if(sig1 == 0){
			return IntersectionType.Exit;
		} else { // sig2 == 0
			return IntersectionType.Enter;
		}
	}
	
	private IntersectionType intersectionType(Vec dirA, Vec toInterB, Vec fromInterB){
		Vec normA = dirA.perpendicularCCW();
		double sig1 = Math.signum(normA.dot(toInterB));
		double sig2 =  Math.signum(normA.dot(fromInterB));
		return getIntersectionType(sig1, sig2);
	}

	
	private IntersectionType intersectionType(Vec toInterA, Vec fromInterA, Vec toInterB, Vec fromInterB){
		Vec normToA = toInterA.perpendicularCCW();
		Vec normFromA = fromInterA.perpendicularCCW();
		double sig1 = Math.signum(normToA.dot(toInterB));
		double sig2 =  Math.signum(normFromA.dot(fromInterB));
		double sig3 = Math.signum(normToA.dot(fromInterB));
		double sig4 = Math.signum(normFromA.dot(toInterB));
		if(sig3 == 0 || sig4 == 0) return intersectionTypePar(sig3, sig4);
		return getIntersectionType(sig1, sig2);
	}
	
	enum CrossingsType{
		Cross,
		Enter,
		Exit;
	}
	
	
	class Crossing{
		public final PathParameter tl, tr;
		public CrossingsType cross;
		public Crossing(PathParameter tl, PathParameter tr, CrossingsType cross) {
			this.tl = tl;
			this.tr = tr;
			this.cross = cross;
		}
	}
	
	List<Crossing> getCrossings(){
		List<Crossing> result = new ArrayList<Intersections.Crossing>();
		List<List<Intersection>> groupInters = groupIntersections();
		for(List<Intersection> i : groupInters){
			if(i.size() == 1){
				Intersection inter = i.get(0);
				assert i.get(0).l.t != 0 && i.get(0).l.t != 1 
						&& i.get(0).r.t != 0 && i.get(0).r.t != 1;
				result.add(new Crossing(inter.l.translateBack(), inter.r.translateBack(), CrossingsType.Cross));
			} else if(i.size() == 2){
				IntersectionType type;
				if(isBorder(i.get(0).l.t)){
					type = intersectionType(i.get(0).r.getTan(),i.get(0).l.getTan(),i.get(1).l.getTan());
				} else {
					type = intersectionType(i.get(0).l.getTan(),i.get(0).r.getTan(),i.get(1).r.getTan());
				}
				addCrossing(result, i, type);
			} else if(i.size() == 4){
				IntersectionType type = intersectionType(i.get(0).l.getTan(), i.get(2).l.getTan(), i.get(0).r.getTan(), i.get(1).r.getTan());
				addCrossing(result, i, type);
			} else {
				throw new Error("Weird crossings! Should not happen!");
			}
		}
		return result;
	}

	public void addCrossing(List<Crossing> result, List<Intersection> i,
			IntersectionType type) {
		switch(type){
			case Touch: break;
			case Parallel: break;
			case Cross: result.add(new Crossing(i.get(0).l.translateBack(), i.get(0).r.translateBack(), CrossingsType.Cross)); break;
			case Enter:  result.add(new Crossing(i.get(0).l.translateBack(), i.get(0).r.translateBack(), CrossingsType.Enter)); break;
			case Exit: result.add(new Crossing(i.get(0).l.translateBack(), i.get(0).r.translateBack(), CrossingsType.Exit)); break;
		}
	}
	
	List<List<Intersection>> groupIntersections(){
		Collections.sort(inters,new Comparator<Intersection>() {
			@Override
			public int compare(Intersection o1, Intersection o2) {
				int cmp = o1.getVec().compareXY(o2.getVec());
				if(cmp == 0){
					int cmp2 = Double.compare(o1.l.t,o2.l.t);
					if(cmp2 == 0){
						return  Double.compare(o1.r.t,o2.r.t);
					}
					return cmp2;
				}
				return cmp;
			}
		});
		List<List<Intersection>> grouped = new ArrayList<List<Intersection>>();
		int i = 0;
		while(i < inters.size()){
			Vec cur = inters.get(i).getVec();
			List<Intersection> group = new ArrayList<Intersection>(4);
			do{
				group.add(inters.get(i));
			} while(i < inters.size() && inters.get(i).getVec().isEq(cur));
			grouped.add(group);
		}
		return grouped;
	}
}
