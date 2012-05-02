package nogbeter;

public class Intersections<LI,RI> {
	
	public final Intersections next;
	public final LI left;
	public final RI right;
	
	
	public Intersections(LI left, RI right,Intersections next) {
		this.next = next;
		this.left = left;
		this.right = right;
	}
	
	

}
