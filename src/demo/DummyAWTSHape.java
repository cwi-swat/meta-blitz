package demo;

import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import deform.BBox;

import paths.paths.iterators.AWTPathIterator;
import paths.paths.paths.Path;


public class DummyAWTSHape implements Shape{

	private final PathIterator it;
	private final Path p ;
	private final int x,y;
	public DummyAWTSHape(Path p, int x, int y ) {
		this.it = new AWTPathIterator(p, x, y);
		this.p = p;
		this.x = x;
		this.y = y;
	}
	
	public DummyAWTSHape(Path p) {
		this(p,0,0);
	}

	@Override
	public Rectangle getBounds() {
		throw new Error("NYI");
	}

	@Override
	public Rectangle2D getBounds2D() {
		BBox b = p.getBBox();
		return new Rectangle2D.Double(b.getXInt() -x, b.getYInt() -y, b.getWidthInt(), b.getHeightInt());
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
