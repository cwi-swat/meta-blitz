package bezier.segment.curve;

import static bezier.util.Util.clamp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import bezier.points.Vec;
import bezier.segment.LengthMap;
import bezier.util.BBox;
import bezier.util.IntervalLocation;
import bezier.util.STuple;

public abstract class NonLinearCurve implements Curve {

	private BBox bbox; 
	private CurveApproxTree approx;
	
	abstract BBox makeBBox();
	
	public CurveApproxTree getFullApproxLengthTree(){
		return new CurveApproxTree(this);
	}
	
	public CurveApproxTree getApproxTree(){
		if(approx == null){
			approx = new CurveApproxTree(this);
		} 
		return approx;
	}
	
	public BBox getBBox(){
		if(bbox == null){
			bbox = makeBBox();
		} 
		return bbox;
	}
	public abstract STuple<Curve> split(double t);
	public STuple<Curve> split(){
		return split(0.5);
	}
	abstract List<Double> getXYRoots();
	
	public List<Curve> makeMonotomous(){
		List<Curve> result = new ArrayList<Curve>();
		List<Double> roots = getXYRoots();
		Collections.sort(roots);
		Curve right = this;
		double offset = 0.0;
//		if(roots.isEmpty()){
//			result.add(this);
//		} else {
//			System.out.printf("Splitting at %f\n",roots.get(0));
//			Tuple<Curve,Curve> split = right.split(roots.get(0));
//			NonLinearCurve sl = (NonLinearCurve)split.l;
//			NonLinearCurve sr = (NonLinearCurve)split.r;
//			sl.makeMonotomous(result);
//			
//		}
		for(double root : roots){
			double relRoot = (root - offset) / (1.0 - offset);
			STuple<Curve> split = right.split(relRoot);
//			System.out.printf("Splitting at %f\n", root);
			result.add(split.l);
			right = split.r;
			offset = root;
		}
		result.add(right);
		return result;
	}
	
	abstract Curve getSimplerApproximation();

	public int nrBelow(Vec p){
		getBBox();
		if(bbox.xIntervalLocation(p.x) != IntervalLocation.INSIDE){
			return 0;
		}
		switch(bbox.yIntervalLocation(p.y)){
		case LEFT_OF: return 0;
		case INSIDE:
			
					 Double t = findX( p.x);
					 if(t == null){
						 return 0;
					 }
					 return getAt(clamp(t)).y < p.y ? 1 :0;
		case RIGHT_OF: return 1;
		}
		throw new Error("Unkown interval location");
	}

	abstract Double findX(double x) ;

	@Override
	public Line getLine() {
		throw new Error("NOT A LINE!");
	}

	@Override
	public boolean isLine(){
		return false;
	}
	
	
	@Override
	public boolean overlapsWith(BBox r) {
		return getBBox().overlaps(r);
	}


	@Override
	public boolean fastIntersectionCheck(Curve other) {
		return other.overlapsWith(getBBox());
	}
	
	@Override
	public STuple<Curve> splitSimpler(){
		STuple<Curve> s = split();
		NonLinearCurve l = (NonLinearCurve)s.l;
		NonLinearCurve r = (NonLinearCurve)s.r;
		return new STuple<Curve>(l.getSimplerApproximation(),r.getSimplerApproximation());
	}

	@Override
	public void fillLengthMap(LengthMap map, double samplesDirect) {
		int nrSamples = (int)Math.ceil(getStartPoint().distance(getEndPoint()) * (double)samplesDirect);
		double tIncrease = 1.0 / nrSamples;
		double prev = getTangentAt(0).norm();
		for(double t = 0 ; t < 1.0; t+=tIncrease){
			double cur = getTangentAt(t).norm();
			double trapezoidArea ;
			if(cur > prev){
				trapezoidArea = (prev + ((cur - prev)/2)) * tIncrease; 
			} else {
				trapezoidArea = (cur + ((prev - cur)/2)) * tIncrease; 
			}
			map.add(tIncrease, trapezoidArea);
			prev = cur;
		}
	}
	
}
