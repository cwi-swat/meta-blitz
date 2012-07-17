package deform.transform;

import util.Util;
import deform.Transform;
import deform.Vec;

public class Fisheye extends Transform{

	final Vec center;
	final double mag, maxDist, inv,  mag2;
	
	public Fisheye(Vec center, double mag, double maxDist){
		this.center = center;
		this.mag = mag;
		this.maxDist = maxDist;
		inv = (1-mag)/maxDist;
		mag2 = mag * mag;
	}
	
	@Override
	public Vec to(Vec from) {
		Vec to = from.sub(center);
		
		double dist = to.norm();
		if(dist > maxDist) {
			return from;
		}
		to = to.div(dist);
		double nd = inv * dist * dist + mag * dist;
		return center.add(to.mul(nd));
		
	}

	@Override
	public Vec from(Vec to) {
		Vec delta = to.sub(center);
		double dist = delta.norm();
		if(dist > maxDist) {
			return to;
		}
//		return to;
		delta = delta.div(dist);
//
		double discriminant = mag2 - 4 * (inv * -dist);
		if(discriminant < 0){
			System.out.println("Huh");
			return to;
		}
////		
//
		discriminant = Math.sqrt(discriminant);
		double ta = 2 * inv;
		double psol = (-mag + discriminant) / ta;
//		
		return center.add(delta.mul(psol));
	}

	
}
