package deform.shapes;


import java.awt.geom.PathIterator;
import java.util.ArrayList;
import java.util.List;

import paths.paths.paths.QueryPath;

import deform.BBox;
import deform.Transform;
import deform.segments.SegPath;

public abstract class Shape {

	public final BBox bbox;
	
	Shape(BBox bbox) {
		this.bbox = bbox;
	}
	
	
	public abstract void render(BBox area, Transform t, List<SegPath> res);


	public QueryPath toQueryPath(){
				throw new Error("Not a canonical Shape!");
	}

	
}
