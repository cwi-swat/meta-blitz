package bezier.paths;

import java.awt.Shape;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import bezier.paths.awt.AWTPathIterator;
import bezier.paths.awt.DummyAWTSHape;
import bezier.paths.compound.CompoundPath;
import bezier.paths.simple.Line;
import bezier.paths.simple.SimplePath;
import bezier.paths.util.BestProjection;
import bezier.paths.util.IPathSelector;
import bezier.paths.util.ITransform;
import bezier.paths.util.PathIterator;
import bezier.paths.util.PathParameter;
import bezier.paths.util.TPair;
import bezier.points.Vec;
import bezier.util.BBox;
import bezier.util.HasBBox;
import bezier.util.MinMax;
import bezier.util.STuple;

public abstract class Path implements HasBBox{
	
	protected BBox bbox; 
	private Path left, right;
	
	public abstract BBox makeBBox();
	
	public BBox getBBox(){
		if(bbox == null){
			bbox = makeBBox();
		} 
		return bbox;
	}
	

	public abstract boolean isLine();
	public abstract Line getLine();
	public abstract boolean isSimple();
	public abstract SimplePath getSimple();
	public abstract boolean isConnected();
	public abstract IConnectedPath getConnected();
	public abstract CompoundPath getCompound();
	public abstract Vec getAt(PathParameter t);
	public abstract Vec getTangentAt(PathParameter t);	
	public abstract Path transform(ITransform m);

	public abstract Path getSubPath(PathParameter start, PathParameter end);
	public abstract STuple<Path> splitSimpler();

	
	public boolean isInside(Vec p){
		return false;
	}
	
	public boolean isEmpty(){
		return false;
	}
	
	public void expand() {
		if(left == null){
			STuple<Path> s = splitSimpler();
			left = s.l;
			right = s.r;
		}
	}

	public Path getLeftSimpler() {
		return left;
	}

	public Path getRightSimpler() {
		return right;
	}
	
	
	public static enum ReportType{
		T, LENGTH;
	}
	
	public STuple<List<PathParameter>> intersections(Path other ){
		List<PathParameter> lres = new ArrayList<PathParameter>();
		List<PathParameter> rres = new ArrayList<PathParameter>();
		if(!isEmpty() && !other.isEmpty()){
			intersections(other, ReportType.T, new PathParameter(0), new PathParameter(0), lres, rres);
		}
		return new STuple<List<PathParameter>>(lres, rres);
	}
	
	public void intersections(Path other , ReportType type, PathParameter lParent,
			PathParameter rParent, List<PathParameter> lres, List<PathParameter> rres){
		if(isLine() && other.isLine()){
			getLine().intersectionLine(other.getLine(),type,lParent,rParent,lres,rres);
		} else if(fastOverlapTest(other)){
			if(preferSplitMe(other)){
				expand();
				getLeftSimpler().intersections(other, type, getLeftParentPath(lParent),rParent, lres,rres);
				getRightSimpler().intersections(other, type, getRightParentPath(lParent),rParent, lres,rres);
			} else {
				other.expand();
				intersections(other.getLeftSimpler(), type,lParent,other.getLeftParentPath(rParent), lres,rres);
				intersections(other.getRightSimpler(), type,lParent,other.getRightParentPath(rParent), lres,rres);
			}
		}
	}

	public PathParameter getLeftParentPath(PathParameter original) {
		return original;
	}
	
	public PathParameter getRightParentPath(PathParameter original) {
		return original;
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

	public PathParameter project(Vec v){
		BestProjection<PathParameter> best = new BestProjection<PathParameter>();
		if(!isEmpty()){
			project(v, ReportType.T, new PathParameter(0), best);
		}
		return best.t;
	}


	
	public void project(Vec p, ReportType type, PathParameter parentPath, BestProjection<PathParameter> best){
		if(isLine()){
			double t = getLine().closestT(p);
			Vec v = getLine().getAt(t);
			double dist = v.distanceSquared(p);
			best.update(getLine().convertTBackLeaf(t,type,parentPath), dist,v);
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
				getLeftSimpler().project(p,  type, getLeftParentPath(parentPath), best);
				getRightSimpler().project(p,  type, getRightParentPath(parentPath), best);
			} else {
				getRightSimpler().project(p, type, getRightParentPath(parentPath), best);
				getLeftSimpler().project(p,  type, getLeftParentPath(parentPath), best);
			}
		}
	}


	public STuple<PathParameter> project(Path other){
		BestProjection<STuple<PathParameter>> best = new BestProjection<STuple<PathParameter>>();
		if(!isEmpty() && !other.isEmpty()){
			project(other,ReportType.T,new PathParameter(0),new PathParameter(0),best);
		}
		return best.t;
	}
	
	public void project(Path other, ReportType type,PathParameter lParent,
			PathParameter rParent, BestProjection<STuple<PathParameter>> best) {
		if(isLine() && other.isLine()){
			TPair res = getLine().closestTs(other.getLine());
			double dist = getLine().getAt(res.tl).distanceSquared(other.getLine().getAt(res.tr)) ;
			STuple<PathParameter> ress = new STuple<PathParameter>(getLine().convertTBackLeaf(res.tl,type,lParent)
											,other.getLine().convertTBackLeaf(res.tr,type,rParent));
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
					getLeftSimpler().project(other,type,getLeftParentPath(lParent),rParent,best);
					getRightSimpler().project(other,type,getRightParentPath(lParent),rParent,best);
				} else {
					getRightSimpler().project(other,type,getRightParentPath(lParent),rParent,best);
					getLeftSimpler().project(other,type,getLeftParentPath(lParent),rParent,best);
				}
			} else{
				other.expand();
				if(fastDistance(other.getLeftSimpler()) < fastDistance(other.getRightSimpler())){
					project(other.getLeftSimpler(),type,lParent,other.getLeftParentPath(rParent),best);
					project(other.getRightSimpler(),type,lParent,other.getRightParentPath(rParent),best);
				} else {
					project(other.getRightSimpler(),type,lParent,other.getRightParentPath(rParent),best);
					project(other.getLeftSimpler(),type,lParent,other.getLeftParentPath(rParent),best);
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
			length =  getRightSimpler().expandFullyAndSetLengths(length);
			return length;
		}
	}
	
	<T> Iterator<T> getIterator(IPathSelector<T> selector){
		return new PathIterator<T>(this, selector);
	}
	
	public Iterator<Line> getLineIterator(){
		return getIterator(new IPathSelector<Line>() {
			@Override
			public Line select(Path p) {
				if(p.isLine()){
					return p.getLine();
				} else {
					return null;
				}
			}
		});
	}
	
	public Iterator<IConnectedPath> getConnectedIterator(){
		return getIterator(new IPathSelector<IConnectedPath>() {
			@Override
			public IConnectedPath select(Path p) {
				if(p.isConnected()){
					return p.getConnected();
				} else {
					return null;
				}
			}
		});
	}
	
	public Iterator<SimplePath> getSimpleIterator(){
		return getIterator(new IPathSelector<SimplePath>() {
			@Override
			public SimplePath select(Path p) {
				if(p.isSimple()){
					return p.getSimple();
				} else {
					return null;
				}
			}
		});
	}
	
	public Shape getAWTShape(){
		return new DummyAWTSHape(new AWTPathIterator(getConnectedIterator()));
	}
}
