package nogbeter.points.oned;

import java.util.Iterator;

import bezier.util.STuple;



public class Interval {
	
	public static final Interval interval01 = new Interval(0, 1);
	public static final Interval emptyInterval = 
			new Interval(	Double.POSITIVE_INFINITY,
							Double.NEGATIVE_INFINITY);
	
	public final double low, high;

	public Interval(double a, double b) {
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
	
	public boolean overlapsWith(Interval other){
		return !(other.high < low || other.low > high);
	}
	
	public IntervalLocation intervalIntervalLocation(Interval other) {
		if(overlapsWith(other)){
			return IntervalLocation.INSIDE;
		} else if(other.high < low){
			return IntervalLocation.LEFT_OF;
		} else {
			return IntervalLocation.RIGHT_OF;
		}
	}
	
	
	public Interval intersection(Interval other){
		double nLow = Math.max(low, other.low);
		double nHigh = Math.min(high, other.high);
		if(nHigh < nLow) {
			return emptyInterval;
		}
		return new Interval(nLow,nHigh);
	}
	
	public Interval union(Interval other){
		return new Interval(	Math.min(low, other.low),
										Math.max(high,other.high));
	}
	
	
	public double minDistance(double x){
		return x - getClosestPoint(x);
	}
	
	public STuple<Double> closestPoints(Interval other){
		switch(intervalIntervalLocation(other)){
		case INSIDE: double x= intersection(other).low;
					 return new STuple<Double>(x, x);
		case LEFT_OF: return new STuple<Double>(low, other.high);
		case RIGHT_OF: return new STuple<Double>(high, other.low);
		}
		throw new Error("No such interval location");
	}
	
	public double minDistance(Interval other){
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
	
	public STuple<Interval> split(double factor){
		double mid = getAtFactor(factor);
		return new STuple<Interval>(
				new Interval(low, mid),
				new Interval(mid, high));
	}
	
	public STuple<Interval> split(){
		return split(0.5);
	}
	
	public static Interval fromPoints(Iterator<Double> points){
		double min = Double.POSITIVE_INFINITY;
		double max = Double.NEGATIVE_INFINITY;
		while(points.hasNext()){
			double p = points.next();
			min = Math.min(min, p);
			max = Math.max(max, p);
		}
		return new Interval(min,max);
	}
	
	public double getFactorForPoint(double p){
		return (p - low)/(high - low);
	}

	public static boolean overlap(Interval a, Interval b) {
		return a != emptyInterval && b != emptyInterval && 
				a.overlapsWith(b);
	}

}
