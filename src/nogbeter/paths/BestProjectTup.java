package nogbeter.paths;

import bezier.util.Tuple;

public class BestProjectTup<L,R> extends BestProject<Tuple<L,R>>{
	
	public BestProjectTup(double distSquared, Tuple<L, R> t) {
		super(distSquared,t);
	}
	
	public BestProjectTup(double distSquared, L l, R r){
		this(distSquared,new Tuple<L, R>(l, r));
	}

	public BestProjectTup<R,L> flip(){
		return new BestProjectTup<R,L>(distSquared,t.flip());
	}
	
	public BestProjectTup<L,R> choose(BestProjectTup<L,R> rhs){
		return (BestProjectTup)super.choose(rhs);
	}

}
