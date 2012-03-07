package bezier.composite;

import java.util.List;
import java.util.Set;

import bezier.graphtheory.Graph;
import bezier.points.Vec;
import bezier.util.BBox;
import bezier.util.Tuple;

public class Shapes {

	public final List<Shape> shape;
	public final BBox bbox;
	
	public Shapes(List<Shape> shape, BBox bbox) {
		this.shape = shape;
		this.bbox = bbox;
	}

	public static Shapes fromPaths(Paths paths){
		boolean[][] contains = new boolean[paths.paths.size()][paths.paths.size()];
		for(int i = 0 ; i < paths.nr() ; i++){
			for(int j = 0 ; j < paths.nr() ; j++){
				contains[i][j] = i != j && paths.get(i).isInside(paths.get(j).getArbPoint());
			}
		}
		Graph containsGraph = new Graph(contains);
		for(List<Tuple<Integer, Set<Integer>>> subGraph : containsGraph){
			
		}
		
	}

	public boolean isInside(Vec p) {
		// TODO Auto-generated method stub
		return false;
	}
	
}
