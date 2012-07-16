package deform.segments;

import java.awt.geom.Path2D;
import java.awt.geom.PathIterator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


import deform.Vec;
import deform.paths.Append;
import deform.paths.Path;

public class SegPath {
	Vec start;
	List<Segment> segs;

	SegPath(){
		segs = new ArrayList<Segment>();
	}
	
	public SegPath(Vec start, Segment[] segs){
		this.start = start;
		this.segs = Arrays.asList(segs);
	}
	
	public Path toPath(){
		List<Path> res = new ArrayList<Path>(segs.size());
		Vec prev= start;
		for(Segment s : segs){
			res.add(s.make(prev));
			prev= s.to;
		}
		return Append.createAppends(res);
	}
	
	public Path2D toJava2d(){
		Path2D res = new Path2D.Double();
		res.moveTo(start.x,start.y);
		for(Segment s : segs){
			s.makeAWT(res);
		}
		return res;
	}
	
	
	public static SegPath fromPath(Path p){
		SegPath res = new SegPath();
		res.start = p.getStart();
		p.getSegments(res.segs);
		return res;
	}
	public static SegPath fromJava2d(Path2D p){
		PathIterator it = p.getPathIterator(null);
		return fromJava2d(it);
	}
	
	public static SegPath fromJava2d(PathIterator it){
		SegPath res = new SegPath();
		double[] coords = new double[6];
		while(!it.isDone()){
			switch(it.currentSegment(coords)){
			case PathIterator.SEG_MOVETO: 
				res.start = new Vec(coords[0],coords[1]); break;
			case PathIterator.SEG_LINETO: 
				res.segs.add(new LineTo(new Vec(coords[0],coords[1]))); break;
			case PathIterator.SEG_QUADTO: 
				res.segs.add(new QuadTo(new Vec(coords[0],coords[1]),new Vec(coords[2],coords[3]))); break;
			case PathIterator.SEG_CUBICTO: 
				res.segs.add(new CubicTo(new Vec(coords[0],coords[1]),
						new Vec(coords[2],coords[3]),new Vec(coords[4],coords[5]))); break;
			case PathIterator.SEG_CLOSE: return res;
			default: throw new Error("Unkown segment constant!");
			}
		}
		throw new Error("Not closed?");
	}
}
