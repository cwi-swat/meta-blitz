package deform.transform;

import deform.BBox;
import deform.Transform;

public class TransformBBox {
	public static BBox transformBBox(Transform t, BBox b){
		if(t.isAffine()){
			return BBox.from4Points(t.to(b.getLeftDown()),
					t.to(b.getLeftUp()),
					t.to(b.getRightDown()),
					t.to(b.getRightUp()));
		} else {
			if(t.affectedArea().encloses(BBox.everything)){
				return BBox.everything;
			} else {
				if(t.affectedArea().overlaps(b)){
					return t.affectedArea().union(b);
				} else {
					return b;
				}
			}
		}
	}
}
