package deform.shapes;

import java.awt.geom.Path2D;
import java.util.List;

import deform.BBox;
import deform.Transform;
import deform.Vec;
import deform.paths.Append;
import deform.paths.Line;
import deform.paths.Path;
import deform.segments.SegPath;

public class Closed extends Shape{
	
	final Path path;

	public Closed(Path path) {
		super(path.bbox);
		this.path = path;
	}

	@Override
	public
	void render(BBox area, Transform t, List<SegPath> res) {
		if(area.overlaps(path.bbox)){
			res.add(path.render(t));
		}
	}

	

}
