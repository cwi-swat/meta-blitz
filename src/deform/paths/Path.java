package deform.paths;

import java.util.ArrayList;
import java.util.List;


import deform.BBox;
import deform.Transform;
import deform.Vec;
import deform.segments.SegPath;
import deform.segments.Segment;

public abstract class Path {

	protected BBox bbox;

	public abstract BBox makeBBox();

	public BBox getBBox() {
		if (bbox == null) {
			bbox = makeBBox();
		}
		return bbox;
	}
	
	abstract void getSimpleLines(Transform t,List<Path> res);
	
	public abstract Path transformAffine(Transform t);
	
	public Path transform(Transform t){
		if(t.isAffine()){
			return transformAffine(t);
		} else {
			List<Path> res = new ArrayList<Path>();
			getSimpleLines(t,res);
			return Append.createAppends(res);
		}
	}
	
	
	public abstract void getSegments(List<Segment> res);
	

	public abstract Vec getStart();
	public abstract Vec getEnd();
	
}
