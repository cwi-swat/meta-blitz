package nogbeter.paths.results.intersections;


import nogbeter.paths.PathIndex;
import nogbeter.paths.results.transformers.PathIndexTupleTransformer;

public class Intersection<LI extends PathIndex,RI extends PathIndex> {

	public final LI left;
	public final RI right;
//	public final Vec where;
//	public final Vec tanLeft;
//	public final Vec tanRight;
	Intersection<LI,RI> next;
	
	public Intersection(LI left, RI right){
		this(left,right,null);
	}
	
	public Intersection(LI left, RI right,Intersection<LI,RI> next) {
		this.next = next;
		this.left = left;
		this.right = right;
	}

	public<LPI extends PathIndex, RPI extends PathIndex>
		Intersection<LPI,RPI> transform(PathIndexTupleTransformer<LPI, RPI> trans){
		return new Intersection<LPI, RPI>(trans.left.transform(left),
										  trans.right.transform(right));	
	}
	
	public Intersection<RI,LI> flip(){
		return new Intersection<RI,LI>(right, left);
	}
	
	public String toString(){
		return "Intersection(" + left.toString() + "," + right.toString() + ")";
	}
}
