package bezier.paths.node;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Stack;

import bezier.paths.ConnectedPath;
import bezier.paths.Path;
import bezier.paths.awt.IAWTLeafPath;
import bezier.paths.awt.IAWTNodePath;
import bezier.paths.awt.IAWTPath;
import bezier.paths.leaf.Line;
import bezier.paths.util.ITransform;
import bezier.paths.util.PathParameter;
import bezier.points.Vec;
import bezier.util.BBox;
import bezier.util.HasBBox;
import bezier.util.STuple;
import bezier.util.Util;

public class LoosePaths extends Path implements IAWTNodePath{
	

	final List<ConnectedPath> paths;
	private List<Integer> sortedX, sortedY;
	
	public LoosePaths(List<ConnectedPath> paths,List<Integer> sortedX, List<Integer> sortedY,BBox b) {
		this.paths = paths;
		this.sortedX = sortedX;
		this.sortedY = sortedY;
		this.bbox = b;
	}

	
	public LoosePaths(List<ConnectedPath> paths) {
		this.paths = paths;
	}

	void setSorteds(){
		if(sortedX != null){
			return;
		}
		sortedX = Util.natListTill(paths.size());
		sortedY = Util.natListTill(paths.size());
		Collections.sort(sortedX, new Comparator<Integer>() {
			@Override
			public int compare(Integer o1, Integer o2) {
				return Double.compare(paths.get(o1).getBBox().x, paths.get(o2).getBBox().x);
			}
		});
		Collections.sort(sortedY, new Comparator<Integer>() {
			@Override
			public int compare(Integer o1, Integer o2) {
				return Double.compare(paths.get(o1).getBBox().y, paths.get(o2).getBBox().y);
			}
		});
	}
	
	@Override
	public BBox makeBBox() {
		List<HasBBox> bbx = (List)paths;
		return new BBox(bbx);
	}

	@Override
	public
	boolean isLine() {
		return false;
	}

	@Override
	public
	Line getLine() {
		return null;
	}

	@Override
	public Vec getAt(PathParameter t) {
		return paths.get(t.index).getAt(t);
	}

	@Override
	public Vec getTangentAt(PathParameter t) {
		return paths.get(t.index).getTangentAt(t);
	}

	@Override
	public  Path transform(ITransform m) {
		List<ConnectedPath> ps = new ArrayList<ConnectedPath>(paths.size());
		for(Path p : ps){
			ps.add((ConnectedPath)p.transform(m));
		}
		return new LoosePaths(ps);
	}
	
	List<ConnectedPath> makeSubLoosePaths(List<Integer> indexes){
		List<ConnectedPath> result = new ArrayList<ConnectedPath>(indexes.size());
		for(int i : indexes){
			result.add(paths.get(i));
		}
		return result;
	}
	


	@Override
	public  STuple<Path> splitSimpler() {
		setSorteds();
		int split = paths.size();
		List<Integer> xsl = sortedX.subList(0, split);
		List<Integer> xsr= sortedX.subList(split,paths.size());
		List<ConnectedPath> lx = makeSubLoosePaths(xsl);
		List<ConnectedPath> rx = makeSubLoosePaths(xsr);
		List<HasBBox> lxb = (List)lx;
		List<HasBBox> rxb = (List)rx;
		BBox lxbb = new BBox(lxb);
		BBox rxbb = new BBox(rxb);
		List<Integer> ysl = sortedY.subList(0, split);
		List<Integer> ysr= sortedY.subList(split,paths.size());
		List<ConnectedPath> ly = makeSubLoosePaths(ysl);
		List<ConnectedPath> ry = makeSubLoosePaths(ysr);
		List<HasBBox> lyb = (List)ly;
		List<HasBBox> ryb = (List)ry;
		BBox lybb = new BBox(lxb);
		BBox rybb = new BBox(rxb);
		if(lxbb.area() + rxbb.area() < lybb.area() + rybb.area()){
			return new STuple<Path>(
					new LoosePaths(lx,xsl,Util.getChoices(sortedY,xsl), lxbb),
					new LoosePaths(rx,xsr,Util.getChoices(sortedY,xsr), rxbb));
		} else {
			return new STuple<Path>(
					new LoosePaths(ly,Util.getChoices(sortedX,ysl),ysl, lybb),
					new LoosePaths(ry,Util.getChoices(sortedX,ysr),ysr, rybb));
		}
	}


	@Override
	public
	boolean isLeaf() {
		return false;
	}


	@Override
	public
	IAWTLeafPath getLeaf() {
		throw new Error("Loosepaths is not a leaf!");
	}


	@Override
	public
	IAWTNodePath getNode() {
		return this;
	}


	@Override
	public void pushChildren(Stack<IAWTPath> stack) {
		for(int i = paths.size()-1 ; i >= 0 ; i++){
			stack.push(paths.get(i));
		}
	}
}
