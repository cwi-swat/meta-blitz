package paths.paths.results.intersections;

import java.util.Iterator;

import paths.paths.paths.PathIndex;
import paths.paths.results.transformers.IPathIndexTransformer;
import paths.paths.results.transformers.PathIndexTupleTransformer;


public class FlippedIntersections<LI extends PathIndex,RI extends PathIndex> 
												implements IIntersections<LI, RI> {
	
	final Intersections<RI,LI> real;

	public FlippedIntersections(Intersections<RI, LI> real) {
		this.real = real;
	}

	Intersections<LI,RI> normalise(){
		Iterator<Intersection<RI,LI>> it = real.iterator();
		if(!it.hasNext()){
			return Intersections.NoIntersections;
		} else {
			Intersection<RI,LI> cur = it.next();
			Intersection<LI,RI> first = cur.flip();
			Intersection<LI,RI> prev = first;
			while(it.hasNext()){
				cur = it.next();
				prev.next = cur.flip();
				prev = prev.next;
			}
			return new Intersections<LI, RI>(first, prev);
		}
	}

	@Override
	public IIntersections<LI, RI> appendFlipped(FlippedIntersections<LI, RI> lhs) {
		return new FlippedIntersections<LI, RI>((Intersections<RI, LI>)real.appendNorm(lhs.real));
	}

	@Override
	public IIntersections<LI, RI> appendNorm(Intersections<LI, RI> r) {
		return normalise().append(r);
	}

	@Override
	public Iterator<Intersection<LI, RI>> iterator() {
		return normalise().iterator();
	}

	

	@Override
	public IIntersections<RI, LI> flip() {
		return real;
	}

	@Override
	public <LPI extends PathIndex, RPI extends PathIndex> IIntersections<LPI, RPI> transform(
			PathIndexTupleTransformer<LPI, RPI> trans) {
		if(trans.doesNothing){
			return (IIntersections<LPI, RPI>)this;
		} else {
			return normalise().transform(trans);
		}
	}

	@Override
	public IIntersections<LI, RI> append(IIntersections<LI, RI> r) {
		return r.appendFlipped(this);
	}



}
