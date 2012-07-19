package deform.paths;

import java.util.List;

import paths.Constants;
import paths.paths.factory.QueryPathFactory;
import paths.paths.paths.QueryPath;

import deform.BBox;
import deform.Transform;
import deform.Vec;
import deform.segments.LineTo;
import deform.segments.QuadTo;
import deform.segments.Segment;
import deform.segments.SegmentsMaker;

public class Quad extends Path{
	
	final Vec start, control, end;

	public Quad(Vec start, Vec control, Vec end) {
		super(BBox.from3Points(start, control, end));
		this.start = start;
		this.control = control;
		this.end = end;
	}

	
	Vec middle(){
		return start.between(end).between(control);
	}

	@Override
	void renderAffine(Transform t, SegmentsMaker res) {
		res.quad(t.to(start), t.to(control), t.to(end));
		
	}

	@Override
	void renderNonAffine(Transform t, SegmentsMaker res) {
		if(start.distanceSquared(end) <= Constants.MAX_ERROR_TRANSFORM_POW2
				&& start.distanceSquared(control) <= Constants.MAX_ERROR_TRANSFORM_POW2){
					Vec nstart = t.to(start);
					Vec nend = t.to(end);
					if(nstart.distanceSquared(nend) <= Constants.MAX_ERROR_TRANSFORM_POW2){
						res.line(nstart,nend);
						return;
					} 
			} 
			Vec controlLeft = start.between(control);
			Vec controlRight = control.between(end);
			Vec middle = controlLeft.between(controlRight);
			new Quad(start,controlLeft,middle).renderNonAffine(t,res);
			new Quad(middle,controlRight,end).renderNonAffine(t,res);
		
	}

	@Override
	public String toString() {
		return "Quad [" + start + ", " + control + ", " + end
				+ "]";
	}


	@Override
	public
	QueryPath toQueryPath() {
		return QueryPathFactory.createQuad(start, control, end);
	}

	
}
