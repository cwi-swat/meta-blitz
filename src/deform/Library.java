package deform;

import static deform.Combinators.*;
import deform.paths.Path;
import deform.segments.Segment;
import deform.transform.Fisheye;
import deform.transform.Translate;

public class Library {

	
	public static Segment lineTo(double x, double y){
		return deform.Combinators.lineTo(new Vec(x,y));
	}
	
	public static Segment quadTo(double cx, double cy, double x, double y){
		return deform.Combinators.quadTo(new Vec(cx,cy),new Vec(x,y));
	}
	
	public static Segment cubicTo(double lx, double ly, double cx, double cy, double x, double y){
		return deform.Combinators.cubicTo(new Vec(lx,ly), new Vec(cx,cy),new Vec(x,y));
	}
	
	public static Path path(double x, double y, Segment ... segs){
		return deform.Combinators.path(new Vec(x,y), segs);
	}
	
	public static Transform translate(Vec v){
		return new Translate(v);
	}
	
	public static Transform fisheye(Vec center, double mag, double maxdist){
		return new Fisheye(center,mag,maxdist);
	}
}
