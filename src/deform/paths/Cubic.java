package deform.paths;

import java.util.List;

import paths.Constants;

import deform.BBox;
import deform.Transform;
import deform.Vec;
import deform.segments.CubicTo;
import deform.segments.Segment;

public class Cubic extends Path{
	final Vec start, controll, controlr, end;

	public Cubic(Vec start, Vec controll, Vec controlr, Vec end) {
		this.start = start;
		this.controll = controll;
		this.controlr = controlr;
		this.end = end;
	}

	@Override
	public BBox makeBBox() {
		return BBox.from4Points(start, controll, controlr, end);
	}

	@Override
	void getSimpleLines(Transform t,List<Path> res) {
		if(start.distanceSquared(end) <= Constants.MAX_ERROR_TRANSFORM_POW2
			&& start.distanceSquared(controll) <= Constants.MAX_ERROR_TRANSFORM_POW2 
			&& controlr.distanceSquared(end) <= Constants.MAX_ERROR_TRANSFORM_POW2 )
		{
			Vec nstart = t.to(start);
			Vec nend = t.to(end);
			if(nstart.distanceSquared(nend) <= Constants.MAX_ERROR_TRANSFORM_POW2){
				res.add(new Line(nstart,nend));
				return;
			} 
		}
		Vec controlll = start.between(controll);
		Vec controlrr = controlr.between(end);
		Vec inter = controll.between(controlr);
		Vec controllr = controlll.between(inter);
		Vec controlrl = inter.between(controlrr);
		Vec middle = controllr.between(controlrl);
		new Cubic(start,controlll,controllr,middle).getSimpleLines(t,res);
		new Cubic(middle, controlrl, controlrr, end).getSimpleLines(t,res);
	}

	@Override
	public Path transformAffine(Transform t) {
		return new Cubic(t.to(start), t.to(controll), t.to(controlr), t.to(end));
	}

	@Override
	public
	void getSegments(List<Segment> res) {
		res.add(new CubicTo(controll,controlr,end));
		
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
