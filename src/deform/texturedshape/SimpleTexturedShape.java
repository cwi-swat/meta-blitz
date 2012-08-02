package deform.texturedshape;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
import java.util.ArrayList;
import java.util.List;



import deform.BBox;
import deform.Color;
import deform.Combinators;
import deform.Texture;
import deform.Transform;
import deform.render.RenderContext;
import deform.render.ScanLiner;
import deform.segments.SegPath;
import deform.segments.ShapesMaker;
import deform.shapes.IntersectionShapes;
import deform.shapes.Shape;
import deform.tests.BasicDemo;
import deform.texture.ImageTex;
import deform.texture.Java2DTexture;
import deform.texture.RepeatingImage;
import deform.texture.TransformTexture;
import deform.transform.affine.AffineTransform;
import deform.transform.affine.IdentityTransform;

public class SimpleTexturedShape extends TexturedShape{
	final Texture tex;
	final Shape shape;
	
	public SimpleTexturedShape(Texture tex, Shape shape) {
		this.tex = tex;
		this.shape = shape;
	}
	

	
	public void render(Transform t,RenderContext ctx) {
		BBox me = t.transformBBox(shape.bbox);
		if(!me.overlaps(ctx.area)){
			return;
		}
		Shape shape = this.shape;
		BBox actual = ctx.area.intersections(me);
		if(tex instanceof ImageTex && t instanceof AffineTransform){
			ctx.renderImage(((ImageTex)tex).i,(AffineTransform)t,shape);
			return;
		} else {
			if( tex instanceof Java2DTexture && t instanceof AffineTransform){
				ctx.renderJava2dPaintShape(((Java2DTexture)tex).getPaint(), (AffineTransform)t, shape);
				return;
			} else {
				Texture tex = Combinators.transform(t, this.tex);
				ctx.renderShapeOutline(t, shape);
				setPixels(ctx, actual, tex);
			}
		}
	}

	void setPixels(final RenderContext ctx, BBox actual, final Texture tex) {
		setPixelsReal(ctx, actual, tex);
	}



	static void setPixelsReal(RenderContext ctx, BBox actual, Texture tex) {
		ScanLiner it = new ScanLiner(ctx.size, actual);
		while(!it.isDone()){
			int alpha = ctx.getAlpha(it.curFill);
	
			if(alpha!= 0){
				Color c= tex.sample(new deform.Vec(it.getRealX() + 0.5, it.getRealY() + 0.5)).mul(alpha);
				
				ctx.addElem(it.cur, c);
			}
			it.increment();
		}
	}

	@Override
	public BBox getBBox() {
		return shape.bbox;
	}

	
	

}
