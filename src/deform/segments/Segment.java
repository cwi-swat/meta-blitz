package deform.segments;

import java.awt.geom.Path2D;

import deform.Vec;
import deform.paths.Path;

public abstract class Segment {
	final Vec to;
	Segment(Vec to) {
		this.to = to;
	}
	abstract Path make(Vec prev);
	abstract void makeAWT(Path2D p);
}
