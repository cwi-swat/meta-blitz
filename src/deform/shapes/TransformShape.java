package deform.shapes;

import java.util.List;

import deform.BBox;
import deform.Transform;
import deform.segments.SegPath;

public class TransformShape extends Shape{

	final Shape s;
	final Transform t;
	
	public TransformShape(Shape s, Transform t) {
		super(t.transformBBox(s.bbox));
		this.s = s;
		this.t = t;
	}



	@Override
	public
	void render(BBox area, Transform t, List<SegPath> res) {

		if(!area.overlaps(t.transformBBox(bbox))){
//			System.out.printf("Hiero! %s\n %s \n %s\n", area, bbox, t.transformBBox(bbox));
			return;
		} else {
			s.render(area, t.compose(this.t), res);
		}
		
	}



	@Override
	public String toString() {
		return "TransformShape [s=" + s + ", t=" + t + "]";
	}

}
