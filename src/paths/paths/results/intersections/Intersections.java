package paths.paths.results.intersections;

import java.util.Iterator;

import paths.paths.paths.PathIndex;
import paths.paths.results.transformers.IPathIndexTransformer;
import paths.paths.results.transformers.PathIndexTupleTransformer;
import paths.points.twod.Vec;

import util.Tuple;



public class Intersections<LI extends PathIndex,RI extends PathIndex> implements IIntersections<LI, RI>, Iterable<Intersection<LI,RI>> {
	
	public static final Intersections NoIntersections = new Intersections((Intersection)null, null);
	
	private final Intersection<LI,RI> first;
	private final Intersection<LI,RI> last;
	
	public Intersections(LI l, RI r,Vec locl, Vec locr, Vec tanl, Vec tanr, IntersectionType typel, IntersectionType typer){
		this.first = new Intersection<LI, RI>(l,r,locl, locr, tanl, tanr, typel, typer);
		this.last = first;
	}
	
	public Intersections(Intersection<LI, RI> first, Intersection<LI, RI> last) {
		this.first = first;
		this.last = last;
	}
	
	public Iterator<Intersection<LI,RI>> iterator(){
		return new IntersectionsIterator<LI,RI>(first);
	}
	
	
	@Override
	public IIntersections<LI, RI> appendNorm(Intersections<LI, RI> lhs) {
		if(lhs.first == null){
			return this;
		}
		if(first == null){
			return lhs;
		}
		lhs.last.next = first;
		return new Intersections<LI, RI>(lhs.first, last);
	}
	
	@Override
	public IIntersections<LI, RI> appendFlipped(FlippedIntersections<LI, RI> lhs) {
		return appendNorm(lhs.normalise());
	}
	
	@Override
	public IIntersections<RI, LI> flip() {
		if(first == null){
			return (IIntersections<RI, LI>)(this);
		} else {
			return new FlippedIntersections<RI, LI>(this);
		}
	}
	
	
	private static class IntersectionsIterator<LI extends PathIndex,RI extends PathIndex> 
		implements Iterator<Intersection<LI,RI>>{

		Intersection<LI,RI> cur;
		
		IntersectionsIterator(Intersection<LI,RI> first){
			cur = first;
		}
		@Override
		public boolean hasNext() {
			return cur != null;
		}

		@Override
		public Intersection<LI,RI> next() {
			Intersection<LI,RI> res = cur;
			cur = cur.next;
			return res;
		}

		@Override
		public void remove() {
			throw new Error("Not implemented!@");
		}
		
	}


	@Override
	public <LPI extends PathIndex, RPI extends PathIndex> IIntersections<LPI, RPI> transform(
			PathIndexTupleTransformer<LPI, RPI> trans) {
		if(trans.doesNothing || first == null){
			return (IIntersections<LPI, RPI>)this;
		} else {
			Intersection <LPI,RPI> firstNew = first.transform(trans);
			Intersection <LPI,RPI> prev = firstNew;
			Intersection<LI,RI> cur = first.next;
			while(cur != null){
				Intersection <LPI,RPI> curn = cur.transform(trans);
				prev.next = curn;
				prev = curn;
				cur = cur.next;
			}
			return new Intersections<LPI, RPI>(firstNew,prev);
		}
	}

	@Override
	public IIntersections<LI, RI> append(IIntersections<LI, RI> r) {
		return r.appendNorm(this);
	}
}
