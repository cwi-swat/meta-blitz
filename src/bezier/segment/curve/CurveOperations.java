package bezier.segment.curve;

import java.util.ArrayList;
import java.util.List;

import bezier.segment.TPair;
import bezier.util.Tuple;

public final class CurveOperations {

	
	public static void intersections(Curve l, TInterval li, Curve r, TInterval ri, List<TPair> result){
		boolean leftLine = l instanceof Line;
		boolean rightLine = r instanceof Line;
		if(l.isLine() && r.isLine()){
			TPair res = l.getLine().intersection(r.getLine());
			if(res != null){
				result.add(new TPair(li.convertBack(res.tl),ri.convertBack(res.tr)));
			}
		} else if(leftLine || (!leftLine && !rightLine && l.getBBox().diagonalLengthSquared() > r.getBBox().diagonalLengthSquared())){
			if(l.fastIntersectionCheck(r)){
				Tuple<Curve,Curve> sr = r.splitSimpler();
				Tuple<TInterval,TInterval> sri = ri.split();
				intersections(l, li, sr.l, sri.l, result);
				intersections(l, li, sr.r, sri.r, result);
			}
		} else{
			if(l.fastIntersectionCheck(r)){
				Tuple<Curve,Curve> sl = l.splitSimpler();
				Tuple<TInterval,TInterval> sli = li.split();
				intersections(sl.l, sli.l,r,ri, result);
				intersections(sl.r, sli.r,r,ri, result);
			}
		}
	}

	public static List<TPair> intersections(Curve p, Curve q) {
		List<TPair> result = new ArrayList<TPair>();
		intersections(p, new TInterval(0, 1), q, new TInterval(0, 1),result);
		return result;
	}

}
