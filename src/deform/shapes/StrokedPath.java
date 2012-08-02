package deform.shapes;

import java.awt.Stroke;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import deform.BBox;
import deform.Transform;
import deform.paths.Path;
import deform.segments.SegPath;
import deform.segments.ShapesMaker;

public class StrokedPath extends Shape{

	final Path s;
	final Stroke stroke;
	
	
	public StrokedPath(Path s,double width, Stroke stroke) {
		super(s.bbox.grow(width));
		this.s = s;
		this.stroke = stroke;
	}


	@Override
	public void render(BBox area, Transform t, List<SegPath> res) {
		if(area.overlaps(t.transformBBox(bbox))){
		SegPath sp = s.render(area, t);
		java.awt.Shape js = stroke.createStrokedShape(sp.toJava2d());
		ShapesMaker.fromJava2D(js.getPathIterator(null), res);
		}
		
	}

}
