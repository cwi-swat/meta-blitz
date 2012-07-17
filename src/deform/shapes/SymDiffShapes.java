package deform.shapes;

import java.awt.geom.Area;
import java.util.ArrayList;
import java.util.List;

import deform.BBox;
import deform.Transform;
import deform.segments.SegPath;
import deform.segments.ShapesMaker;

public class SymDiffShapes extends Shape{
	final Shape l, r;

	public SymDiffShapes(Shape l, Shape r) {
		super(l.bbox.union(r.bbox));
		this.l = l;
		this.r = r;
	}

	
	@Override
	public
	void render(BBox area, Transform t, List<SegPath> res) {
		List<SegPath> rl = new ArrayList<SegPath>();
		l.render(area, t, rl);
		List<SegPath> rr = new ArrayList<SegPath>();
		r.render(area, t, rr);
		Area la = new Area(ShapesMaker.makePath(rl));
		Area ra = new Area(ShapesMaker.makePath(rr));
		la.exclusiveOr(ra);
		ShapesMaker.fromJava2D(la.getPathIterator(null), res);
	}
}
