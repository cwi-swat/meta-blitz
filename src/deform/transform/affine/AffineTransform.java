package deform.transform.affine;

import deform.BBox;
import deform.Transform;
import deform.Vec;
import deform.transform.CompositeTransform;

public class AffineTransform extends Transform{
	public final Matrix to;
	public final Matrix from;
	
	AffineTransform(Matrix to, Matrix from) {
		this.to = to;
		this.from = from;
	}


	@Override
	public Vec to(Vec from) {
		return to.mul(from);
	}

	@Override
	public Vec from(Vec to) {
		return from.mul(to);
	}
	
	public BBox transformBBox(BBox b){
//		return BBox.everything;
		BBox res = BBox.from4Points(to(b.getLeftDown()),
				to(b.getLeftUp()),
				to(b.getRightDown()),
				to(b.getRightUp()));
		return BBox.everything;
	}
	

	public Transform compose(Transform rhs){
		if(rhs instanceof IdentityTransform){
			return this;
		}
		if(rhs instanceof AffineTransform){
			AffineTransform ar = (AffineTransform)rhs; 
			return new AffineTransform(to.mul(ar.to), ar.from.mul(from));
		} else {
			return new CompositeTransform(this,rhs);
		}
	}
	

	public static AffineTransform rotate(double rads) {
		return new AffineTransform(Matrix.rotate(rads),
				Matrix.rotate(-rads));
	}

	public static AffineTransform translate(Vec v) {
		return new AffineTransform(Matrix.translate(v),
				Matrix.translate(v.negate()));
	}

	public static AffineTransform scale(double x, double y) {
		return new AffineTransform(Matrix.scale(x, y),
				Matrix.scale(1.0 / x, 1.0 / y));
	}

	public AffineTransform scale(Vec s) {
		return scale(s.x, s.y);
	}
	
	public java.awt.geom.AffineTransform toJava2DTransform(){
		return to.toJava2DTransform();
	}


	public static AffineTransform shear(double x,double y) {
		return new AffineTransform(Matrix.shear(x,y),
				Matrix.shear(-x, -y));
	}
	
	public String toString(){
		return to.toString();
	}


	public boolean isTranslation() {
		return to.isTranslation();
	}
	
}
