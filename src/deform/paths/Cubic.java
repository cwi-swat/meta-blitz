package deform.paths;

import java.util.List;

import paths.Constants;
import paths.paths.factory.QueryPathFactory;
import paths.paths.paths.QueryPath;

import deform.BBox;
import deform.Transform;
import deform.Vec;
import deform.segments.CubicTo;
import deform.segments.Segment;
import deform.segments.SegmentsMaker;

public class Cubic extends Path{
	final Vec start, controll, controlr, end;

	public Cubic(Vec start, Vec controll, Vec controlr, Vec end) {
		super(BBox.from4Points(start, controll, controlr, end));
		this.start = start;
		this.controll = controll;
		this.controlr = controlr;
		this.end = end;
	}

	@Override
	void renderAffine(Transform t, SegmentsMaker res) {
		res.cubic(t.to(start), t.to(controll), t.to(controlr), t.to(end));
	}


	@Override
	void renderNonAffine(Transform t, SegmentsMaker res) {
		if(start.distanceSquared(end) <= Constants.MAX_ERROR_TRANSFORM_POW2
				&& start.distanceSquared(controll) <= Constants.MAX_ERROR_TRANSFORM_POW2 
				&& controlr.distanceSquared(end) <= Constants.MAX_ERROR_TRANSFORM_POW2 )
		{
			Vec nstart = t.to(start);
			Vec nend = t.to(end);
			if(nstart.distanceSquared(nend) <= Constants.MAX_ERROR_TRANSFORM_POW2){
				res.line(nstart,nend);
				return;
			} 
		}
		Vec controlll = start.between(controll);
		Vec controlrr = controlr.between(end);
		Vec inter = controll.between(controlr);
		Vec controllr = controlll.between(inter);
		Vec controlrl = inter.between(controlrr);
		Vec middle = controllr.between(controlrl);
		new Cubic(start,controlll,controllr,middle).renderNonAffine(t,res);
		new Cubic(middle, controlrl, controlrr, end).renderNonAffine(t,res);
	}

	@Override
	public String toString() {
		return "Cubic [" + start + ", " + controll
				+ ", " + controlr + ", " + end + "]";
	}

	@Override
	public
	QueryPath toQueryPath() {
		return QueryPathFactory.createCubic(start, controll, controlr, end);
	}
	
	
}
