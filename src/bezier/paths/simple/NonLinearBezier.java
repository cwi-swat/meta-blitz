package bezier.paths.simple;

import static bezier.util.Util.clamp;

import java.util.List;

import bezier.paths.IConnectedPath;
import bezier.paths.Path;
import bezier.points.Vec;
import bezier.util.IntervalLocation;
import bezier.util.STuple;
public abstract class NonLinearBezier extends SimplePath{

	public NonLinearBezier(double tStart, double tEnd) {
		super(tStart, tEnd);
	}
	
	
	
	public abstract STuple<NonLinearBezier> split(double t);
	STuple<NonLinearBezier> split() { return split(0.5); }


	abstract List<Double> getXYRoots();
	
//	public List<Curve> makeMonotomous(){
//		List<Curve> result = new ArrayList<Curve>();
//		List<Double> roots = getXYRoots();
//		Collections.sort(roots);
//		Path right = this;
//		double offset = 0.0;
////		if(roots.isEmpty()){
////			result.add(this);
////		} else {
////			System.out.printf("Splitting at %f\n",roots.get(0));
////			Tuple<Curve,Curve> split = right.split(roots.get(0));
////			NonLinearCurve sl = (NonLinearCurve)split.l;
////			NonLinearCurve sr = (NonLinearCurve)split.r;
////			sl.makeMonotomous(result);
////			
////		}
//		for(double root : roots){
//			double relRoot = (root - offset) / (1.0 - offset);
//			STuple<Curve> split = right.split(relRoot);
////			System.out.printf("Splitting at %f\n", root);
//			result.add(split.l);
//			right = split.r;
//			offset = root;
//		}
//		result.add(right);
//		return result;
//	}
//	
	public boolean isLine(){
		return false;
	}

	public Line getLine(){
		throw new Error("Not a line!");
	}

	@Override
	public boolean isBelow(Vec p){
		getBBox();
		if(bbox.xIntervalLocation(p.x) != IntervalLocation.INSIDE){
			return false;
		}
		switch(bbox.yIntervalLocation(p.y)){
		case LEFT_OF: return false;
		case INSIDE:
			
					 Double t = findX( p.x);
					 if(t == null){
						 return false;
					 }
					 return getAt(clamp(t)).y < p.y;
		case RIGHT_OF: return true;
		}
		throw new Error("Unkown interval location");
	}

	abstract Double findX(double x) ;
	

	public double findTForX(double x) {
		Double d =  findX(x);
		if(d == null){
			System.out.printf("Cannot find %f %s\n",x,this);
		}
		return d;
	}

	@Override
	public
	STuple<Path> splitSimpler() {
		STuple<NonLinearBezier> sp = split();
		return new STuple<Path>(
				sp.l.getSimplerApproximation(),
				sp.r.getSimplerApproximation());
	}
	

	abstract Path getSimplerApproximation();

}
