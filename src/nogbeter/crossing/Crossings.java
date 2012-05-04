package nogbeter.crossing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import bezier.util.Tuple;

import nogbeter.paths.Path;
import nogbeter.paths.PathIndex;
import nogbeter.paths.results.intersections.IIntersections;
import nogbeter.paths.results.intersections.Intersection;

public class Crossings<L extends PathIndex, R extends PathIndex> implements Iterable<Crossing<L,R>> {
	
	final List<Crossing<L,R>> elems;

	public Crossings(List<Crossing<L,R>> elems) {
		this.elems = elems;
	}
	
	public int size(){
		return elems.size();
	}
	
	public String toString(){
		StringBuilder build = new StringBuilder();
		build.append("Crossings[" + elems.size() + "](");
		for(Crossing p : elems){
			build.append(p.toString());
			build.append("\n, ");
		}
		build.append(")");
		return build.toString();
	}

	@Override
	public Iterator<Crossing<L,R>> iterator() {
		return elems.iterator();
	}

}
