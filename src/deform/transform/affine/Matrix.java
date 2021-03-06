package deform.transform.affine;

import deform.Vec;

public class Matrix {
	public final double x1, x2, x3;
	public final double y1, y2, y3;

	public static Matrix identity = new Matrix(1, 0, 0, 0, 1, 0);

	public Matrix(double x1, double x2, double x3, double y1, double y2,
			double y3) {
		this.x1 = x1;
		this.x2 = x2;
		this.x3 = x3;
		this.y1 = y1;
		this.y2 = y2;
		this.y3 = y3;
		if(Double.isNaN(x3) || Double.isInfinite(x3)){
			throw new Error("NAN in matrix!");
		}
	}

	public Matrix(double x1, double x2, double x3, double y1, double y2,
			double y3, double z1, double z2, double z3) {
		this(x1, x2, x3, y1, y2, y3);
	}

	public Matrix mul(Matrix r) {
		return new Matrix(r.x1 * x1 + r.y1 * x2, r.x2 * x1 + r.y2 * x2, r.x3
				* x1 + r.y3 * x2 + x3,

		r.x1 * y1 + r.y1 * y2, r.x2 * y1 + r.y2 * y2, r.x3 * y1 + r.y3 * y2
				+ y3);
	}
	
	public boolean isEqExTranslation(Matrix other){
		return x1 == other.x1 && x2 == other.x2 && y1 == other.y1 && y2 == other.y2;
	}
	
	public Vec getTranslation(){
		return new Vec(x3,y3);
	}

	public Vec mul(Vec v) {
		return new Vec(x1 * v.x + x2 * v.y + x3 , 
					y1 * v.x + y2* v.y + y3 );
	}

	public static Matrix identitiy() {
		return identity;
	}

	public static Matrix rotate(double rads) {
		double s = Math.sin(rads);
		double c = Math.cos(rads);
		return new Matrix(c, s, 0, -s, c, 0);
	}

	public static Matrix translate(Vec v) {
		return new Matrix(1, 0, v.x, 0, 1, v.y);
	}

	public static Matrix scale(double x, double y) {
		return new Matrix(x, 0, 0, 0, y, 0);
	}
	public static Matrix scale(double d) {
		return scale(d, d);
	}

	public static Matrix shear(double x,double y) {
		return new Matrix(1, x, 0, y, 1, 0);
	}

	public static Matrix rotateAroundPoint(Vec v, double rads) {
		Matrix rotBack = rotate(-rads);
		return translate(v).mul(rotate(rads)).mul(translate(rotBack.mul(v)));
	}

	public java.awt.geom.AffineTransform toJava2DTransform(){
		return new java.awt.geom.AffineTransform(x1, y1, x2, y2, x3, y3);
	}
	
	public String toString() {
		return String
				.format("[%7.3f %7.3f %7.3f]\n[%7.3f %7.3f %7.3f]\n[%7.3f %7.3f %7.3f]\n",
						x1, x2, x3, y1, y2, y3, 0.0, 0.0, 1.0);
	}

	public boolean isTranslation() {
		return x1 == 1 && x2 == 0 && y1 == 0 && y2 == 0;
	}
}
