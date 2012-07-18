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
		SegPath sp = s.render( t);
		java.awt.Shape js = stroke.createStrokedShape(ShapesMaker.makePath(Collections.singletonList(sp)));
		ShapesMaker.fromJava2D(js.getPathIterator(null), res);
		
	}

}
