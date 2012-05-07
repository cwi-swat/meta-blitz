package nogbeter.paths.results.intersections;


import nogbeter.paths.PathIndex;
import nogbeter.paths.results.transformers.PathIndexTupleTransformer;
import nogbeter.points.twod.Vec;

public class Intersection<LI extends PathIndex,RI extends PathIndex>{

	public final LI left;
	public final RI right;
	public final Vec loc;
	public final Vec tanl;
	public final Vec tanr;
	Intersection<LI,RI> next;
	
	public Intersection(LI left, RI right, Vec loc, Vec tanl, Vec tanr){
		this(left,right,loc,tanl,tanr,null);
	}
	
	public Intersection(LI left, RI right, Vec loc, Vec tanl, Vec tanr, Intersection<LI,RI> next) {
		this.next = next;
		this.left = left;
		this.right = right;
		this.loc = loc;
		this.tanl = tanl;
		this.tanr = tanr;
	}

	public<LPI extends PathIndex, RPI extends PathIndex>
		Intersection<LPI,RPI> transform(PathIndexTupleTransformer<LPI, RPI> trans){
		return new Intersection<LPI, RPI>(trans.left.transform(left),
										  trans.right.transform(right),loc,tanl,tanr);	
	}
	
	public Intersection<RI,LI> flip(){
		return new Intersection<RI,LI>(right, left,loc,tanr,tanl);
	}
	
	public String toString(){
		return "Intersection(" + left.toString() + "\n\t" + right.toString() + ")";
	}



	
}
