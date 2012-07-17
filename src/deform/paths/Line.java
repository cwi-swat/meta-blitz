package deform.paths;

import java.util.List;

import paths.Constants;
import deform.BBox;
import deform.Transform;
import deform.Vec;
import deform.segments.LineTo;
import deform.segments.Segment;
import deform.segments.SegmentsMaker;

public class Line extends Path{

	final Vec start ,end;

	public Line(Vec start, Vec end) {
		super(BBox.from2Points(start, end));
		this.start = start;
		this.end = end;
	}




	void renderAffine(Transform t, SegmentsMaker res) {
		res.line(start, end);
	}


	void renderNonAffine(Transform t, SegmentsMaker res) {
		if(start.distanceSquared(end) <= Constants.MAX_ERROR_TRANSFORM_POW2){
			Vec nstart = t.to(start);
			Vec nend = t.to(end);
			if(nstart.distanceSquared(nend) <= Constants.MAX_ERROR_TRANSFORM_POW2){
				res.line(nstart,nend);
				return;
			} 
		}
		Vec mid = start.between(end);
		new Line(start,mid).renderNonAffine(t,res);
		new Line(mid, end).renderNonAffine(t,res);
		
	}

	
}
