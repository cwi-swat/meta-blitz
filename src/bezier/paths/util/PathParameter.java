package bezier.paths.util;



public final class PathParameter {

	public final int index;
	public final double t;
	
	
	public PathParameter(int index, double t) {
		this.index = index;
		this.t = t;
	}


	public PathParameter(double d) {
		this(0,d);
	}
	
}
