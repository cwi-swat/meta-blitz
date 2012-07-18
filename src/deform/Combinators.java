package deform;

import java.awt.Graphics2D;

import deform.paths.Path;
import deform.paths.TransformPath;
import deform.segments.CubicTo;
import deform.segments.LineTo;
import deform.segments.QuadTo;
import deform.segments.SegPath;
import deform.segments.Segment;
import deform.shapes.Closed;
import deform.shapes.IntersectionShapes;
import deform.shapes.MinusShapes;
import deform.shapes.Shape;
import deform.shapes.SymDiffShapes;
import deform.shapes.TransformShape;
import deform.shapes.UnionShapes;
import deform.texture.AffineTransformableTex;
import deform.texture.CombineTex;
import deform.texture.TransformTexture;
import deform.texturedshape.CombineTexturedShape;
import deform.texturedshape.LocatedImage;
import deform.texturedshape.SimpleTexturedShape;
import deform.texturedshape.TexturedShape;
import deform.texturedshape.TransformTexturedShape;
import deform.transform.affine.AffineTransform;
import deform.transform.affine.IdentityTransform;

public class Combinators {
	
	public static Segment lineTo(Vec to){
		return new LineTo(to);
	}
	
	public static Segment quadTo(Vec control, Vec to){
		return new QuadTo(control, to);
	}
	
	public static Segment cubicTo(Vec control1, Vec control2, Vec to){
		return new CubicTo(control1, control2, to);
	}
	
	public static Path path(Vec start, Segment ... segs){
		return new SegPath(start,segs).toPath();
	}

	public static Shape close(Path p){
		return new Closed(p);
	}
	
	public static Shape union(Shape a, Shape b){
		return new UnionShapes(a,b) ;
	}
	
	public static Shape intersection(Shape a, Shape b){
		return new IntersectionShapes(a,b) ;
	}
	
	public static Shape minus(Shape a, Shape b){
		return new MinusShapes(a,b) ;
	}
	
	public static Shape symdiff(Shape a, Shape b){
		return new SymDiffShapes(a,b) ;
	}
	
	public static Texture combine(ColorCombine comb, Texture a, Texture b){
		return new CombineTex(comb,a,b);
	}
	
	public static TexturedShape fill(Shape shape, Texture tex){
		return new SimpleTexturedShape(tex, shape);
	}
	
	public static Path transform(Transform t, Path p){
		return new TransformPath(p, t);
	}
	
	public static Shape transform(Transform t, Shape p){
		return new TransformShape(p, t);
	}
	
	public static Texture transform(Transform t, Texture tex){
		if(t instanceof AffineTransform && tex instanceof AffineTransformableTex){
			return ((AffineTransformableTex)tex).transform(t);
		}
		return new TransformTexture(t,tex);
	}
	
	public static TexturedShape transform(Transform t, TexturedShape s){
		return new TransformTexturedShape(s,t);
	}
	
	public static TexturedShape combine(ColorCombine comb, TexturedShape a, TexturedShape b){
		return new CombineTexturedShape(a,b,comb);
	}
	
	public static void render(Graphics2D g, BBox b, TexturedShape s){
		LocatedImage i = s.render(IdentityTransform.Instance, b);
		i.draw(g);
	}
	
}
