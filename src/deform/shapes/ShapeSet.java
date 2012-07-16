package deform.shapes;

import java.awt.geom.Path2D;
import java.util.ArrayList;
import java.util.List;

import deform.BBox;
import deform.Transform;

public class ShapeSet extends Shape{
	final List<Shape> closed;
	BBox b;
	public ShapeSet(List<Shape> closed) {
		this.closed = closed;
	}

	@Override
	BBox getBBox() {
		if(b == null){
			b = BBox.emptyBBox;
			for(Shape c : closed){
				b = b.union(c.getBBox());
			}
		}
		return b;
	}

	@Override
	java.awt.Shape toJava2D() {
		Path2D res = new Path2D.Double();
		for(Shape c: closed){
			res.append(c.toJava2D(), false);
		}
		return res;
	}

	@Override
	public Shape transform(Transform t) {
		List<Shape> res = new ArrayList<Shape>();
		for(Shape c : closed){
			res.add(c.transform(t));
		}
		return new ShapeSet(res);
	}
}
