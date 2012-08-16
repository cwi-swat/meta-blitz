package deform;

import static deform.Combinators.*;

import java.awt.BasicStroke;
import java.awt.Font;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.awt.font.LineMetrics;
import java.awt.geom.PathIterator;
import java.util.ArrayList;
import java.util.List;


import deform.paths.Path;
import deform.segments.Segment;
import deform.segments.ShapesMaker;
import deform.shapes.Shape;
import deform.shapes.ShapeSet;
import deform.shapes.StrokedPath;
import deform.shapes.StrokedShape;
import deform.texture.FillColor;
import deform.texture.ImageTex;
import deform.texture.RepeatingImage;
import deform.texture.gradients.HorCycleMultipleGradient;
import deform.texture.gradients.HorCyclicGradient;
import deform.texture.gradients.HorGradient;
import deform.texture.gradients.HorNoCycleMultipleGradient;
import deform.texture.gradients.HorNonCyclicGradient;
import deform.texture.gradients.HorReflectMultipleGradient;
import deform.texture.gradients.RadialCyclicGradient;
import deform.texture.gradients.RadialNonCyclicGradient;
import deform.texture.gradients.RadialReflectGradient;
import deform.texturedshape.Over;
import deform.texturedshape.TexturedShape;
import deform.transform.ConeEye;
import deform.transform.Fisheye;
import deform.transform.affine.AffineTransform;
import deform.transform.affine.IdentityTransform;
import deform.transform.sweep.Sweep;

import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;

import javax.swing.UIManager;

import paths.paths.factory.CircleFactory;
public class Library {

	
	private static final int DefaultFontSize = 16;

	public static Vec vec(double x, double y){
		return new Vec(x, y);
	}
	
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
	
	public static Transform coneLens(Vec center,  double maxdist){
		return new ConeEye(center,maxdist);
	}
	
	public static Shape stroke(Path p, double width){
		return new StrokedPath(p,width,new BasicStroke((float)width));
	}
	
	public static Shape stroke(Shape p,double width){
		return new StrokedShape(p,width,new BasicStroke((float)width));
	}
	
	public static Path arc(double startAngle, double endAngle){
		return CircleFactory.makeArc(startAngle, endAngle);
	}
	
	public static Path arc(Vec center,double startAngle, double diameter, double endAngle){
		return CircleFactory.makeCircularArc(center, diameter, startAngle, endAngle);
	}
	
	public static Path ellipticalArc(Vec center,double width, double height,double startAngle, double endAngle){
		return CircleFactory.makeEllipiticalArc(center, width, height, startAngle, endAngle);
	}
	
	public static Shape circle(){
		return CircleFactory.makeCircle();
	}
	
	public static Shape rectangle(){
		return close(path(-1,-1,lineTo(1,-1),lineTo(1,1),lineTo(-1,1),lineTo(-1,-1)));
	}
	
	public static Texture fillColor(Color c){
		return new FillColor(c);
	}
	
	public static TexturedShape over(TexturedShape ... ts){
		return new Over(ts);
	}
	
	public static Shape text(String text, int fontSize) {
		return text(defaultFontName(), fontSize, text);
	}
	
	public static Shape text(String text) {
		return text(defaultFontName(),  DefaultFontSize, text);
	}

	private static String defaultFontName() {
		return UIManager.getDefaults().getFont("Label.font").getName();
	}
	
	public static Shape text(String font,int fontSize, String text) {
		Font f = new Font(font, Font.PLAIN, fontSize);
		FontRenderContext ctx = new FontRenderContext(null, true, true);
		LineMetrics lm = f.getLineMetrics(text, ctx);
		double height = lm.getHeight();
		String[] lines = text.split("\\n");
		int i = 0;
		List<Shape> shapes = new ArrayList<Shape>();
		for(String line : lines){
			if(line.trim().isEmpty()){
				continue;
			}
			GlyphVector v = f.createGlyphVector(ctx, line);
			java.awt.Shape res = v.getOutline();
			shapes.add(
					transform(translate(0,i*height),ShapesMaker.fromJava2dToShape(res.getPathIterator(null))));
			i++;
			}
		return Combinators.set(shapes);
		
		
	
}

	private static java.awt.Shape textSingleLine(String font, int fontSize,
			String text) {
		Font f = new Font(font, Font.PLAIN, fontSize);
		FontRenderContext ctx = new FontRenderContext(null, true, true);
		GlyphVector v = f.createGlyphVector(ctx, text);
		java.awt.Shape res = v.getOutline();
		return res;
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
	
	public static Texture horGradient(Color ... colors){
		ColorAndFraction[] cfs = new ColorAndFraction[colors.length+1];
		for(int i = 0 ; i < colors.length ; i++){
			cfs[i] = new ColorAndFraction((double)i/colors.length, colors[i]);
		}
		cfs[colors.length] = new ColorAndFraction(1.0, colors[0]);
		return horGradient(cfs);
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
	
	public static Texture image(String path){
		return new ImageTex(path);
	}
	
	public static Texture tiledImage(String path){
		return new RepeatingImage(path);
	}
	
	public static Transform sweep(Path p){
		return new Sweep(p);
	}
	
	public static Shape sweep(Shape s, Path p){
		Vec trans = s.bbox.getLeftUp().negate();
		trans = new Vec(trans.x,trans.y - s.bbox.height()/2);
		s = transform(scale(length(p)/s.bbox.width()).compose(translate(trans)),s);
		return transform(sweep(p),s);
	}
	
	public static TexturedShape sweep(TexturedShape s, Path p){
		Vec trans = s.getBBox().getLeftUp().negate();
		trans = new Vec(trans.x,trans.y - s.getBBox().height()/2);
		s = transform(scale(length(p)/s.getBBox().width()).compose(translate(trans)),s);
		return transform(sweep(p),s);
	}
	
	public static double length(Path p){
		return p.normalise().toQueryPath().length();
	}
	
	
	public static Texture fillColor(int i, int j, int k) {
		return fillColor(color(i,j,k));
	}
	

	public static Texture fillColor(int i, int j, int k, int l) {
		return fillColor(color(i,j,k,l));
	}
}
