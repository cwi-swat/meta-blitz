package bezier.paths.compound;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Stack;

import bezier.paths.IConnectedPath;
import bezier.paths.Path;
import bezier.paths.simple.Line;
import bezier.paths.util.ITransform;
import bezier.paths.util.PathParameter;
import bezier.points.Vec;
import bezier.util.BBox;
import bezier.util.HasBBox;
import bezier.util.STuple;
import bezier.util.Util;

public class Paths extends CompoundPath{
	

	final List<Append> paths;
	final List<Integer> choices;
	private List<Integer> sortedX, sortedY;
	
	public Paths(Paths parent, List<Integer> choices,BBox b) {
		this.paths = parent.paths;
		this.sortedX = Util.getChoices(parent.sortedX, choices,paths.size());
		this.sortedY =  Util.getChoices(parent.sortedY, choices,paths.size());
		this.choices = choices;
		this.bbox = b;
	}

	
	public Paths(List<Append> paths) {
		this.paths = paths;
		choices = Util.natListTill(paths.size());
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
		List<Append> ps = new ArrayList<Append>(paths.size());
		for(Path p : ps){
			ps.add((Append)p.transform(m));
		}
		return new Paths(ps);
	}
	
	List<Append> makeSubLoosePaths(List<Integer> indexes){
		List<Append> result = new ArrayList<Append>(indexes.size());
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
		List<Append> lx = makeSubLoosePaths(xsl);
		List<Append> rx = makeSubLoosePaths(xsr);
		List<HasBBox> lxb = (List)lx;
		List<HasBBox> rxb = (List)rx;
		BBox lxbb = new BBox(lxb);
		BBox rxbb = new BBox(rxb);
		List<Integer> ysl = sortedY.subList(0, split);
		List<Integer> ysr= sortedY.subList(split,paths.size());
		List<Append> ly = makeSubLoosePaths(ysl);
		List<Append> ry = makeSubLoosePaths(ysr);
		List<HasBBox> lyb = (List)ly;
		List<HasBBox> ryb = (List)ry;
		BBox lybb = new BBox(lxb);
		BBox rybb = new BBox(rxb);
		if(lxbb.area() + rxbb.area() < lybb.area() + rybb.area()){
			return new STuple<Path>(
					new Paths(this,xsl, lxbb),
					new Paths(this,xsr, lxbb));
		} else {
			return new STuple<Path>(
					new Paths(this,ysl, lybb),
					new Paths(this,ysr, lybb));
		}
	}



	@Override
	public boolean isInside(Vec p) {
		return false;
	}


	public boolean isConnected(){
		return false;
	}
	
	public IConnectedPath getConnected(){
		throw new Error("Not connected!");
	}


	@Override
	public PathParameter convertBackCompound(PathParameter p) {
		return new PathParameter(choices.get(0), p.t);
	}


	@Override
	public boolean isCompoundLeaf() {
		return choices.size() == 1;
	}
	
}
