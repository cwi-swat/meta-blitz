package bezier.paths.simple;

import static bezier.util.Util.clamp;

import java.util.Collections;
import java.util.List;

import bezier.paths.ConnectedPath;
import bezier.paths.Path;
import bezier.points.Vec;
import bezier.util.IntervalLocation;
import bezier.util.STuple;
public abstract class NonLinearBezier extends SimplePath{


	List<Double> xyRoots;
	
	public NonLinearBezier(double tStart, double tEnd){
		this(tStart,tEnd,null);

	}
	
	public NonLinearBezier(double tStart, double tEnd, List<Double> xyRoots) {
		super(tStart, tEnd);
		this.xyRoots = xyRoots;
	}
	
	public abstract STuple<NonLinearBezier> split(double t);
	STuple<NonLinearBezier> split() { return split(0.5); }

	abstract List<Double> getXYRoots();
	
	public void setXYRoots(){
		if(xyRoots == null){
			xyRoots = getXYRoots();
			Collections.sort(xyRoots);
		}
	}
	
	@SuppressWarnings("unchecked")
	public STuple<NonLinearBezier> makeMonotomous(){
		STuple<NonLinearBezier> result = split(xyRoots.get(0));
		result.l.xyRoots = Collections.EMPTY_LIST;
		result.r.xyRoots = xyRoots.subList(1, xyRoots.size());
		return result;
	} 
	
	public boolean isLine(){
		return false;
	}

	public Line getLine(){
		throw new Error("Not a line!");
	}

	@Override
	public int nrBelow(Vec p){
		if(!isMonotomous()){
			expand();
			return getLeftSimpler().getSimple().nrBelow(p)
					+ getRightSimpler().getSimple().nrBelow(p);
		}
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
					 return getAt(clamp(t)).y < p.y ? 1 : 0;
		case RIGHT_OF: return 1;
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
	
	public boolean isMonotomous(){
		setXYRoots();
		return xyRoots.size() == 0;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public STuple<Path> splitSimpler() {
		setXYRoots();
		if(!isMonotomous()){
			return (STuple)makeMonotomous();
		} else {
			STuple<NonLinearBezier> sp = split();
			return new STuple<Path>(
					sp.l.getSimplerApproximation(),
					sp.r.getSimplerApproximation());
		}
	}
	

	abstract Path getSimplerApproximation();

	@Override
	public ConnectedPath getSubPath(double start, double end) {
		if(end < start){
			return getSubPath(end,start).reverse();
		}
		if(start == 0){
			if(end == 1){
				return this;
			} 
			return split(end).l;
		}
		if(end == 1){
			return split(start).r;
		}
		NonLinearBezier subr = split(start).r;
		double leftOver = 1.0 - start;
		double newEnd = (end - start)/leftOver; 
		return subr.split(newEnd).l;
	}
}
