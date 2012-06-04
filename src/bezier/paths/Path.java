package bezier.paths;

import java.awt.Shape;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import nogbeter.points.twod.Vec;
import nogbeter.transform.IToTransform;

import bezier.paths.awt.AWTPathIterator;
import bezier.paths.awt.DummyAWTSHape;
import bezier.paths.compound.ICompoundPath;
import bezier.paths.factory.PathFactory;
import bezier.paths.simple.Line;
import bezier.paths.simple.SimplePath;
import bezier.paths.util.BestProjection;
import bezier.paths.util.IPathSelector;
import bezier.paths.util.PathIterator;
import bezier.paths.util.PathParameter;
import bezier.paths.util.TPair;
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
	public abstract ConnectedPath getConnected();
	public abstract ICompoundPath getCompound();
	public abstract Vec getAt(PathParameter t);
	public abstract Vec getTangentAt(PathParameter t);	
	public abstract Path transform(IToTransform m);

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
	
	
	public abstract STuple<List<ApproxCurvePosition>> intersectionsLine(Line other);
	public abstract STuple<List<ApproxCurvePosition>> intersectionsSimple(SimplePath other);
	public abstract STuple<List<ApproxCurvePosition>> intersections(Path other);
	

	public void intersections(Path other , PathParameter lParent,
			PathParameter rParent, Intersections res){
		if(isLine() && other.isLine()){
			TPair t = getLine().intersection(other.getLine());
			if(t != null){
				res.add(new Intersection(
						new ApproxCurvePosition(getLine(), t.tl, lParent),
						new ApproxCurvePosition(other.getLine(), t.tr, rParent)
						)
				);
			}
		} else if(fastOverlapTest(other)){
			if(preferSplitMe(other)){
				expand();
				getLeftSimpler().intersections(other,getLeftParentPath(lParent),rParent, res);
				getRightSimpler().intersections(other, getRightParentPath(lParent),rParent, res);
			} else {
				other.expand();
				intersections(other.getLeftSimpler(), lParent,other.getLeftParentPath(rParent), res);
				intersections(other.getRightSimpler(), lParent,other.getRightParentPath(rParent), res);
			}
		}
	}


	public abstract PathParameter getRootPathParameter() ;
	
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
	
	public Iterator<ConnectedPath> getConnectedIterator(){
		return getIterator(new IPathSelector<ConnectedPath>() {
			@Override
			public ConnectedPath select(Path p) {
				if(p.isConnected()){
					return p.getConnected();
				} else {
					return null;
				}
			}
		});
	}
	
	public Set<ConnectedPath> getConnectedSet(){
		Set<ConnectedPath> result = new HashSet<ConnectedPath>();
		Iterator<ConnectedPath> it = getConnectedIterator();
		while(it.hasNext()){
			result.add(it.next());
		}
		return result;
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
	
	// Below: set operation stuff
	

	public Path union(Path r){
		return merge(false,r,false);
	}

	public Path substract(Path r){
		return merge(false,r,true);
	}
	
	public Path intersection(Path r){
		return merge(true,r,true);
	}

	private Set<ConnectedPath> getConnectedFromPathParameters(List<PathParameter> l){
		Set<ConnectedPath> result = new HashSet<ConnectedPath>();
		for(PathParameter p : l){
			result.add(p.connected);
		}
		return result;
	}
	
	
	private Map<ConnectedPath, List<Double>> getPathParametersPerConnectedPath(List<PathParameter> ps){
		Map<ConnectedPath, List<Double>> res = new HashMap<ConnectedPath, List<Double>>();
		for(PathParameter p : ps){
			ConnectedPath con = p.connected;
			assert con != null;
			if(!res.containsKey(con)){
				res.put(con, new ArrayList<Double>());
			}
			res.get(con).add(p.t);
		}
		for(List<Double> l : res.values()){
			Collections.sort(l);
		}
		return res;
	}
	
	private Set<ConnectedPath> getSegments(boolean shouldBeInside, Path r, List<PathParameter> intersections){
		Set<ConnectedPath> all = getConnectedSet();
		Set<ConnectedPath> intersectionConnectedL = getConnectedFromPathParameters(intersections);
		all.removeAll(intersectionConnectedL); // now contains connected paths with no intersection
		Set<ConnectedPath> res = new HashSet<ConnectedPath>();
		for(ConnectedPath p : all){
			if(r.isInside(p.getStartPoint()) == shouldBeInside){
				res.add(p);
			}
		}
		int before = res.size();
		Map<ConnectedPath, List<Double>> paramPer = getPathParametersPerConnectedPath(intersections);
		for(ConnectedPath p : paramPer.keySet()){
			res.addAll(p.getSegments(paramPer.get(p), r, shouldBeInside));
		}
		return res;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Path merge(boolean shouldBeInside, Path r, boolean shouldBeInsideR){
		STuple<List<PathParameter>> intersections = intersections(r);
		Set<ConnectedPath> leftSegs = getSegments(shouldBeInside, r, intersections.l);
		Set<ConnectedPath> rightSegs = r.getSegments(shouldBeInsideR, this, intersections.r);
		leftSegs.addAll(rightSegs);
		return PathFactory.join((Set)mergeSegments(leftSegs));

		
	}
	
	private static STuple<ConnectedPath> findUniquelyConnectingPair(Set<ConnectedPath> segments){
		for(ConnectedPath p : segments){
			List<ConnectedPath> possibilities = p.findConnecting(segments);
			if(possibilities.size()==1){
				return new STuple<ConnectedPath>(p,possibilities.get(0));
			} else {
				for(ConnectedPath q : possibilities){
					if(p.willMakeClosed(q) || p.willMakeClosedReversed(q)){
						return new STuple<ConnectedPath>(p, q);
					}
				}
			}
		}
		return null;
	}
	
	
	public Set<ConnectedPath> mergeSegments(Set<ConnectedPath> segments){
		Set<ConnectedPath> closedPaths = new HashSet<ConnectedPath>();
		for(ConnectedPath p : segments){
			if(p.isClosed()){
				closedPaths.add(p);
			}
		}
		segments.removeAll(closedPaths);
		STuple<ConnectedPath> cp;
		while((cp = findUniquelyConnectingPair(segments)) != null){
			segments.remove(cp.l); segments.remove(cp.r);
			ConnectedPath merged = cp.l.append(cp.r);
			if(merged.isClosed()){
				closedPaths.add(merged);
			} else {
				segments.add(merged);
			}
		} 
//		
		if(!segments.isEmpty()){
			System.out.println("Left over segments:");
			for(Path p : segments){
				System.out.println(p);
			}
		}
		return closedPaths;
	}

	
	

	
}
