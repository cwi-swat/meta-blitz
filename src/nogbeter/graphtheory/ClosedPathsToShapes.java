package nogbeter.graphtheory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import bezier.util.Tuple;

import nogbeter.paths.Path;
import nogbeter.paths.compound.ClosedPath;
import nogbeter.paths.factory.PathFactory;

public class ClosedPathsToShapes {
	
	public static Path closedPathsToShapes(List<ClosedPath> closed){
		Tuple<List<Integer>,List<Integer>> bh = splitIntoBordersAndHoles(closed);
		List<Integer> borders = bh.l;
		List<Integer> holes = bh.r;
		boolean[][] contains = new boolean[closed.size()][closed.size()];
		for(int i : borders){
			for(int j : holes){
				if(closed.get(i).contains(closed.get(j))){
					contains[i][j] = true;
				} else if(!closed.get(j).contains(closed.get(i))){
					contains[j][i] = true;
				}
			}
		}
		List<Tree> forrest = new Graph(contains).getForrest();
		return PathFactory.createSet(makeShapeSet(closed,forrest));
	}

	private static List<Path> makeShapeSet(List<ClosedPath> closed, List<Tree> forrest) {
		List<Path> shapes = new ArrayList<Path>();
		for(Tree t : forrest){
			shapes.add(makeShape(closed,t));
		}
		return shapes;
	}

	private static Path makeShape(List<ClosedPath> closed, Tree t) {
		return PathFactory.createShape(closed.get(t.root), makeShapeSet(closed, Arrays.asList(t.children)));
	}

	static Tuple<List<Integer>,List<Integer>> splitIntoBordersAndHoles(List<ClosedPath> closed){
		List<Integer> borders = new ArrayList<Integer>();
		List<Integer> holes = new ArrayList<Integer>();
		for(int i = 0 ; i < closed.size() ; i++){
			if(closed.get(i).isDefindedClockwise()){
				borders.add(i);
			} else {
				holes.add(i);
			}
		}
		return new Tuple<List<Integer>, List<Integer>>(borders, holes);
	}
	
}
