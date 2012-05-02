package bezier.paths.util;

import nogbeter.points.twod.Vec;


public final class BestProjection<A>{
	public A t;
	public double distanceSquaredUpperbound;
	public Vec v;
	
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
	
	public BestProjection(A init, double initDist, Vec v) {
		this.t = init;
		distanceSquaredUpperbound = initDist;
		this.v = v;
	}

	public void update(A t, double dist){
		if(dist < distanceSquaredUpperbound){
			this.t = t;
			distanceSquaredUpperbound = dist;
		}
	}
	
	public void update(A t, double dist, Vec v){
		if(dist < distanceSquaredUpperbound){
			this.t = t;
			distanceSquaredUpperbound = dist;
			this.v = v;
		}
	}
}

