package deform.paths;

import java.util.List;

import paths.Constants;

import deform.BBox;
import deform.Transform;
import deform.Vec;
import deform.segments.LineTo;
import deform.segments.QuadTo;
import deform.segments.Segment;

public class Quad extends Path{
	final Vec start, control, end;

	public Quad(Vec from, Vec control, Vec end) {
		this.start = from;
		this.control = control;
		this.end = end;
	}

	@Override
	public BBox makeBBox() {
		return BBox.from3Points(start, control, end);
	}

	@Override
	public Path transformAffine(Transform t) {
		return new Quad(t.to(start),t.to(control),t.to(end));
	}
	
	Vec middle(){
		return start.between(end).between(control);
	}


	@Override
	void getSimpleLines(Transform t,List<Path> res) {
		if(start.distanceSquared(end) <= Constants.MAX_ERROR_TRANSFORM_POW2
			&& start.distanceSquared(control) <= Constants.MAX_ERROR_TRANSFORM_POW2){
				Vec nstart = t.to(start);
				Vec nend = t.to(end);
				if(nstart.distanceSquared(nend) <= Constants.MAX_ERROR_TRANSFORM_POW2){
					res.add(new Line(nstart,nend));
					return;
				} 
		} 
		Vec controlLeft = start.between(control);
		Vec controlRight = control.between(end);
		Vec middle = controlLeft.between(controlRight);
		new Quad(start,controlLeft,middle).getSimpleLines(t,res);
		new Quad(middle,controlRight,end).getSimpleLines(t,res);
	}

	@Override
	public
	void getSegments(List<Segment> res) {
		res.add(new QuadTo(control,end));
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
