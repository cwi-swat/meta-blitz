package deform.paths;

import java.util.List;

import paths.Constants;
import paths.paths.factory.QueryPathFactory;
import paths.paths.paths.QueryPath;
import deform.BBox;
import deform.Transform;
import deform.Vec;
import deform.segments.LineTo;
import deform.segments.Segment;
import deform.segments.SegmentsMaker;

public class Line extends Path{

	static int depth = 0;
	final Vec start ,end;

	public Line(Vec start, Vec end) {
		super(BBox.from2Points(start, end));
		this.start = start;
		this.end = end;
	}




	void renderAffine(Transform t, SegmentsMaker res) {
		res.line(t.to(start), t.to(end));
	}


	void renderNonAffine(Transform t, SegmentsMaker res) {
		depth++;
//		if(depth > 100){
//			System.out.println("huh");
//		}
		if(depth > 60 || start.distanceSquared(end) <= Constants.MAX_ERROR_TRANSFORM_POW2){
			Vec nstart = t.to(start);
			Vec nend = t.to(end);
			if(depth > 60 ||  nstart.distanceSquared(nend) <= Constants.MAX_ERROR_TRANSFORM_POW2){
				res.line(nstart,nend);
				depth--;
				return;
			} 
		}
		Vec mid = start.between(end);
		new Line(start,mid).renderNonAffine(t,res);
		new Line(mid, end).renderNonAffine(t,res);
		depth--;
		
	}




	@Override
	public String toString() {
		return "Line [" + start + ", " + end + "]";
	}




	@Override
	public
	QueryPath toQueryPath() {
		return QueryPathFactory.createLine(start, end);
	}

	
}
