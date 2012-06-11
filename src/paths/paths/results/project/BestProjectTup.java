package paths.paths.results.project;

import paths.paths.paths.PathIndex;
import paths.paths.results.intersections.Intersection;
import paths.paths.results.transformers.PathIndexTupleTransformer;
import util.Tuple;

public class BestProjectTup{
	
	public static final BestProjectTup noBestYet = new BestProjectTup(Double.POSITIVE_INFINITY);
	
	public final double distSquared;
	public final PathIndex l;
	public final PathIndex r;
	
	public BestProjectTup(double distSquared){
		this(distSquared,null,null);
	}
	
	
	public BestProjectTup(double distSquared, PathIndex l, PathIndex r){
		this.distSquared = distSquared;
		this.l = l;
		this.r = r;
	}

	public BestProjectTup flip(){
		return new BestProjectTup(distSquared,r,l);
	}
	
	public BestProjectTup choose(BestProjectTup rhs){
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
	
	public
	BestProjectTup transform(PathIndexTupleTransformer trans){
		return new BestProjectTup(distSquared,trans.left.transform(l),
									  trans.right.transform(r));	
	}
	
	public String toString(){
		return "BestProjectTup("+distSquared + "," + l + "," + r + ")";
	}
	

	
}
