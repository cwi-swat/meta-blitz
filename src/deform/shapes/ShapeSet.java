package deform.shapes;

import java.awt.geom.Path2D;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import paths.paths.factory.QueryPathFactory;
import paths.paths.paths.QueryPath;

import deform.BBox;
import deform.Transform;
import deform.segments.SegPath;

public class ShapeSet extends Shape{
	public final List<Shape> closed;
	
	public ShapeSet(List<Shape> closed) {
		super(getBBox(closed));
		this.closed = closed;
	}

	public ShapeSet(Shape[] shapes) {
		this(Arrays.asList(shapes));
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
			
		} else {
//			System.out.println("SKIP!");
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

	@Override
	public QueryPath toQueryPath(){
		List<QueryPath> res = new ArrayList<QueryPath>();
		for(Shape s : closed){
			res.add(s.toQueryPath());
		}
		return QueryPathFactory.createSet(res);
	}

	
}
