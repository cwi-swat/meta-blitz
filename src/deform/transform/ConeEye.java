package deform.transform;

import util.Util;
import deform.Transform;
import deform.Vec;

public class ConeEye extends Transform{

	final Vec center;
	final double maxDist;
	
	public ConeEye(Vec center,  double maxDist){
		this.center = center;
		this.maxDist = maxDist;
	}
	
	@Override
	public Vec to(Vec from) {
		Vec to = from.sub(center);
		
		double dist = to.norm();
		if(dist > maxDist) {
			return from;
		}
		double frac = dist/maxDist;
		frac = Math.sqrt(frac);
		return center.add(to.mul((frac*maxDist)/dist));
	}

	@Override
	public Vec from(Vec to) {
		Vec to2 = to.sub(center);
		
		double dist = to2.norm();
		if(dist > maxDist) {
			return to;
		}
		double frac = dist/maxDist;
		frac = frac * frac;
		return center.add(to2.mul((frac*maxDist)/dist));
		
	}

	
}
