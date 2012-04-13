package bezier.paths;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import bezier.paths.factory.PathFactory;
import bezier.paths.util.PathParameter;
import bezier.points.Vec;
import bezier.util.STuple;
import bezier.util.Tuple;

public abstract class ConnectedPath extends Path{

	public abstract Vec getStartPoint();
	public abstract Vec getEndPoint();
	public abstract Vec getAt(double t);
	public abstract Vec getTangentAt(double t);
	public abstract ConnectedPath reverse();
	public abstract ConnectedPath getWithAdjustedStartPoint(Vec newStart);
	
	public boolean isClosed(){
		return getStartPoint().isEqError(getEndPoint());
	}
	public abstract ConnectedPath getSubPath(double start, double end) ;
	public boolean canAppend(ConnectedPath q){
		return getEndPoint().isEqError(q.getStartPoint());
	}
	public boolean canAppendReversed(ConnectedPath q){
		return getEndPoint().isEqError(q.getEndPoint());
	}

	public boolean willMakeClosed(ConnectedPath other){
		return getStartPoint().isEqError(other.getEndPoint());
	}
	
	public boolean willMakeClosedReversed(ConnectedPath other){
		return getStartPoint().isEqError(other.getStartPoint());
	}
	
	ConnectedPath append(ConnectedPath r) {
		if(canAppend(r)){
			return PathFactory.append(this,r.getWithAdjustedStartPoint(getEndPoint()));
		} else {
			return PathFactory.append(this,r.reverse().getWithAdjustedStartPoint(getEndPoint()));
		}
	}
	
	public ConnectedPath getConnected(){
		return this;
	}
	
	public abstract int nrBelow(Vec p);
	
	@Override
	public boolean isInside(Vec p) {
		return isClosed() && nrBelow(p) % 2 == 1;
	}
	
	public PathParameter getRootPathParameter(){
		return new PathParameter(this,0);
	}
	
	@Override
	public PathParameter getLeftParentPath(PathParameter original) {
		if(original.connected == null){
			return new PathParameter(this, original.t);
		}
		return original;
	}
	
	@Override
	public PathParameter getRightParentPath(PathParameter original) {
		if(original.connected == null){
			return new PathParameter(this, original.t);
		}
		return original;
	}
	
	// below: set operation stuff

	private enum Inside{
		INSIDE,
		OUTSIDE,
		UNSURE;
		
		Inside flip(){
			if(this == INSIDE){
				return OUTSIDE;
			} else {
				return INSIDE;
			}
		}
	}
	
	public Vec getBetween(double tl, double tr) {
		double between = (tl + tr)/2.0;
		return getConnected().getAt(between);
	}
	

	private Inside isInside(double tl, double tr, Path other){
		Vec l = getConnected().getAt(tl);
		Vec r = getConnected().getAt(tr);
		Vec between = getBetween(tl,tr);
		if(l.distanceSquared(between) <= Constants.MAX_ERROR_POW2 ||
				r.distanceSquared(between) <= Constants.MAX_ERROR_POW2){
			return Inside.UNSURE;
		} else {
			return other.isInside(between) ? Inside.INSIDE : Inside.OUTSIDE;
		}
	}
	
	private Inside segmentsStartInside(List<Double> ts, Path other){
		double prev = ts.get(ts.size()-1);
		boolean flip = true;
		for(double cur : ts){
			Inside inside = isInside(prev, cur, other);
			if(inside != Inside.UNSURE){
				if(flip){
					return inside.flip();
				} else {
					return inside;
				}
			}
			flip = !flip;
		}
		return Inside.UNSURE;
	}
	
	public Set<ConnectedPath> getSegments(List<Double> ts, Path other, boolean shouldBeInside){
		boolean startInside =false ;
		switch(segmentsStartInside(ts, other)){
			case INSIDE: startInside = true; break;
			case OUTSIDE: startInside = false ; break;
			case UNSURE: 
				if(ts.size() == 2){
					startInside = ts.get(1)-ts.get(0) < 0.5;
				} else {
					System.err.printf("getSegments : Cannot be sure!!!\n"); 
				} break;
		}
//		assert ts.size() % 2 == 0;
		if(ts.size() %2 != 0){
			Vec prev = getAt(ts.get(ts.size()-1));
			for(double d : ts){
				if(getAt(d).isEqError(prev)){
					System.out.printf("To similar!");
				}
			}
		}
		int first = startInside == shouldBeInside ? 0 : 1;
		Set<ConnectedPath> segments = new HashSet<ConnectedPath>();
		for(int i = first; i < first + ts.size() ; i+=2){
			ConnectedPath p = getSubPath(ts.get(i % ts.size()), ts.get((i+1) % ts.size()));
			if(!p.isEmpty() && p.getStartPoint().distanceSquared(p.getEndPoint()) > Constants.MAX_ERROR_POW2){
				segments.add(p);
			}
			
		}
		return segments;
	}
	

	List<ConnectedPath> findConnecting(Set<ConnectedPath> others){
		List<ConnectedPath> res = new ArrayList<ConnectedPath>();
		for(ConnectedPath q : others){
			if(this != q && (getConnected().canAppend(q) || getConnected().canAppendReversed(q))){
				res.add(q);
			}
		}
		return res;
	}


	
	
}
