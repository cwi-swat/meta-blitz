package bezier.points;


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
		return new Transformation(other.to.mul(to),  back.mul(other.back));
	}
	
	public Transformation rotate(double rads){
		return new Transformation(Matrix.rotate(rads).mul(to), back.mul(Matrix.rotate(-rads)));
	}
	
	public Transformation translate(double x,double y){
		return new Transformation(Matrix.translate(x, y).mul(to), back.mul(Matrix.translate(-x,-y)));
	}
	
	public Transformation translate(Vec v){
		return translate(v.x,v.y);
	}
	
	public Transformation scale(double x, double y){
		return new Transformation(Matrix.scale(x, y).mul(to), back.mul(Matrix.scale(1.0/x,1.0/y)));
	}
	
	public Transformation scale(Vec s){
		return scale(s.x,s.y);
	}
	
	public Transformation scale(double x){
		return scale(x,x);
	}
	
	public Transformation shearX(double x){
		return new Transformation(Matrix.shearX(x).mul(to), back.mul(Matrix.shearX(-x)));
	}
	
	public Transformation shearY(double x){
		return new Transformation(Matrix.shearY(x).mul(to), back.mul(Matrix.shearY(-x)));
	}
}
