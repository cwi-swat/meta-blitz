package bezier.paths.node;

import java.awt.geom.PathIterator;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import bezier.paths.ConnectedPath;
import bezier.paths.Path;
import bezier.paths.awt.IAWTLeafPath;
import bezier.paths.awt.IAWTNodePath;
import bezier.paths.awt.IAWTPath;
import bezier.paths.leaf.Line;
import bezier.paths.util.ITransform;
import bezier.points.Vec;
import bezier.util.BBox;
import bezier.util.HasBBox;
import bezier.util.STuple;
import bezier.util.Tuple;
import bezier.util.Util;

public class Composite extends ConnectedPath implements IAWTNodePath{

	List<ConnectedPath> curves;
	
	public Composite(List<ConnectedPath> curves,int index) {
		super(index,0, curves.size());
		this.curves = curves;
	}
	
	public boolean isLine(){
		return false;
	}

	public Line getLine(){
		throw new Error("Not a line!");
	}
	
	public Vec getStartPoint(){
		return curves.get(0).getStartPoint();
	}
	
	public Vec getEndPoint(){
		return curves.get(curves.size()-1).getEndPoint();
	}


	private Tuple<Integer,Integer> getStraightInterval(int start, double x){
		int startStraight = start;
		do{
			startStraight--;
		} while(curves.get(Util.mod(startStraight, curves.size())).getEndPoint().x == x);
		startStraight++;
		int endStraight = start ;
		do{
			endStraight++;
		} while(curves.get(Util.mod(endStraight, curves.size())).getStartPoint().x == x);
		endStraight--;
		return new Tuple<Integer, Integer>(startStraight, endStraight);
	}
	
	public int nrBelow(Vec p){
		int nr = 0;
		int i = 0;
		int end = curves.size();
		while(i < end){
			ConnectedPath c = curves.get(i);
			boolean sameStart = c.getStartPoint().x == p.x;
			boolean sameEnd = c.getEndPoint().x == p.x;
			if(sameStart || sameEnd){ // border case
				Tuple<Integer,Integer> straight = getStraightInterval(i, p.x);
				// if tangent did not change, it is an intersection
				int s = Util.mod(straight.l, curves.size());
				int e = Util.mod(straight.r, curves.size());
				if(straight.l < 0){
					end = Math.min(end, -straight.l);
				}
				double yHigh = Math.max(curves.get(s).getEndPoint().y, curves.get(e).getStartPoint().y);
				
				if(yHigh < p.y && Math.signum(curves.get(s).getTangentAt(1.0).x) ==
							Math.signum(curves.get(e).getTangentAt(0.0).x)){
							nr++;
				} 
				i = straight.r+1;
			} else {
				nr+= c.nrBelow(p);
				i++;
			}
		}
		return nr;
	}
	


	public Vec getTangentAt(double t){
		int n = (int) t;
		if(n % curves.size() == 0){
			n--; 
		}
		n = Util.mod(n, curves.size());
		double tl = t - n;
		return curves.get(n).getTangentAt(tl);
	}
	
	
	public Vec getAt(double t){
		int n = (int) t;
		if(n % curves.size() == 0){
			n--; 
		}
		n = Util.mod(n, curves.size());
		double tl = t - n;
		return curves.get(n).getAt(tl);
	}

	@Override
	public  Path transform(ITransform m) {
		List<ConnectedPath> result = new ArrayList<ConnectedPath>(curves.size());
		for(ConnectedPath p : curves){
			result.add((ConnectedPath)p.transform(m));
		}
		return new Composite(result,index);
	}

	@Override
	public ConnectedPath reverse() {
		List<ConnectedPath> result = new ArrayList<ConnectedPath>(curves.size());
		for(int i = curves.size()-1; i >= 0 ; i--){
			result.add(curves.get(i).reverse());
		}
		return new Composite(result,index);
	}

	@Override
	public  ConnectedPath getWithAdjustedStartPoint(Vec newStart) {
		List<ConnectedPath> result = new ArrayList<ConnectedPath>(curves.size());
		result.add(curves.get(0).getWithAdjustedStartPoint(newStart));
		result.addAll(curves.subList(1, curves.size()));
		return new Composite(result,index);
	}

	@Override
	public BBox makeBBox() {
		List<HasBBox> hasBB = (List) curves;
		return new BBox(hasBB);
	}

	

	public static Path createComposite(List<ConnectedPath> p,int index){
		if(p.size() == 1){
			return p.get(0);
		} else {
			return new Composite(p,index);
		}
	}

	@Override
	public  STuple<Path> splitSimpler() {
		int split = curves.size()/2;
		return new STuple<Path>(
				createComposite(curves.subList(0, split),index),
				createComposite(curves.subList(split,curves.size()),index));
	}

	@Override
	public
	boolean isLeaf() {
		return false;
	}

	@Override
	public
	IAWTLeafPath getLeaf() {
		throw new Error("Cannot get Node of path!");
	}

	@Override
	public
	IAWTNodePath getNode() {
		return this;
	}

	@Override
	public void pushChildren(Stack<IAWTPath> stack) {
		stack.push(new StartAWT());
		for(int i = curves.size()-1 ; i >= 0 ; i++){
			stack.push(curves.get(i));
		}
		stack.push(new EndAWT());
	}
	
	class StartAWT implements IAWTLeafPath, IAWTPath{
		@Override
		public int currentSegmentAWT(float[] coords) {
			coords[0] = (float)curves.get(0).getStartPoint().x;
			coords[1] = (float)curves.get(0).getStartPoint().y;
			return PathIterator.SEG_MOVETO;
		}

		@Override
		public boolean isLeaf() {
			return true;
		}

		@Override
		public IAWTLeafPath getLeaf() {
			return this;
		}

		@Override
		public IAWTNodePath getNode() {
			throw new Error("Not a node!");
		}
	}
	
	class EndAWT implements IAWTLeafPath,IAWTPath{
		@Override
		public int currentSegmentAWT(float[] coords) {
			return PathIterator.SEG_CLOSE;
		}
		
		@Override
		public boolean isLeaf() {
			return true;
		}

		@Override
		public IAWTLeafPath getLeaf() {
			return this;
		}

		@Override
		public IAWTNodePath getNode() {
			throw new Error("Not a node!");
		}
	}

}
