package deform.segments;

import java.awt.geom.Path2D;

import deform.Vec;
import deform.paths.Path;
import deform.paths.Quad;

public class QuadTo extends Segment {
	
	final Vec control;

	public QuadTo(Vec control, Vec to) {
		super(to);
		this.control = control;
	}

	@Override
	Path make(Vec prev) {
		return new Quad(prev,control,to);
	}


	@Override
	void makeAWT(Path2D s) {
		s.quadTo(control.x, control.y, to.x, to.y);
	}
	
	public String toString(){
		return "quadTo(" + control + ", " + to + ")";
	}
}
