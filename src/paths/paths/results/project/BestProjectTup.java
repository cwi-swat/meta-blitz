package paths.paths.results.project;

import paths.paths.paths.PathIndex;
import paths.paths.results.intersections.Intersection;
import paths.paths.results.transformers.PathIndexTupleTransformer;
import util.Tuple;

public class BestProjectTup<L extends PathIndex,R extends PathIndex>{
	
	public static final BestProjectTup noBestYet = new BestProjectTup(Double.POSITIVE_INFINITY);
	
	public final double distSquared;
	public final L l;
	public final R r;
	
	public BestProjectTup(double distSquared){
		this(distSquared,null,null);
	}
	
	
	public BestProjectTup(double distSquared, L l, R r){
		this.distSquared = distSquared;
		this.l = l;
		this.r = r;
	}

	public BestProjectTup<R,L> flip(){
		return new BestProjectTup<R,L>(distSquared,r,l);
	}
	
	public BestProjectTup<L,R> choose(BestProjectTup<L,R> rhs){
		if(distSquared == rhs.distSquared){
			if(l != null) {
				return this;
			} else {
				return rhs;
			}
		}
		if(distSquared < rhs.distSquared){
			return this;
		} else {
			return rhs;
		}
	}
	
	public<LPI extends PathIndex, RPI extends PathIndex>
	BestProjectTup<LPI,RPI> transform(PathIndexTupleTransformer<LPI, RPI> trans){
		return new BestProjectTup<LPI, RPI>(distSquared,trans.left.transform(l),
									  trans.right.transform(r));	
	}
	
	public String toString(){
		return "BestProjectTup("+distSquared + "," + l + "," + r + ")";
	}
	

	
}
