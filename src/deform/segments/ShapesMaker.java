package deform.segments;

import java.awt.geom.Path2D;
import java.awt.geom.PathIterator;
import java.util.ArrayList;
import java.util.List;

import deform.shapes.Closed;
import deform.shapes.Shape;
import deform.shapes.ShapeSet;

public class ShapesMaker {
	
	public static Path2D makePath(List<SegPath> sp){
		Path2D res = new Path2D.Double();
		for(SegPath s: sp){
			Path2D r = s.toJava2d();
			r.closePath();
			
			res.append(r,false);
		}
		return res;
	}
	
	public static void fromJava2D(PathIterator it, List<SegPath> res){
		while(!it.isDone()){
			res.add(SegPath.fromJava2d(it));
			it.next();
		}
	}
	
	public static Shape makeShape(List<SegPath> res){
		List<Shape> shapes = new ArrayList<Shape>();
		for(SegPath p : res){
			shapes.add(new Closed(p.toPath()));
		}
		return new ShapeSet(shapes);
	}
	

}
