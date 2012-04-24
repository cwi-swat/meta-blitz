package nogbeter.util;

import java.awt.Shape;
import java.awt.geom.Rectangle2D;
import java.util.Arrays;
import java.util.Iterator;

import bezier.paths.Constants;
import bezier.paths.simple.Line;
import bezier.points.Vec;
public final class BBox {
	
	public final InclusiveInterval xInterval, yInterval;
	
	public BBox(double x, double y, double xr, double yd){
		this(new InclusiveInterval(x, xr), new InclusiveInterval(y, yd));
	}

	public BBox(InclusiveInterval xInterval, InclusiveInterval yInterval) {
		this.xInterval = xInterval;
		this.yInterval = yInterval;
	}

	
//	
//	public BBox(Iterable<HasBBox> curves) {
//		double x = Double.MAX_VALUE, xr = Double.MIN_VALUE,
//				y = Double.MAX_VALUE, yd = Double.MIN_VALUE;
//		for(HasBBox v : curves){
//			BBox b = v.getBBox();
//			x = Math.min(b.x, x);
//			xr = Math.max(b.xr, xr);
//			y = Math.min(b.y, y);
//			yd = Math.max(b.yd, yd);
//		}
//		this.x = x; this.y = y;
//		this.xr = xr;
//		this.yd = yd;
//		this.width = xr - x;
//		this.height = yd - y;
//	}




	public boolean overlaps(BBox other){
		return xInterval.overlapsWith(other.xInterval)
				&& yInterval.overlapsWith(other.yInterval);
	}

	public Vec getFarthestPoint(Vec p){
		return new Vec(	xInterval.getFarthestPoint(p.x), 
						yInterval.getFarthestPoint(p.y));
	}
	
	public Vec getNearestPoint(Vec p){
		return new Vec(	xInterval.getClosestPoint(p.x), 
						yInterval.getClosestPoint(p.y));
	}
	
	public double width(){
		return xInterval.length();
	}
	
	public double height(){
		return yInterval.length();
	}
	
	public double area(){
		return xInterval.length() * yInterval.length();
	}
	
	public boolean sidesSmallerThan(double d){
		return width() <= d && height() <= d;
	}
	
	public double diagonalLengthSquared(){
		return width()*width() + height()*height();
	}
	
	public BBox intersections(BBox other){
		return new BBox(	xInterval.intersection(other.xInterval),
							yInterval.intersection(other.yInterval));
	}
	
	public BBox union(BBox other){
		return new BBox(	xInterval.union(other.xInterval),
							yInterval.union(other.yInterval));
	}
	
	public double avgDistSquared(Vec p){
		return getMiddle().distanceSquared(p);
	}
	
	public Vec getMiddle(){
		return new Vec(xInterval.middle(),yInterval.middle());
	}
	
	public Vec getLeftUp(){
		return new Vec(xInterval.low,yInterval.low);
	}
	
	public Vec getLeftDown(){
		return new Vec(xInterval.low,yInterval.high);
	}
	
	public Vec getRightUp(){
		return new Vec(xInterval.high,yInterval.low);
	}
	
	public Vec getRightDown(){
		return new Vec(xInterval.high,yInterval.high);
	}
	
	public boolean isInside(Vec p){
		return  xInterval.isInside(p.x) && yInterval.isInside(p.y);
	}
	
	public static BBox fromPoints(Iterator<Vec> vecs){
		return new BBox(	InclusiveInterval.fromPoints(new VecXIterator(vecs)),
							InclusiveInterval.fromPoints(new VecYIterator(vecs)));
	}
	
	public static BBox fromPoints(Vec ... vecs){
		return fromPoints(Arrays.asList(vecs).iterator());
	}
}
