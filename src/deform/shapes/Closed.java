package deform.shapes;

import java.awt.geom.Path2D;

import deform.BBox;
import deform.Transform;
import deform.Vec;
import deform.paths.Append;
import deform.paths.Line;
import deform.paths.Path;
import deform.segments.SegPath;

public class Closed extends Shape{
	
	final Path path;

	public Closed(Path path) {
		Vec s = path.getStart();
		Vec e = path.getEnd();
		if(!s.isEq(e)){
			this.path = new Append(path,new Line(e,s));
		} else {
			this.path = path;
		}
	}
	
	BBox getBBox(){
		return path.getBBox();
	}
	
	java.awt.Shape toJava2D(){
		Path2D res = SegPath.fromPath(path).toJava2d();
		res.closePath();
		return res;
	}

	@Override
	public Shape transform(Transform t) {
		return new Closed(path.transform(t));
	}
	

}
