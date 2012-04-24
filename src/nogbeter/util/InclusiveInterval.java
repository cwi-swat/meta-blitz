package nogbeter.util;

import java.util.Iterator;

import bezier.util.STuple;



public class InclusiveInterval {
	
	public static final InclusiveInterval interval01 = new InclusiveInterval(0, 1);
	
	public final double low, high;

	public InclusiveInterval(double a, double b) {
		if(a > b){
			double tmp = a;
			a = b;
			b = tmp;
		}
		this.low = a;
		this.high = b;
	}
	
	public boolean isInside(double x){
		return x >= low && x <= high;
	}
	
	public IntervalLocation getIntervalLocation(double x){
		if(x < low) return IntervalLocation.LEFT_OF;
		else if(x > high) return IntervalLocation.RIGHT_OF;
		else return IntervalLocation.INSIDE;
	}
	
	public double getClosestPoint(double x){
		if(x < low){
			return low;
		} else if(x > high){
			return high;
		} else {
			return x;
		}
	}
	
	public double getFarthestPoint(double x){
		if(x < low){
			return high;
		} else if(x > high){
			return low;
		} else if( x - low > high - x){
			return low;
		} else {
			return high;
		}
	}
	
	public boolean overlapsWith(InclusiveInterval other){
		return !(other.high < low || other.low > high);
	}
	
	public IntervalLocation intervalIntervalLocation(InclusiveInterval other) {
		if(overlapsWith(other)){
			return IntervalLocation.INSIDE;
		} else if(other.high < low){
			return IntervalLocation.LEFT_OF;
		} else {
			return IntervalLocation.RIGHT_OF;
		}
	}
	
	public InclusiveInterval intersection(InclusiveInterval other){
		return new InclusiveInterval(	Math.max(low, other.low),
										Math.min(high,other.high));
	}
	
	public InclusiveInterval union(InclusiveInterval other){
		return new InclusiveInterval(	Math.min(low, other.low),
										Math.max(high,other.high));
	}
	
	public double minDistance(InclusiveInterval other){
		switch(intervalIntervalLocation(other)){
		case INSIDE: return 0;
		case LEFT_OF: return low - other.high;
		case RIGHT_OF: return other.low - high;
		}
		throw new Error("No such interval location");
	}
	
	public double middle(){
		return (low + high) / 2.0;
	}
	
	public double length(){
		return high - low;
	}
	
	public double getAtFactor(double factor){
		return (high - low) * factor + low;
	}
	
	public STuple<InclusiveInterval> split(double factor){
		double mid = getAtFactor(factor);
		return new STuple<InclusiveInterval>(
				new InclusiveInterval(low, mid),
				new InclusiveInterval(mid, high));
	}
	
	public STuple<InclusiveInterval> split(){
		return split(0.5);
	}
	
	public static InclusiveInterval fromPoints(Iterator<Double> points){
		double min = Double.POSITIVE_INFINITY;
		double max = Double.NEGATIVE_INFINITY;
		while(points.hasNext()){
			double p = points.next();
			min = Math.min(min, p);
			max = Math.max(max, p);
		}
		return new InclusiveInterval(min,max);
	}
	
	public double getFactorForPoint(double p){
		return (p - low)/(high - low);
	}

}
