package bezier.util;
import static bezier.util.Util.getIntervalLocation;
import static bezier.util.Util.getIntervalLocationExEnd;

import java.awt.Shape;
import java.awt.geom.Rectangle2D;

import bezier.paths.Constants;
import bezier.paths.simple.Line;
import bezier.points.Vec;
public final class BBox {
	
	public final double x, y, width, height, xr , yd;
	
	public BBox(double x, double y, double xr, double yd){
		this.x = x; this.y = y;
		this.xr = xr;
		this.yd = yd;
		this.width = xr - x;
		this.height = yd - y;
	}
	
	public BBox(Vec a, Vec b){
		x = Math.min(a.x, b.x);
		xr = Math.max(a.x, b.x);
		y = Math.min(a.y, b.y);
		yd = Math.max(a.y, b.y);
		this.width = xr - x;
		this.height = yd - y;
	}
	
	public BBox(Iterable<HasBBox> curves) {
		double x = Double.MAX_VALUE, xr = Double.MIN_VALUE,
				y = Double.MAX_VALUE, yd = Double.MIN_VALUE;
		for(HasBBox v : curves){
			BBox b = v.getBBox();
			x = Math.min(b.x, x);
			xr = Math.max(b.xr, xr);
			y = Math.min(b.y, y);
			yd = Math.max(b.yd, yd);
		}
		this.x = x; this.y = y;
		this.xr = xr;
		this.yd = yd;
		this.width = xr - x;
		this.height = yd - y;
	}

	public boolean overlaps(BBox other){
		return overlapsX(other) && overlapsY(other);
	}
	
	public boolean overlaps(Line other){
		if(other.isHorizontal()){
			return Util.isBetween(other.start.y, y, yd) 
					&& Util.intervalsOverlap(other.start.x, other.end.x,x,xr);
		} 
		if(other.isVertical()){
			return Util.isBetween(other.start.x, x, xr) 
					&& Util.intervalsOverlap(other.start.y, other.end.y,y,yd);
		}
		double prox = Util.clamp(other.getTAtX(x));
		double proxr = Util.clamp(other.getTAtX(xr));
		double proy = Util.clamp(other.getTAtY(y));
		double proyd = Util.clamp(other.getTAtY(yd));
		return Util.intervalsOverlap(prox, proxr, proy, proyd);
				
	}
	
	public boolean overlapsX(BBox other){
		return !(other.xr < x || other.x > xr) ;
	}
	
	public boolean overlapsY(BBox other){
		return  !(other.yd < y || other.y > yd);
	}
	
	private static double minDistSquared(double a, double b, double c, double d){
		double da = a - c;
		double db = a - d;
		double dc = b - c;
		double dd = b - d;
		da*=da; db*=db; dc*=dc; dd*=dd;
		return Util.min4(da, db, dc, dd);
	}
	
	private static double maxDistSquared(double a, double b, double c, double d){
		double da = a - c;
		double db = a - d;
		double dc = b - c;
		double dd = b - d;
		da*=da; db*=db; dc*=dc; dd*=dd;
		return Util.max4(da, db, dc, dd);
	}
	
	public MinMax minMaxDistSquared(BBox other){
		double xMinDist;
		double xMaxDist;
		if(overlapsX(other)){
			xMinDist = 0;
		} else {
			xMinDist = minDistSquared(x, xr, other.x, other.xr);
		}
		xMaxDist = maxDistSquared(x, xr, other.x, other.xr);
		double yMinDist;
		double yMaxDist;
		if(overlapsY(other)){
			yMinDist = 0;
		} else {
			yMinDist = minDistSquared(y, yd, other.y, other.yd);
		}
		yMaxDist = maxDistSquared(y, yd, other.y, other.yd);
		return new MinMax(xMinDist + yMinDist, xMaxDist + yMaxDist);
	}

	public Vec getFarthestPoint(Vec p){
		return new Vec(Util.getFarthestPoint(p.x, x, xr), Util.getFarthestPoint(p.y,y,yd));
	}
	
	public Vec getNearestPoint(Vec p){
		return new Vec(Util.getClosestPoint(p.x,x,xr), Util.getClosestPoint(p.y,y,yd));
	}
	
	public MinMax minMaxDist(Vec point){
		return new MinMax(getNearestPoint(point).distanceSquared(point), 
							getFarthestPoint(point).distanceSquared(point));
	}
	
	public double area(){
		return width * height;
	}
	
	public boolean sidesSmallerThan(double d){
		return width <= d && height <= d;
	}
	
	public double diagonalLengthSquared(){
		return width*width + height*height;
	}
	
	public BBox overlap(BBox other){
		return new BBox(Math.max(x,other.x),Math.max(y, other.y), 
				        Math.min(xr, other.xr), Math.min(yd, other.yd));
	}
	
	public BBox union(BBox other){
		return new BBox(Math.min(x,other.x),Math.min(y, other.y), 
		        Math.max(xr, other.xr), Math.max(yd, other.yd));
	}
	
	public Vec middle(){
		return new Vec((x + xr)/2.0,(y+yd)/2.0);
	}
	
	public IntervalLocation xIntervalLocation(double x){
		return getIntervalLocation(x, this.x, xr);
	}
	
	public IntervalLocation yIntervalLocation(double y){
		return getIntervalLocation(y, this.y, yd);
	}
	
	public IntervalLocation xIntervalLocationExEnd(double x){
		return getIntervalLocationExEnd(x, this.x, xr);
	}
	
	public Shape toAWT(){
		return new Rectangle2D.Double(x,y,width,height);
	}
	
	public String toString(){
		return String.format("BBox %f %f - %f %f (%f,%f)", x, y , xr, yd, width, height);
	}
	
	public Vec getSize(){
		return new Vec(width,height);
	}
	
	public Vec getLeftUp(){
		return new Vec(x,y);
	}
	
	public Vec getLeftDown(){
		return new Vec(x,yd);
	}
	
	public Vec getRightUp(){
		return new Vec(xr,y);
	}
	
	public Vec getMiddle(){
		return new Vec((x+xr)/2.0,(y+yd)/2.0);
	}
	
	public Vec getRightDown(){
		return new Vec(xr,yd);
	}
	
	public boolean isInside(Vec p){
		return Util.isBetween(p.x, x,xr) && Util.isBetween(p.y, y , yd);
	}
	
	public static BBox fromPoints(Vec ...vecs){
		double x = Double.MAX_VALUE, xr = Double.MIN_VALUE,
				y = Double.MAX_VALUE, yd = Double.MIN_VALUE;
		for(Vec v : vecs){
			x = Math.min(v.x, x);
			xr = Math.max(v.x, xr);
			y = Math.min(v.y, y);
			yd = Math.max(v.y, yd);
		}
		return new BBox(x,y,xr,yd);
	}
}
