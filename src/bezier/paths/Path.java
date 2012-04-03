package bezier.paths;

import java.awt.geom.PathIterator;
import java.util.Iterator;
import java.util.List;

import bezier.paths.awt.AWTPathIterator;
import bezier.paths.awt.IAWTPath;
import bezier.paths.leaf.Line;
import bezier.paths.util.BestProjection;
import bezier.paths.util.ITransform;
import bezier.paths.util.LineIterator;
import bezier.paths.util.PathParameter;
import bezier.paths.util.TPair;
import bezier.points.Vec;
import bezier.util.BBox;
import bezier.util.HasBBox;
import bezier.util.MinMax;
import bezier.util.STuple;

public abstract class Path implements HasBBox, IAWTPath{
	
	protected BBox bbox; 
	private Path leftSimplified, rightSimplified;
	
	public abstract BBox makeBBox();
	
	public BBox getBBox(){
		if(bbox == null){
			bbox = makeBBox();
		} 
		return bbox;
	}
	public abstract boolean isLine();
	public abstract Line getLine();
	public abstract Vec getAt(PathParameter t);
	public abstract Vec getTangentAt(PathParameter t);	
	public abstract Path transform(ITransform m);

	public abstract STuple<Path> splitSimpler();

	public void expand() {
		if(leftSimplified == null){
			STuple<Path> s = splitSimpler();
			leftSimplified = s.l;
			rightSimplified = s.r;
		}
	}

	public Path getLeftSimpler() {
		return leftSimplified;
	}

	public Path getRightSimpler() {
		return rightSimplified;
	}
	
	
	public static enum ReportType{
		T, LENGTH;
	}
	
	void intersections(Path other , ReportType type, List<STuple<PathParameter>> result){
		if(isLine() && other.isLine()){
			getLine().intersectionLine(other.getLine(),type,result);
		} else if(fastOverlapTest(other)){
			if(preferSplitMe(other)){
				expand();
				getLeftSimpler().intersections(other, type, result);
				getRightSimpler().intersections(other, type, result);
			} else {
				other.expand();
				intersections(other.getLeftSimpler(), type,result);
				intersections(other.getRightSimpler(), type,result);
			}
		}
	}

	private boolean fastOverlapTest(Path other) {
		if(isLine()){
			return other.getBBox().overlaps(getLine());
		} else if(other.isLine()){
			return getBBox().overlaps(other.getLine());
		} else {
			return getBBox().overlaps(other.getBBox());
		}
	}
	
	public boolean preferSplitMe(Path other){
		return 	!isLine() && (!other.isLine() ||
				getBBox().diagonalLengthSquared() > 
				other.getBBox().diagonalLengthSquared());
	}
	
	public double fastDistance( Vec p){
		return getBBox().getMiddle().distanceSquared(p);
	}

	public double fastDistance(Path r){
		return getBBox().getMiddle().distanceSquared(r.getBBox().getMiddle());
	}

	

	public void project(Vec p, ReportType type, BestProjection<PathParameter> best){
		if(isLine()){
			double t = getLine().closestT(p);
			Vec v = getLine().getAt(t);
			double dist = v.distanceSquared(p);
			best.update(getLine().convertTBack(t,type), dist,v);
		} else {
			BBox b = getBBox();
			double distSquaredLowerBound = b.getNearestPoint(p).distanceSquared(p);
			if(distSquaredLowerBound >= best.distanceSquaredUpperbound){
				return;
			}
			double distanceSquaredUpperBound = b.getFarthestPoint(p).distanceSquared(p);
			if(distanceSquaredUpperBound < best.distanceSquaredUpperbound){
				best.distanceSquaredUpperbound = distanceSquaredUpperBound;
			}
			expand();
			if(getLeftSimpler().fastDistance(p) <=
					getRightSimpler().fastDistance(p)){
				getLeftSimpler().project(p, type, best);
				getRightSimpler().project(p, type, best);
			} else {
				getRightSimpler().project(p, type, best);
				getLeftSimpler().project(p, type, best);
			}
		}
	}
	

	public void project(Path other, ReportType type, BestProjection<STuple<PathParameter>> best) {
		if(isLine() && other.isLine()){
			TPair res = getLine().closestTs(other.getLine());
			double dist = getLine().getAt(res.tl).distanceSquared(other.getLine().getAt(res.tr)) ;
			STuple<PathParameter> ress = new STuple<PathParameter>(getLine().convertTBack(res.tl,type)
											,other.getLine().convertTBack(res.tr,type));
			best.update(ress, dist);
		} else {
			MinMax mm = getBBox().minMaxDistSquared(other.getBBox());
			if(mm.getMin() > best.distanceSquaredUpperbound){
				return;
			}
			if(mm.getMax() < best.distanceSquaredUpperbound){
				best.distanceSquaredUpperbound = mm.getMax();
			}
			if(preferSplitMe(other)){
				expand();
				if(getLeftSimpler().fastDistance(other) < getRightSimpler().fastDistance( other)){
					getLeftSimpler().project(other,type,best);
					getRightSimpler().project(other,type,best);
				} else {
					getRightSimpler().project(other,type,best);
					getLeftSimpler().project(other,type,best);
				}
			} else{
				other.expand();
				if(fastDistance(other.getLeftSimpler()) < fastDistance(other.getRightSimpler())){
					project(other.getLeftSimpler(),type,best);
					project(other.getRightSimpler(),type,best);
				} else {
					project(other.getRightSimpler(),type,best);
					project(other.getLeftSimpler(),type,best);
				}
			}
		}
	}
	
	void expandFullyAndSetLengths(){
		expandFullyAndSetLengths(0);
	}
	
	double expandFullyAndSetLengths(double length){
		if(isLine()){
			getLine().lengthStart = length;
			getLine().length = getLine().length();
			length += getLine().length;
			return length;
		} else {
			expand();
			length = getLeftSimpler().expandFullyAndSetLengths(length);
			return  getRightSimpler().expandFullyAndSetLengths(length);
		}
	}
	
	PathIterator getPathIterator(){
		return new AWTPathIterator(this);
	}
	
	Iterator<Line> getLineAprroximationIterator(){
		return new LineIterator(this);
	}
	


}
