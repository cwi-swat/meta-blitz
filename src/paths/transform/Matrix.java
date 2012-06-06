package paths.transform;

import paths.points.twod.Vec;

public class Matrix {
	public final double x1,x2,x3;
	public final double y1,y2,y3;
	
	public static Matrix identity = new Matrix(1,0,0,0,1,0);
	
	public Matrix(double x1, double x2, double x3, double y1, double y2,
			double y3) {
		this.x1 = x1;
		this.x2 = x2;
		this.x3 = x3;
		this.y1 = y1;
		this.y2 = y2;
		this.y3 = y3;
	}
	
	public Matrix(double x1, double x2, double x3, double y1, double y2,
			double y3, double z1, double z2, double z3) {
		this(x1,x2,x3,y1,y2,y3);
	}
	
	public Matrix mul(Matrix r){
		return new 
			Matrix(	r.x1 * x1 + r.y1 * x2, 
					r.x2 * x1 + r.y2 * x2, 
					r.x3 * x1 + r.y3 * x2 + x3  ,
					
					r.x1 * y1 + r.y1 * y2,
					r.x2 * y1 + r.y2 * y2,
					r.x3 * y1 + r.y3 * y2 + y3 );
	}
	
	public Vec mul(Vec v){
		return new Vec(
				x1 * v.x + x2 * v.y + (v.point ? x3 : 0),
				y1 * v.x + y2 * v.y + (v.point ? y3 : 0), v.point);
	}
	
	public static Matrix identitiy(){
		return identity;
	}
	
	public static Matrix rotate(double rads){
		double s = Math.sin(rads);
		double c = Math.cos(rads);
		return new Matrix(c,s,0,
				          -s,c,0);
	}
	
	public static Matrix translate(double x, double y){
		return new Matrix(1,0,x,0,1,y);
	}
	
	public static Matrix translate(Vec v){
		return translate(v.x,v.y);
	}
	
	public static Matrix scale(double x, double y){
		return new Matrix(x,0,0,0,y,0);
	}
	
	public static Matrix scale(Vec v){
		return scale(v.x,v.y);
	}
	
	public static Matrix scale(double d) {
		return scale(d,d);
	}
	
	public static Matrix shearX(double x){
		return new Matrix(1,x,0,0,1,0);
	}
	
	public static Matrix shearY(double x){
		return new Matrix(1,0,0,x,1,0);
	}
	
	public static Matrix rotateAroundPoint(Vec v, double rads){
		Matrix rotBack = rotate(-rads);
		return translate(v).mul(rotate(rads)).mul(translate(rotBack.mul(v)));
	}
	
	public String toString(){
		return String.format("[%7.3f %7.3f %7.3f]\n[%7.3f %7.3f %7.3f]\n[%7.3f %7.3f %7.3f]\n",x1,x2,x3,y1,y2,y3,0.0,0.0,1.0);
	}
}
