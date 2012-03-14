package bezier.segment;


public class BestProjection<A>{
	public A t;
	public double distanceSquaredUpperbound;
	
	public BestProjection(){
		t = null;
		distanceSquaredUpperbound = Double.POSITIVE_INFINITY;
	}
	
	public BestProjection(double initDist){
		distanceSquaredUpperbound = initDist;
		t = null;
	}
	
	public BestProjection(A init, double initDist) {
		this.t = init;
		distanceSquaredUpperbound = initDist;
	}
	
	public void update(A t, double dist){
		if(dist < distanceSquaredUpperbound){
			this.t = t;
			distanceSquaredUpperbound = dist;
		}
	}
}

