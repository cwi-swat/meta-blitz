package bezier.util;

import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

public class DummySWTSHape implements Shape{

	private final PathIterator it;
	
	public DummySWTSHape(PathIterator it) {
		this.it = it;
	}
	
	@Override
	public Rectangle getBounds() {
		throw new Error("NYI");
	}

	@Override
	public Rectangle2D getBounds2D() {
		throw new Error("NYI");
	}

	@Override
	public boolean contains(double x, double y) {
		throw new Error("NYI");
	}

	@Override
	public boolean contains(Point2D p) {
		throw new Error("NYI");
	}

	@Override
	public boolean intersects(double x, double y, double w, double h) {
		throw new Error("NYI");
	}

	@Override
	public boolean intersects(Rectangle2D r) {
		throw new Error("NYI");
	}

	@Override
	public boolean contains(double x, double y, double w, double h) {
		throw new Error("NYI");
	}

	@Override
	public boolean contains(Rectangle2D r) {
		throw new Error("NYI");
	}

	@Override
	public PathIterator getPathIterator(AffineTransform at) {
		return it;
	}

	@Override
	public PathIterator getPathIterator(AffineTransform at, double flatness) {
		throw new Error("NYI");
	}

}
