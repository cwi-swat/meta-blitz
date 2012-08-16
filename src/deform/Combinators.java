package deform;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.imageio.ImageIO;

import paths.points.oned.Interval;

import deform.paths.Append;
import deform.paths.Function;
import deform.paths.Path;
import deform.paths.TransformPath;
import deform.render.RenderContext;
import deform.segments.CubicTo;
import deform.segments.LineTo;
import deform.segments.QuadTo;
import deform.segments.SegPath;
import deform.segments.Segment;
import deform.shapes.Closed;
import deform.shapes.IntersectionShapes;
import deform.shapes.MinusShapes;
import deform.shapes.Shape;
import deform.shapes.ShapeSet;
import deform.shapes.SymDiffShapes;
import deform.shapes.TransformShape;
import deform.shapes.UnionShapes;
import deform.texture.AffineTransformableTex;
import deform.texture.CombineTex;
import deform.texture.FillColor;
import deform.texture.TransformTexture;
import deform.texturedshape.CombineTexturedShape;
import deform.texturedshape.IntersectionTexturedShape;
import deform.texturedshape.MemoTexturedShape;
import deform.texturedshape.MinusTexturedShape;
import deform.texturedshape.ParallelTexturedShape;
import deform.texturedshape.SimpleTexturedShape;
import deform.texturedshape.TexturedShape;
import deform.texturedshape.TransformTexturedShape;
import deform.transform.CompositeTransform;
import deform.transform.TransformedTransform;
import deform.transform.affine.AffineTransform;
import deform.transform.affine.IdentityTransform;

public class Combinators {
	
	public static Color color(int r, int g, int b){
		return Color.color(r, g, b);
	}
	
	public static Color color(int r, int g , int b, int a){
		return Color.color(r, g, b, a);
	}
	
	public static Segment lineTo(Vec to){
		return new LineTo(to);
	}
	
	public static Segment quadTo(Vec control, Vec to){
		return new QuadTo(control, to);
	}
	
	public static Segment cubicTo(Vec control1, Vec control2, Vec to){
		return new CubicTo(control1, control2, to);
	}
	
	public static Path funcPath(IFunction f){
		return new Function(f);
	}
	
	public static Path path(Vec start, Segment ... segs){
		return new SegPath(start,segs).toPath();
	}
	
	public static Path append(Path a, Path b){
		return new Append(a,b);
	}

	public static Shape close(Path p){
		return new Closed(p);
	}
	

	public static Shape set(List<Shape> shapes) {
		List<Shape> res = new ArrayList<Shape>();
		for(Shape s : shapes){
			if(s instanceof ShapeSet){
				res.addAll(((ShapeSet)s).closed);
			} else {
				res.add(s);
			}
		}
		return new ShapeSet(res);
	}
	
	public static Shape set(Shape ... shapes){
		return set(Arrays.asList(shapes));
	}
	
	public static Shape union(Shape a, Shape b){
		return new UnionShapes(a,b) ;
	}
	
	public static Shape intersection(Shape a, Shape b){
		return new IntersectionShapes(a,b) ;
	}
	
	
	public static TexturedShape intersection(TexturedShape a, Shape b){
		return new IntersectionTexturedShape(a,b) ;
	}
	
	public static TexturedShape intersection(Shape b,TexturedShape a){
		return new IntersectionTexturedShape(a,b) ;
	}
	
	public static Shape minus(Shape a, Shape b){
		return new MinusShapes(a,b) ;
	}
	
	public static TexturedShape minus(TexturedShape a, Shape b){
		return new MinusTexturedShape(a,b) ;
	}
	
	
	public static TexturedShape minus(Shape b, TexturedShape a){
		return new MinusTexturedShape(a,b) ;
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
	
	public static TexturedShape fillPar(int blockSize,Shape shape, Texture tex){
		return new ParallelTexturedShape(blockSize,tex, shape);
	}
	
	public static Path transform(Transform t, Path p){
		return new TransformPath(p, t);
	}
	
	public static Shape transform(Transform t, Shape p){
		return new TransformShape(p, t);
	}
	
	public static TexturedShape memo(TexturedShape s){
		return new MemoTexturedShape(s);
	}
	
	public static Transform transform(Transform t, Transform arg){
		return new TransformedTransform(t, arg);
	}
	
	public static Texture transform(Transform t, Texture tex){
		if(t instanceof IdentityTransform){
			return tex;
		}
		if(t instanceof AffineTransform && tex instanceof AffineTransformableTex){
			return ((AffineTransformableTex)tex).transform(t);
		} else if(tex instanceof FillColor){
			return tex;
		}
		return new TransformTexture(t,tex);
	}
	
	public static TexturedShape transform(Transform t, TexturedShape s){
		return new TransformTexturedShape(s,t);
	}
	
	public static TexturedShape combine(ColorCombine comb, TexturedShape a, TexturedShape b){
		return new CombineTexturedShape(a,b,comb);
	}
	
	public static void render(int width , int height, String file, TexturedShape s){
		BBox b = new BBox(0,0,width,height);
		
		RenderContext ctx = new RenderContext(b, null);
		Graphics2D g = (Graphics2D) ctx.getImage().getGraphics();
		g.setBackground(java.awt.Color.white);
		g.clearRect(0, 0, width, height);
		s.render(IdentityTransform.Instance,ctx);
		try {
			ImageIO.write(ctx.getImage(), "png", new File(file + ".png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	
	public static void render(BBox b, Graphics g, TexturedShape s){
		RenderContext ctx = new RenderContext(b, null);
		s.render(IdentityTransform.Instance,ctx);
        g.drawImage(ctx.getImage(), 0, 0,null);
	}

	static final ExecutorService threadPool = Executors.newFixedThreadPool(16);
	
	static class RenderJob implements Runnable{
		final Graphics g;
		final BBox b;
		final TexturedShape s;

		public RenderJob(Graphics g, BBox b, TexturedShape s) {
			this.g = g;
			this.b = b;
			this.s = s;
		}

		@Override
		public void run() {
			RenderContext ctx = new RenderContext(b, null);
			s.render(IdentityTransform.Instance, ctx);
			if(!g.drawImage(ctx.getImage(), b.getXInt(), b.getYInt(),null)){
				System.out.println("HUH!!");
			}
//			System.out.println(b);
		}
		
	}
	
	public static void renderPar(int nrJobsX, int nrJobsY, BBox b, Graphics g, TexturedShape s){
		int jobWidth = b.getWidthInt()/nrJobsX;
		int jobHeight = b.getHeightInt()/nrJobsY;
		List<Future> futures = new ArrayList<Future>();
		for(int x = 0 ; x< nrJobsX ; x++){
			for(int y = 0 ; y < nrJobsY; y++){
				int xLU = x * jobWidth + b.getXInt();
				int yLU = y * jobHeight + b.getYInt();
				int xRD = x == nrJobsX - 1? b.getWidthInt() + b.getXInt() : xLU + jobWidth;
				int yRD = y == nrJobsY - 1? b.getHeightInt() + b.getYInt() : yLU + jobHeight;
				BBox r = new BBox(xLU, yLU, xRD, yRD);
				
				futures.add(threadPool.submit(new RenderJob(g,r,s)));
			}
		}
		
		for(Future f : futures){
			try {
				f.get();
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			}
		}
	}

	
}
