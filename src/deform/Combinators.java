package deform;

import deform.paths.Path;
import deform.segments.CubicTo;
import deform.segments.LineTo;
import deform.segments.QuadTo;
import deform.segments.SegPath;
import deform.segments.Segment;

public class Combinators {
	
	public Segment lineTo(Vec to){
		return new LineTo(to);
	}
	
	public Segment quadTo(Vec control, Vec to){
		return new QuadTo(control, to);
	}
	
	public Segment cubicTo(Vec control1, Vec control2, Vec to){
		return new CubicTo(control1, control2, to);
	}
	
	public Path path(Vec start, Segment ... segs){
		return new SegPath(start,segs).toPath();
	}

	
	
}
