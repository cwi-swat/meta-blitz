package deform.segments;

import java.awt.geom.Path2D;
import java.awt.geom.PathIterator;
import java.util.ArrayList;
import java.util.List;

import paths.paths.paths.QueryPath;

import deform.shapes.Closed;
import deform.shapes.Shape;
import deform.shapes.ShapeSet;

public class ShapesMaker {
	
	public static Path2D makePath(List<SegPath> sp){
		Path2D res = new Path2D.Double();
		res.setWindingRule(Path2D.WIND_EVEN_ODD);
		int i = 0;
		for(SegPath s: sp){
			Path2D r = s.toJava2d();
			r.closePath();
			i++;
			res.append(r,false);
		}
//		res.closePath();
//		System.out.println(i);
		return res;
	}
	
	public static void fromJava2D(PathIterator it, List<SegPath> res){
		int i = 0 ;
		while(!it.isDone()){
			i++;
			res.add(SegPath.fromJava2d(it));
			it.next();
		}
	}
	
	public static Shape fromJava2dToShape(PathIterator it){
		List<SegPath> res = new ArrayList<SegPath>();
		fromJava2D(it,res);
		Shape s =  makeShape(res);
		return s;
	}
	
	public static Shape makeShape(List<SegPath> res){
		List<Shape> shapes = new ArrayList<Shape>();
		for(SegPath p : res){
			shapes.add(new Closed(p.toPath()));
		}
		return new ShapeSet(shapes);
	}

	

}
