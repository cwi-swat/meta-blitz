package deform.transform.lenses;

import deform.BBox;
import deform.Transform;
import deform.Vec;

public class BasicLens extends Transform{

	final double mag;
	final double outerRadius;
	final double innerRadius;
	final Vec center;
	
	public BasicLens(Vec center,double mag, double innerRadius, double outerRadius) {
		this.center = center;
		this.mag = mag;
		this.innerRadius = innerRadius;
		this.outerRadius = outerRadius;
	}

	@Override
	public Vec to(Vec from) {
//		return from;
		Vec fromCenter = from.sub(center);
		double d = norm(fromCenter);
		if(d <= innerRadius/mag){
			return center.add(fromCenter.mul(mag));
		} else if(d < outerRadius){
			double p = ((d*mag* outerRadius) - d * innerRadius)/ (outerRadius + d*mag - innerRadius - d);
//			double s = (d + d *mag + innerRadius*d*mag/(outerRadius-innerRadius))*(d*mag/(outerRadius-innerRadius));
//			System.out.println(s);
			return toNorm(fromCenter,d,p).add(center);
//////			return fromCenter.mul(outerRadius).interpolate(s, fromCenter.mul(innerRadius/4)).add(center);
		} else {
			return from;
		}
	}

	@Override
	public Vec from(Vec to) {
		Vec fromCenter = to.sub(center);
		double d = norm(fromCenter);
		if(d <= innerRadius){
			return center.add(fromCenter.div(mag));
		} else if(d < outerRadius){
			double s = d / ((1- scale((d-innerRadius)/(outerRadius-innerRadius))) * (mag - 1) + 1);
			return center.add(toNorm(fromCenter,d,s));
		} else {
			return to;
		}
	}

	private double norm(Vec fromCenter) {
		return fromCenter.norm();
//		return Math.max(Math.abs(fromCenter.x) ,Math.abs(fromCenter.y));
	}

	private Vec toNorm(Vec fromCenter, double oldNorm, double norm){
		
		return fromCenter.mul(norm/oldNorm);
	}
	
	private double scale(double d) {
		return d;
	}
	
	public BBox transformBBox(BBox b){
		Vec add = new Vec(outerRadius,outerRadius);
		BBox me = BBox.from2Points(center.sub(add), center.add(add));
		if(b.overlaps(me)){
			return b.union(me);
		} else {
			return b;
		}
	}
	

}
