package deform.segments;

import java.awt.geom.Path2D;

import deform.Vec;
import deform.paths.Cubic;
import deform.paths.Path;

public class CubicTo extends Segment{

	final Vec control1, control2;

	public CubicTo(Vec control1, Vec control2, Vec to) {
		super(to);
		this.control1 = control1;
		this.control2 = control2;
	}

	@Override
	Path make(Vec prev) {
		return new Cubic(prev, control1, control2, to);
	}
	@Override
	void makeAWT(Path2D s) {
		s.curveTo(control1.x, control1.y, control2.x, control2.y, to.x, to.y);
	}
	
	public String toString(){
		return "cubicTo(" + control1 + ", " + control2 + ", " +  to + ")";
	}
}
