package deform;

import static deform.Combinators.*;

import java.awt.BasicStroke;

import deform.paths.Path;
import deform.segments.Segment;
import deform.shapes.Shape;
import deform.shapes.StrokedPath;
import deform.shapes.StrokedShape;
import deform.texture.FillColor;
import deform.texture.gradients.HorCycleMultipleGradient;
import deform.texture.gradients.HorCyclicGradient;
import deform.texture.gradients.HorGradient;
import deform.texture.gradients.HorNoCycleMultipleGradient;
import deform.texture.gradients.HorNonCyclicGradient;
import deform.texture.gradients.HorReflectMultipleGradient;
import deform.texture.gradients.RadialCyclicGradient;
import deform.texture.gradients.RadialNonCyclicGradient;
import deform.texture.gradients.RadialReflectGradient;
import deform.transform.Fisheye;
import deform.transform.affine.AffineTransform;
import deform.transform.affine.IdentityTransform;

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
		return AffineTransform.translate(v);
	}
	

	public static Transform translate(double x, double y) {
		return translate(new Vec(x,y));
	}
	
	public static Transform scale(double x, double y){
		return AffineTransform.scale(x, y);
	}
	
	public static Transform scale(double s){
		return scale(s,s);
	}
	
	public static Transform rotate(double rads){
		return AffineTransform.rotate(rads);
	}
	
	public static Transform shear(double x, double y){
		return AffineTransform.shear(x,y);
	}
	
	public static Transform fisheye(Vec center, double mag, double maxdist){
		return new Fisheye(center,mag,maxdist);
	}
	
	public static Shape stroke(Path p, double width){
		return new StrokedPath(p,width,new BasicStroke((float)width));
	}
	
	public static Shape stroke(Shape p,double width){
		return new StrokedShape(p,width,new BasicStroke((float)width));
	}
	
	public static Texture fillColor(Color c){
		return new FillColor(c);
	}
	
	// a gradient of width 1
	public static Texture horGradient(Color a, Color b){
		return new HorNonCyclicGradient(a,b, IdentityTransform.Instance);
	}
	
	// a gradient of width 1
	public static Texture horCyclicGradient(Color a, Color b){
		return new HorCyclicGradient(a,b, IdentityTransform.Instance);
	}
	
	public static enum GradientCycleMethod{
		NoCycle,
		Reflect,
		Cycle;
	}
	
	public static class ColorAndFraction{
		public final Color color;
		public final double fraction;
		public ColorAndFraction(double fraction,Color color) {
			this.color = color;
			this.fraction = fraction;
		}
	}
	
	public static Texture horGradient(ColorAndFraction ... cfs){
		return horGradient(GradientCycleMethod.Reflect, cfs);
	}
	
	public static Texture horGradient(GradientCycleMethod cycle, ColorAndFraction ... cfs){
		switch(cycle){
		case NoCycle: return new HorNoCycleMultipleGradient(cfs, IdentityTransform.Instance );
		case Reflect: return new HorReflectMultipleGradient(cfs, IdentityTransform.Instance );
		case Cycle: return new HorCycleMultipleGradient(cfs, IdentityTransform.Instance );
		}
		throw new Error("Unkown cycle type!");
	}
	
	public static Texture radialGradient(ColorAndFraction ... cfs){
		return radialGradient(GradientCycleMethod.Reflect, cfs);
	}
	
	public static Texture radialGradient(GradientCycleMethod cycle, ColorAndFraction ... cfs){
		switch(cycle){
		case NoCycle: return new RadialNonCyclicGradient(cfs, IdentityTransform.Instance );
		case Reflect: return new RadialReflectGradient(cfs, IdentityTransform.Instance );
		case Cycle: return new RadialCyclicGradient(cfs, IdentityTransform.Instance );
		}
		throw new Error("Unkown cycle type!");
	}
}
