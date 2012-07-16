package deform.shapes;


import java.awt.geom.PathIterator;
import java.util.ArrayList;
import java.util.List;

import deform.BBox;
import deform.Transform;
import deform.segments.SegPath;

public abstract class Shape {

	abstract BBox getBBox();
	
	abstract java.awt.Shape toJava2D();
	
	public abstract Shape transform(Transform t);
	
	static Shape fromJava2D(PathIterator it){
		List<Shape> res = new ArrayList<Shape>();
		while(!it.isDone()){
			res.add(new Closed(SegPath.fromJava2d(it).toPath()));
			it.next();
		}
		return new ShapeSet(res);
	}
}
