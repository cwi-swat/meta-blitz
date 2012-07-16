package deform.paths;

import java.util.List;

import deform.BBox;
import deform.Transform;
import deform.Vec;
import deform.segments.Segment;

public class TransformedPath extends Path{
	final Path p;
	final Transform t;
	private Path trans;
	
	public TransformedPath(Path p, Transform t) {
		this.p = p;
		this.t = t;
	}

	@Override
	public BBox makeBBox() {
		if(t.isAffine()){
			BBox b = p.getBBox();
			return BBox.from4Points(t.to(b.getLeftDown()),
					t.to(b.getLeftUp()),
					t.to(b.getRightDown()),
					t.to(b.getRightUp()));
		} else {
			if(t.affectedArea().encloses(BBox.everything)){
				return BBox.everything;
			} else {
				if(t.affectedArea().overlaps(p.getBBox())){
					return t.affectedArea().union(p.getBBox());
				} else {
					return p.getBBox();
				}
			}
		}
	}
	
	private void makeTrans(){
		if(trans == null){
			if(t.affectedArea().overlaps(p.getBBox())){
				trans = p.transform(t);
			} else {
				trans = p;
			}
		}
	}

	@Override
	void getSimpleLines(Transform t, List<Path> res) {
		makeTrans();
		trans.getSimpleLines(t, res);
	}

	@Override
	public Path transformAffine(Transform t) {
		makeTrans();
		return trans.transformAffine(t);
	}

	@Override
	public
	void getSegments(List<Segment> res) {
		makeTrans();
		trans.getSegments(res);
		
	}

	@Override
	public Vec getStart() {
		makeTrans();
		return trans.getStart();
	}

	@Override
	public Vec getEnd() {
		makeTrans();
		return trans.getEnd();
	}
	
}
