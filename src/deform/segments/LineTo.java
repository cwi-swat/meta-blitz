package deform.segments;

import java.awt.geom.Path2D;
import java.awt.geom.Path2D.Double;

import deform.Vec;
import deform.paths.Line;
import deform.paths.Path;


public class LineTo extends Segment{

	public LineTo(Vec to) {
		super(to);
	}

	@Override
	Path make(Vec prev) {
		return new Line(prev,to);
	}

	@Override
	void makeAWT(Path2D s) {
		s.lineTo(to.x, to.y);
	}
	
	
}
