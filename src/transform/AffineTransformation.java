package transform;

import paths.points.twod.Vec;

public final class AffineTransformation implements ITransform {

	public static AffineTransformation id = new AffineTransformation();

	public final Matrix to;
	public final Matrix back;

	public AffineTransformation(Matrix to, Matrix back) {
		this.to = to;
		this.back = back;
	}

	public AffineTransformation() {
		this(Matrix.identity, Matrix.identity);
	}

	public Vec to(Vec from) {
		return to.mul(from);
	}

	public Vec from(Vec to) {
		return back.mul(to);
	}

	public AffineTransformation mul(AffineTransformation other) {
		return new AffineTransformation(other.to.mul(to), back.mul(other.back));
	}

	public AffineTransformation rotate(double rads) {
		return new AffineTransformation(Matrix.rotate(rads).mul(to),
				back.mul(Matrix.rotate(-rads)));
	}

	public AffineTransformation translate(double x, double y) {
		return new AffineTransformation(Matrix.translate(x, y).mul(to),
				back.mul(Matrix.translate(-x, -y)));
	}

	public AffineTransformation translate(Vec v) {
		return translate(v.x, v.y);
	}

	public AffineTransformation scale(double x, double y) {
		return new AffineTransformation(Matrix.scale(x, y).mul(to),
				back.mul(Matrix.scale(1.0 / x, 1.0 / y)));
	}

	public AffineTransformation scale(Vec s) {
		return scale(s.x, s.y);
	}

	public AffineTransformation scale(double x) {
		return scale(x, x);
	}

	public AffineTransformation shearX(double x) {
		return new AffineTransformation(Matrix.shearX(x).mul(to),
				back.mul(Matrix.shearX(-x)));
	}

	public AffineTransformation shearY(double x) {
		return new AffineTransformation(Matrix.shearY(x).mul(to),
				back.mul(Matrix.shearY(-x)));
	}

	public Vec transform(Vec d) {
		return to.mul(d);
	}

	@Override
	public boolean isAffine() {
		return true;
	}

	@Override
	public AffineTransformation getAffine() {
		return this;
	}

}
