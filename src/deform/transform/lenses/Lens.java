package deform.transform.lenses;

import deform.BBox;
import deform.Transform;
import deform.Vec;
import deform.transform.lenses.Norms.Norm;

public abstract class Lens extends Transform{

	final BBox inside;
	final BBox insideZoom;
	final double mag;
	final double outerRadius;
	final double innerRadius;
	final double border;
	final Vec center;
	final Norm norm;
	
	public Lens(Norm norm, Vec center,double mag, double innerRadius, double outerRadius) {
		this.center = center;
		this.mag = mag;
		this.innerRadius = innerRadius;
		this.outerRadius = outerRadius;
		Vec move = new Vec(outerRadius,outerRadius);
		inside = BBox.from2Points(center.sub(move), center.add(move));
		move = new Vec(innerRadius/mag, innerRadius/mag);
		insideZoom = BBox.from2Points(center.sub(move), center.add(move));
		this.norm = norm;
		this.border = outerRadius - innerRadius;
	}

	@Override
	public Vec to(Vec from) {
		Vec fromCenter = from.sub(center);
		double d = norm.norm(fromCenter);
		if(d <= innerRadius/mag){
			return center.add(fromCenter.mul(mag));
		} else if(d < outerRadius){
			double p = toNorm(d);
			return fromCenter.mul(p/d).add(center);
		} else {
			return from;
		}
	}

	

	@Override
	public Vec from(Vec to) {
		Vec fromCenter = to.sub(center);
		double d = norm.norm(fromCenter);
		if(d <= innerRadius){
			return center.add(fromCenter.div(mag));
		} else if(d < outerRadius){
			return center.add(fromCenter.mul(fromNorm(d)/d));
		} else {
			return to;
		}
	}
	
	abstract double toNorm(double d);
	
	double fromNorm(double d){
		return d / ((1- profile((d-innerRadius)/border)) * (mag - 1) + 1);
	}

	abstract double profile(double d);
	
	public boolean isAffine(BBox b){
//		return false;
		return !b.overlaps(inside) || insideZoom.encloses(b);
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
