package bezier.paths;

import java.util.List;

import bezier.paths.util.BestProjection;
import bezier.paths.util.ITransform;
import bezier.paths.util.PathParameter;
import bezier.paths.util.TPair;
import bezier.points.Vec;
import bezier.util.BBox;
import bezier.util.HasBBox;
import bezier.util.MinMax;
import bezier.util.STuple;

public abstract class Path implements HasBBox {
	
	protected BBox bbox; 
	private Path leftSimplified, rightSimplified;
	
	abstract BBox makeBBox();
	
	public BBox getBBox(){
		if(bbox == null){
			bbox = makeBBox();
		} 
		return bbox;
	}
	abstract boolean isLine();
	abstract Line getLine();
	abstract Vec getAt(PathParameter t);
	abstract Vec getTangentAt(PathParameter t);	
	abstract Path transform(ITransform m);

	abstract STuple<Path> splitSimpler();
	

	void expand() {
		if(leftSimplified == null){
			STuple<Path> s = splitSimpler();
			leftSimplified = s.l;
			rightSimplified = s.r;
		}
	}

	Path getLeftSimpler() {
		return leftSimplified;
	}

	Path getRightSimpler() {
		return rightSimplified;
	}
	

	
	
	void intersections(Path other , List<STuple<PathParameter>> result){
		if(isLine() && other.isLine()){
			getLine().intersectionLine(other.getLine(),result);
		} else if(fastOverlapTest(other)){
			if(preferSplitMe(other)){
				expand();
				getLeftSimpler().intersections(other, result);
				getRightSimpler().intersections(other, result);
			} else {
				other.expand();
				intersections(other.getLeftSimpler(),result);
				intersections(other.getRightSimpler(),result);
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

	

	public void project(Vec p, BestProjection<PathParameter> best){
		if(isLine()){
			double t = getLine().closestT(p);
			Vec v = getLine().getAt(t);
			double dist = v.distanceSquared(p);
			best.update(getLine().convertTBack(t), dist,v);
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
				getLeftSimpler().project(p, best);
				getRightSimpler().project(p, best);
			} else {
				getRightSimpler().project(p, best);
				getLeftSimpler().project(p, best);
			}
		}
	}
	

	public void project(Path other, BestProjection<STuple<PathParameter>> best) {
		if(isLine() && other.isLine()){
			TPair res = getLine().closestTs(other.getLine());
			double dist = getLine().getAt(res.tl).distanceSquared(other.getLine().getAt(res.tr)) ;
			STuple<PathParameter> ress = new STuple<PathParameter>(getLine().convertTBack(res.tl)
											,other.getLine().convertTBack(res.tr));
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
					getLeftSimpler().project(other,best);
					getRightSimpler().project(other,best);
				} else {
					getRightSimpler().project(other,best);
					getLeftSimpler().project(other,best);
				}
			} else{
				other.expand();
				if(fastDistance(other.getLeftSimpler()) < fastDistance(other.getRightSimpler())){
					project(other.getLeftSimpler(),best);
					project(other.getRightSimpler(),best);
				} else {
					project(other.getRightSimpler(),best);
					project(other.getLeftSimpler(),best);
				}
			}
		}
	
	}

}
