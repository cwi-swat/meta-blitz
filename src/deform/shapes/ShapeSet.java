package deform.shapes;

import java.awt.geom.Path2D;
import java.util.ArrayList;
import java.util.List;

import deform.BBox;
import deform.Transform;
import deform.segments.SegPath;

public class ShapeSet extends Shape{
	final List<Shape> closed;
	
	public ShapeSet(List<Shape> closed) {
		super(getBBox(closed));
		this.closed = closed;
	}

	static BBox getBBox(List<Shape> closed) {
		BBox b = BBox.emptyBBox;
		for(Shape c : closed){
			b = b.union(c.bbox);
		}
		return b;
	}

	@Override
	public
	void render(BBox area, Transform t, List<SegPath> res) {
		if(area.overlaps(t.transformBBox(bbox))){
			for(Shape s : closed){
				s.render(area, t, res);
			}
		}
	}
	
	public String toString(){
		StringBuffer buf = new StringBuffer();
		buf.append('{');
		for(Shape s: closed){
			buf.append(s.toString());
		}
		buf.append('}');
		return buf.toString();
	}

}
