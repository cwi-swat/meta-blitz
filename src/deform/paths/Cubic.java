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


	

	void renderSub(BBox b, Transform t, SegmentsMaker res){
		BBox met = t.transformBBox(bbox);
		if(!b.overlaps(met) || t.isAffine(bbox)){
			res.cubic(t.to(start),t.to(controll),t.to(controlr), t.to(end));
		} else if(start.distanceSquared(end) <= Constants.MAX_ERROR_TRANSFORM_FROM_POW2 &&
				start.distanceSquared(controll) <= Constants.MAX_ERROR_TRANSFORM_FROM_POW2 &&
				start.distanceSquared(controlr) <= Constants.MAX_ERROR_TRANSFORM_FROM_POW2){
			renderSubTo(t,t.to(start),t.to(end),res);
		} else {
			Vec controlll = start.between(controll);
			Vec controlrr = controlr.between(end);
			Vec inter = controll.between(controlr);
			Vec controllr = controlll.between(inter);
			Vec controlrl = inter.between(controlrr);
			Vec middle = controllr.between(controlrl);
			new Cubic(start,controlll,controllr,middle).renderSub(b,t,res);
			new Cubic(middle, controlrl, controlrr, end).renderSub(b,t,res);
		}
	}


	private void renderSubTo(Transform t, Vec toStart, Vec toEnd ,SegmentsMaker res) {
		if(toStart.distanceSquared(toEnd) <= Constants.MAX_ERROR_TRANSFORM_POW2){
			res.line(toStart, toEnd);
		} else {
			Vec controlll = start.between(controll);
			Vec controlrr = controlr.between(end);
			Vec inter = controll.between(controlr);
			Vec controllr = controlll.between(inter);
			Vec controlrl = inter.between(controlrr);
			Vec middle = controllr.between(controlrl);
			Vec middleTo = t.to(middle);
			new Cubic(start,controlll,controllr,middle).renderSubTo(t,toStart,middleTo,res);
			new Cubic(middle, controlrl, controlrr, end).renderSubTo(t,middleTo,toEnd,res);
		}
		
	}

	@Override
	void render(BBox area,Transform t, SegmentsMaker res) {
		BBox met = t.transformBBox(bbox);
		if(!area.overlaps(met) || t.isAffine(bbox)){
			res.cubic(t.to(start),t.to(controll),t.to(controlr), t.to(end));
		} else {
			renderSub(area,t,res);
		}
		
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
