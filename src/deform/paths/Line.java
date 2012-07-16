package deform.paths;

import java.util.List;

import paths.Constants;
import deform.BBox;
import deform.Transform;
import deform.Vec;
import deform.segments.LineTo;
import deform.segments.Segment;

public class Line extends Path{

	final Vec start ,end;

	public Line(Vec start, Vec end) {
		this.start = start;
		this.end = end;
	}

	@Override
	public BBox makeBBox() {
		return BBox.from2Points(start, end);
	}


	@Override
	public Path transformAffine(Transform t) {
		return new Line(t.to(start), t.to(end));
	}
	
	@Override
	void getSimpleLines(Transform t, List<Path> res) {
		if(start.distanceSquared(end) <= Constants.MAX_ERROR_TRANSFORM_POW2){
			Vec nstart = t.to(start);
			Vec nend = t.to(end);
			if(nstart.distanceSquared(nend) <= Constants.MAX_ERROR_TRANSFORM_POW2){
				res.add(new Line(nstart,nend));
				return;
			} 
		}
		Vec mid = start.between(end);
		new Line(start,mid).getSimpleLines(t,res);
		new Line(mid, end).getSimpleLines(t,res);
		
	}

	@Override
	public
	void getSegments(List<Segment> res) {
		res.add(new LineTo(end));
	}
	
	@Override
	public Vec getStart() {
		return start;
	}

	@Override
	public Vec getEnd() {
		return end;
	}
	
}
