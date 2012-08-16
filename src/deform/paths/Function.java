package deform.paths;

import java.util.ArrayList;
import java.util.List;

import paths.Constants;
import paths.paths.paths.QueryPath;
import deform.BBox;
import deform.IFunction;
import deform.Transform;
import deform.Vec;
import deform.library.BezierInterpolator;
import deform.library.BezierPoint;
import deform.library.CurveFit;
import deform.segments.SegmentsMaker;
import deform.transform.affine.IdentityTransform;

public class Function extends Path{

	final IFunction f;
	
	public Function(IFunction f) {
		super(f.getBBox());
		this.f = f;
	}

	private void renderSubTo(Transform t, double s, double e, Vec fromStart, Vec fromEnd, Vec toStart, Vec toEnd ,List<Vec> res) {
		if((e -s) < 0.5 && toStart.distanceSquared(toEnd) <= Constants.MAX_ERROR_CONTINOUS_POW2){
			res.add(toStart);
		} else {
			double pmid = (s + e)/2.0;
			
			Vec mid = f.getAt(pmid);
			Vec midTo = t.to(mid);
			renderSubTo(t, s, pmid, fromStart,mid, toStart,midTo,res);
			renderSubTo(t,pmid,e, mid,fromEnd, midTo,toEnd,res);
		}
		
	}
	
	@Override
	void render(BBox area, Transform t, SegmentsMaker res) {
		Vec fromStart = f.getAt(0);
		Vec fromEnd = f.getAt(1);
		Vec toEnd = t.to(fromEnd);
		ArrayList<Vec> ps = new ArrayList<Vec>();
		renderSubTo(t, 0, 1, fromStart, fromEnd, t.to(fromStart), toEnd, ps);
		ps.add(toEnd);
		for(int i = 1 ; i < ps.size() ; i+=2){
//			System.out.println(ps.get(i));
			res.line(ps.get(i-1),ps.get(i));
		}

//		List<Vec> vs = CurveFit.fitCurve(ps, Constants.MAX_ERROR_APPROX_CUBIC_POW2);
////		System.out.printf("%d -> %d\n",ps.size(), vs.size());
//		for(int i = 1 ; i < vs.size() ; i+=3){
//			res.cubic(vs.get(i-1), vs.get(i), vs.get(i+1), vs.get(i+2));
//		}
		
	}

	@Override
	public QueryPath toQueryPath() {
		SegmentsMaker m = new SegmentsMaker();
		render(BBox.everything,IdentityTransform.Instance, m);
		return m.done().toPath().toQueryPath();
	}

}
