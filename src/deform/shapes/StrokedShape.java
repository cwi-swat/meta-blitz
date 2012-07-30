package deform.shapes;

import java.awt.Stroke;
import java.util.ArrayList;
import java.util.List;

import deform.BBox;
import deform.Transform;
import deform.segments.SegPath;
import deform.segments.ShapesMaker;

public class StrokedShape extends Shape{

	final Shape s;
	
	final Stroke stroke;
	
	
	public StrokedShape(Shape s, double width,Stroke stroke) {
		super(s.bbox.grow(width));
		this.s = s;
		this.stroke = stroke;
	}


	@Override
	public void render(BBox area, Transform t, List<SegPath> res) {
		if(area.overlaps(t.transformBBox(bbox))){
		List<SegPath> segs = new ArrayList<SegPath>();
		s.render(area, t, segs);
		java.awt.Shape ps = ShapesMaker.makePath(segs);
		java.awt.Shape js = stroke.createStrokedShape(ps);
		ShapesMaker.fromJava2D(js.getPathIterator(null), res);
		}
		
	}

}
