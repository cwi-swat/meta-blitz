package bezier.util;

public final class MinMax {
	private double min, max;

	public MinMax(){
		max = Double.MAX_VALUE;
		min = Double.NEGATIVE_INFINITY;
	}
	
	public MinMax(double min, double max){
		this.min = min;
		this.max = max;
	}
	
	public void update(double d){
		min = Math.max(d, min);
		max = Math.min(d, max);
	}
	
	public double getMin() {
		return min;
	}

	public double getMax() {
		return max;
	}
	
	public double getMiddle(){
		return (min + max) / 2.0;
	}
	
	public void update(MinMax other){
		update(other.min);
		update(other.max);
	}
}
