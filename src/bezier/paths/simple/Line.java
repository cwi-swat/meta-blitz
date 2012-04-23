package bezier.paths.simple;

import java.awt.geom.PathIterator;
import java.util.List;

import bezier.paths.ApproxCurvePosition;
import bezier.paths.ConnectedPath;
import bezier.paths.Intersection;
import bezier.paths.Path;
import bezier.paths.factory.PathFactory;
import bezier.paths.util.ITransform;
import bezier.paths.util.PathParameter;
import bezier.paths.util.TPair;
import bezier.points.Vec;
import bezier.util.BBox;
import bezier.util.STuple;
import bezier.util.Util;

public final class Line extends SimplePath{


	@Override
	public STuple<List<ApproxCurvePosition>> intersectionsLine(Line other) {
		TPair t = intersection(other);
		if(t != null){
			res.add(new Intersection(
					new ApproxCurvePosition(this, t.tl, lParent),
					new ApproxCurvePosition(other, t.tr, rParent)
					)
			);
		}
	}

	@Override
	public STuple<List<ApproxCurvePosition>> intersectionsSimple(SimplePath other) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public STuple<List<ApproxCurvePosition>> intersections(Path other) {
		return other.intersectionsLine(this);
	}


	
	
	
}
