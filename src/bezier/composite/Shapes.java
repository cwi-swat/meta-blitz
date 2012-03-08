package bezier.composite;

import java.awt.geom.Path2D;
import java.awt.geom.PathIterator;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import bezier.graphtheory.Graph;
import bezier.graphtheory.MutableGraph;
import bezier.points.Vec;
import bezier.util.BBox;
import bezier.util.STuple;
import bezier.util.Tuple;

public class Shapes implements Area{

	public final List<Shape> shapes;
	public final BBox bbox;
	
	
	public Shapes(MutableGraph<Path> containsGraph){
		shapes = new ArrayList<Shape>();
		for(MutableGraph<Path> sub : containsGraph.subGraphs()){
			shapes.add(new Shape(sub));
		}
		bbox =  new BBox(shapes.toArray(new Shape[]{}));
	}
	
	public Shapes(Paths paths){
		this(paths.containsGraph());
	}

	public boolean isInside(Vec p) {
		for(Shape s : shapes){
			if(s.isInside(p)){
				return true;
			}
		}
		return false;
	}



	@Override
	public BBox getBBox() {
		return bbox;
	}
	

	public String toString(){
		StringBuffer b = new StringBuffer();
		b.append("Paths:\n");
		for(Shape c : shapes){
			b.append(c.toString());
			b.append('\n');
		}
		b.append('\n');
		return b.toString();
	}
	
}
