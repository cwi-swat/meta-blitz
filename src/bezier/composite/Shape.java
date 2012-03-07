package bezier.composite;

import java.util.List;

import bezier.graphtheory.Graph;
import bezier.points.Vec;

public class Shape {
	
	public final Path outer;
	public final Shapes inner;
	
	public Shape(Path outer, Shapes inner) {
		this.outer = outer;
		this.inner = inner;
	}

	public boolean isInside(Vec p){
		return outer.isInside(p) && !inner.isInside(p);
	}
	
	public Shape create(List<Shape> shapes, Graph containsGraph){
		
	}
	
}