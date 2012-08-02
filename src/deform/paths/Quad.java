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
//
//	@Override
//	void renderAffine(Transform t, SegmentsMaker res) {
//		res.quad(t.to(start), t.to(control), t.to(end));
//		
//	}
//
//	@Override
//	void renderNonAffine(Transform t, SegmentsMaker res) {
//		if(start.distanceSquared(end) <= Constants.MAX_ERROR_TRANSFORM_POW2
//				&& start.distanceSquared(control) <= Constants.MAX_ERROR_TRANSFORM_POW2){
//					Vec nstart = t.to(start);
//					Vec nend = t.to(end);
//					if(nstart.distanceSquared(nend) <= Constants.MAX_ERROR_TRANSFORM_POW2){
//						res.line(nstart,nend);
//						return;
//					} 
//			} 
//			Vec controlLeft = start.between(control);
//			Vec controlRight = control.between(end);
//			Vec middle = controlLeft.between(controlRight);
//			new Quad(start,controlLeft,middle).renderNonAffine(t,res);
//			new Quad(middle,controlRight,end).renderNonAffine(t,res);
//		
//	}
	
	void renderSub(BBox b, Transform t, SegmentsMaker res){
		BBox met = t.transformBBox(bbox);
		if(!b.overlaps(met) || t.isAffine(bbox)){
			res.quad(t.to(start),t.to(control),t.to(end));
		} else if(start.distanceSquared(end) <= Constants.MAX_ERROR_TRANSFORM_FROM_POW2 &&
				start.distanceSquared(control) <= Constants.MAX_ERROR_TRANSFORM_FROM_POW2){
			renderSubTo(t,t.to(start),t.to(end),res);
		} else {
			Vec controlLeft = start.between(control);
			Vec controlRight = control.between(end);
			Vec middle = controlLeft.between(controlRight);
			new Quad(start,controlLeft,middle).renderSub(b,t,res);
			new Quad(middle,controlRight,end).renderSub(b,t,res);
		}
	}


	private void renderSubTo(Transform t, Vec toStart, Vec toEnd ,SegmentsMaker res) {
		if(toStart.distanceSquared(toEnd) <= Constants.MAX_ERROR_TRANSFORM_POW2){
			res.line(toStart, toEnd);
		} else {
			Vec controlLeft = start.between(control);
			Vec controlRight = control.between(end);
			Vec middle = controlLeft.between(controlRight);
			Vec middleTo = t.to(middle);
			new Quad(start,controlLeft,middle).renderSubTo(t,toStart,middleTo,res);
			new Quad(middle,controlRight,end).renderSubTo(t,middleTo,toEnd,res);
		}
		
	}

	@Override
	void render(BBox area,Transform t, SegmentsMaker res) {
		BBox met = t.transformBBox(bbox);
		if(!area.overlaps(met) || t.isAffine(bbox)){
			res.quad(t.to(start),t.to(control), t.to(end));
		} else {
			renderSub(area,t,res);
		}
		
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
