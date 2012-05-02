package nogbeter.points.twod;

import bezier.paths.Constants;

public final class Vec {
	public final double x, y;
	public final boolean point;
	
	public Vec(double x, double y, boolean point){
		this.x = x;
		this.y = y;
		this.point = point;
	}
	
	public Vec(double x, double y){
		this(x,y,true);
	}
	
	public double normSquared(){
		return x*x + y*y;
	}
	
	public double norm(){
		return Math.sqrt(normSquared());
	}
	
	public Vec normalize(){
		return div(norm());
	}
	
	public Vec add(Vec r){
		return new Vec(x + r.x , y + r.y);
	}
	
	public Vec sub(Vec r){
		return new Vec(x - r.x , y - r.y);
	}
	
	public Vec mirror(Vec r){
		return new Vec(2*x - r.x, 2*y - r.y);
	}

	public Vec mul(double r){
		return new Vec(x*r, y *r);
	}
	
	public Vec div(double r){
		return new Vec(x/r, y/r);
	}
	
	public double dot(Vec r){
		return x * r.x + y * r.y;
	}
	
	public Vec perpendicularCCW(){
		return new Vec(-y,x);
	}
	
	public Vec perpendicularCW(){
		return new Vec(y,-x);
	}
	
	public Vec addMul(double s, Vec add){
		return new Vec(x + s*add.x , y + s*add.y);
	}
	
	public double distanceSquared(Vec other){
		double xDist = x - other.x;
		double yDist = y - other.y;
		return xDist*xDist + yDist*yDist;
	}
	
	public Vec interpolate(double t,Vec other){
		double rt = 1.0 -t;
		return new Vec(x * rt + other.x * t, y * rt + other.y * t);
	}
	
	public Vec negate(){
		return new Vec(-x,-y);
	}
	
	public boolean isEqError(Vec other){
		return distanceSquared(other) <= Constants.MAX_ERROR_2_POW2;
	}
	
	public String toString(){
		return String.format("<%3.3f,%3.3f>",x,y);
	}

	public double distance(Vec other) {
		return Math.sqrt(distanceSquared(other));
	}

	public int compareXY(Vec vec) {
		if(x == vec.x){
			return Double.compare(y, vec.y);
		} else {
			return Double.compare(x, vec.x);
		}
	}

	public boolean isEq(Vec vec) {
		return x == vec.x && y == vec.y;
	}
}