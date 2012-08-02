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

	@Override
	public String toString() {
		return "Line [" + start + ", " + end + "]";
	}

	@Override
	public
	QueryPath toQueryPath() {
		return QueryPathFactory.createLine(start, end);
	}

	void renderSub(BBox b, Transform t,  Vec fromStart, Vec fromEnd,SegmentsMaker res){
		BBox me = BBox.from2Points(fromStart, fromEnd);
		BBox met = t.transformBBox(me);
		if(!b.overlaps(met) || t.isAffine(me)){
			res.line(t.to(fromStart),t.to(fromEnd));
		} else if(fromStart.distanceSquared(fromEnd) <= Constants.MAX_ERROR_TRANSFORM_FROM_POW2){
			renderSubTo(t,fromStart,fromEnd, t.to(fromStart),t.to(fromEnd),res);
		} else {
			Vec mid = fromStart.between(fromEnd);
			renderSub(b,t, fromStart,mid,res);
			renderSub(b,t,mid,fromEnd,res);
		}
	}


	private void renderSubTo(Transform t, Vec fromStart, Vec fromEnd, Vec toStart, Vec toEnd ,SegmentsMaker res) {
		if(toStart.distanceSquared(toEnd) <= Constants.MAX_ERROR_TRANSFORM_POW2){
			res.line(toStart, toEnd);
		} else {
			Vec mid = fromStart.between(fromEnd);
			Vec midTo = t.to(mid);
			renderSubTo(t, fromStart,mid, toStart,midTo,res);
			renderSubTo(t, mid,fromEnd, midTo,toEnd,res);
		}
		
	}

	@Override
	void render(BBox area,Transform t, SegmentsMaker res) {
		BBox met = t.transformBBox(bbox);
		if(!area.overlaps(met) || t.isAffine(bbox)){
			res.line(t.to(start), t.to(end));
		} else {
			renderSub(area,t,start,end,res);
		}
		
	}

	
}
