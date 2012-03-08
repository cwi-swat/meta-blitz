package bezier.points;

import static bezier.points.Matrix.*;

public final class Transformation {
	
	public static Transformation id = new Transformation();
	
	public final Matrix to;
	public final Matrix back;
	
	public Transformation(Matrix to, Matrix back) {
		this.to = to;
		this.back = back;
	}
	
	public Transformation(){
		this(Matrix.identity,Matrix.identity);
	}
	
	public Vec to(Vec from){
		return to.mul(from);
	}
	
	
	public Vec from(Vec to){
		return back.mul(to);
	}
	
	public Transformation mul(Transformation other){
		return new Transformation(to.mul(other.to), other.back.mul(back));
	}
	
	public Transformation rotate(double rads){
		return new Transformation(to.mul(Matrix.rotate(rads)), Matrix.rotate(-rads).mul(back));
	}
	
	public Transformation translate(double x,double y){
		return new Transformation(to.mul(Matrix.translate(x, y)), Matrix.translate(-x,-y).mul(back));
	}
	
	public Transformation translate(Vec v){
		return translate(v.x,v.y);
	}
	
	public Transformation scale(double x, double y){
		return new Transformation(to.mul(Matrix.scale(x, y)), Matrix.scale(1.0/x,1.0/y).mul(back));
	}
	
	public Transformation scale(Vec s){
		return scale(s.x,s.y);
	}
	
	public Transformation scale(double x){
		return scale(x,x);
	}
	
	public Transformation shearX(double x){
		return new Transformation(to.mul(Matrix.shearX(x)), Matrix.shearX(-x).mul(back));
	}
	
	public Transformation shearY(double x){
		return new Transformation(to.mul(Matrix.shearX(x)), Matrix.shearX(-x).mul(back));
	}
}
