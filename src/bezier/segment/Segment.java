package bezier.segment;

import java.util.ArrayList;
import java.util.List;

import bezier.points.Vec;
import bezier.segment.curve.Curve;

public class Segment {

	public List<Curve> curves;
	
	public Segment(List<Curve> curves){
		assert curves.size() != 0;
		this.curves = curves;
	}
	
	public Vec getFirst(){
		return curves.get(0).getStartPoint();
	}
	
	public Vec getLast(){
		return curves.get(curves.size()-1).getEndPoint();
	}
	
	public Segment reverse(){
		List<Curve> reversed = new ArrayList<Curve>(curves.size());
		for(int i = curves.size()-1; i >= 0; i--){
			reversed.add(curves.get(i).reverse());
		}
		return new Segment(reversed);
	}
	
	public Segment merge(Segment r){
		List<Curve> merged = new ArrayList<Curve>(curves.size() + r.curves.size());
		merged.addAll(curves);
		merged.addAll(r.curves);
		return new Segment(merged);
	}
	
	public boolean connectsWith(Segment seg){
		return getLast().isEqError(seg.getFirst());
	}
	
	public boolean connectWithReversed(Segment seg){
		return getLast().isEqError(seg.getLast());
	}
	

	public String toString(){
		StringBuffer b = new StringBuffer();
		b.append("Segment:\n");
		for(Curve c : curves){
			b.append(c.toString());
			b.append('\n');
		}
		b.append('\n');
		return b.toString();
	}
	
	
	
}
