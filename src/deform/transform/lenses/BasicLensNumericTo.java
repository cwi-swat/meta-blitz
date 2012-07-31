package deform.transform.lenses;

import paths.Constants;
import deform.BBox;
import deform.Transform;
import deform.Vec;

public class BasicLensNumericTo extends Transform{


	private static final double MAX_ERROR_SOLVE = Constants.MAX_ERROR_TRANSFORM/5;
	private static final double MAX_ERROR_TABLE = MAX_ERROR_SOLVE * 20;
	
	final double mag;
	final double outerRadius;
	final double innerRadius;
	final Vec center;
	final double inraddivmag;
	final double border;
	final double borderdiv4;
	
	public BasicLensNumericTo(Vec center,double mag, double innerRadius, double outerRadius) {
		this.center = center;
		this.mag = mag;
		this.innerRadius = innerRadius;
		this.outerRadius = outerRadius;
		inraddivmag = innerRadius/mag;
		border = outerRadius - innerRadius;
		borderdiv4 = outerRadius - innerRadius /4;
	}
	
	private double[] makeTable(){
		
	}

	@Override
	public Vec to(Vec from) {
//		return from;
		Vec fromCenter = from.sub(center);
		double d = norm(fromCenter);
		if(d <= innerRadius/mag){
			return center.add(fromCenter.mul(mag));
		} else if(d < outerRadius){
			fromCenter = fromCenter.div(d);
			return center.add(fromCenter.mul(solveNumerically(d)));
		} else {
			return from;
		}
	}

	
	
	private double solveNumerically(double d) {
		double leftBorder = innerRadius;
		double rightBorder = outerRadius;
		double sol = (d - inraddivmag) / (borderdiv4) * (border) + innerRadius;
		double outCome = scale(sol);
		while(Math.abs(outCome-d) > MAX_ERROR_SOLVE){
			if(outCome < d){
				leftBorder = sol;
				sol = (sol + rightBorder)/2.0;
			} else { // outcome < d
				rightBorder = sol;
				sol = (sol + leftBorder)/2.0;
			}
			outCome = scale(sol);
		}
//		System.out.printf("Err %f %f %f\n", sol, outCome, Math.abs(outCome-d));
		return sol;
	}

	@Override
	public Vec from(Vec to) {
		Vec fromCenter = to.sub(center);
		double d = norm(fromCenter);
		if(d <= innerRadius){
			return center.add(fromCenter.div(mag));
		} else if(d < outerRadius){
			double s = scale(d);
			fromCenter = fromCenter.div(d);
			return center.add(fromCenter.mul(s));
		} else {
			return to;
		}
	}

	private double norm(Vec fromCenter) {
//		return fromCenter.norm();
		return Math.max(Math.abs(fromCenter.x) ,Math.abs(fromCenter.y));
	}

	
	private double scale(double d) {
		return d / ((1- scaleMore((d-innerRadius)/(outerRadius-innerRadius))) * (mag - 1) + 1);
	}

	private double scaleMore(double d) {
		return Math.sin(d*Math.PI*0.5);
//		return Math.sqrt(d);
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
