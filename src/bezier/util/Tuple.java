package bezier.util;

public class Tuple<L, R> {
	public final L l; public final R r;
	public Tuple(L l, R r){
		this.l = l;
		this.r = r;
	}
	
	public final Tuple<R,L> flip(){
		return new Tuple<R,L>(r,l);
	}
}
