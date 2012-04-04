package bezier.paths;

import java.awt.Shape;
import java.util.Iterator;
import java.util.List;

import bezier.paths.awt.AWTPathIterator;
import bezier.paths.awt.DummyAWTSHape;
import bezier.paths.compound.CompoundPath;
import bezier.paths.simple.Line;
import bezier.paths.simple.SimplePath;
import bezier.paths.util.BestProjection;
import bezier.paths.util.IPathParameterTransformer;
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

	public abstract STuple<Path> splitSimpler();

	public boolean isCompoundLeaf(){
		return !isSimple() && getCompound().isCompoundLeaf();
	}
	
	public boolean isInside(Vec p){
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
	
	public void intersections(Path other , ReportType type, List<PathParameter> lres, List<PathParameter> rres){
		if(isLine() && other.isLine()){
			getLine().intersectionLine(other.getLine(),type,lres,rres);
		} else if(fastOverlapTest(other)){
			int lsizestart = lres.size();
			int rsizestart = rres.size();
			if(preferSplitMe(other)){
				expand();
				getLeftSimpler().intersections(other, type, lres,rres);
				getRightSimpler().intersections(other, type, lres,rres);
			} else {
				other.expand();
				intersections(other.getLeftSimpler(), type,lres,rres);
				intersections(other.getRightSimpler(), type,lres,rres);
			}
			convertUpwards(this,lres, lsizestart);
			convertUpwards(other,rres, rsizestart);
		}
	}

	public void convertUpwards(Path p, List<PathParameter> lres, int lsizestart) {
		if(isCompoundLeaf()){
			p.getCompound().convertBackCompounds(lres.subList(lsizestart, lres.size()));
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

	

	public void project(Vec p, IPathParameterTransformer trans,ReportType type, BestProjection<PathParameter> best){
		if(isLine()){
			double t = getLine().closestT(p);
			Vec v = getLine().getAt(t);
			double dist = v.distanceSquared(p);
			best.update(trans.transform(getLine().convertTBackLeaf(t,type)), dist,v);
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
			PathParameter oldt = best.t;
			if(getLeftSimpler().fastDistance(p) <=
					getRightSimpler().fastDistance(p)){
				getLeftSimpler().project(p, trans, type, best);
				getRightSimpler().project(p, trans, type, best);
			} else {
				getRightSimpler().project(p,trans, type, best);
				getLeftSimpler().project(p, trans, type, best);
			}
			convertUpwardsBestProjection(this,best, oldt);
		}
	}
	

	private void convertUpwardsBestProjection(Path path,
			BestProjection<PathParameter> best, PathParameter oldt) {
		if(isCompoundLeaf() && best.t != oldt){
			best.t = getCompound().convertBackCompound(best.t);
		}
		
	}

	public void project(Path other, ReportType type, BestProjection<STuple<PathParameter>> best) {
		if(isLine() && other.isLine()){
			TPair res = getLine().closestTs(other.getLine());
			double dist = getLine().getAt(res.tl).distanceSquared(other.getLine().getAt(res.tr)) ;
			STuple<PathParameter> ress = new STuple<PathParameter>(getLine().convertTBackLeaf(res.tl,type)
											,other.getLine().convertTBackLeaf(res.tr,type));
			best.update(ress, dist);
		} else {
			MinMax mm = getBBox().minMaxDistSquared(other.getBBox());
			if(mm.getMin() > best.distanceSquaredUpperbound){
				return;
			}
			PathParameter oldtl = best.t.l;
			PathParameter oldtr = best.t.r;
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
			convertUpwardsBestProjectionTup( other, best, oldtl, oldtr);
		}
	}
	
	private void convertUpwardsBestProjectionTup(Path other,
			BestProjection<STuple<PathParameter>> best, PathParameter oldtl,PathParameter oldtr) {
		if(isCompoundLeaf() && best.t.l != oldtl){
			best.t = new STuple<PathParameter>(getCompound().convertBackCompound(best.t.l), best.t.r);
		}
		if(other.isCompoundLeaf() && best.t.r != oldtr){
			best.t = new STuple<PathParameter>(best.t.l,other.getCompound().convertBackCompound(best.t.r));
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
