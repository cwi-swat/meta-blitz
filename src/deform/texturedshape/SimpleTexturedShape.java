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
import deform.render.LocatedImage;
import deform.render.RenderContext;
import deform.render.ScanLiner;
import deform.segments.SegPath;
import deform.segments.ShapesMaker;
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
		BBox actual = ctx.area.intersections(me);
		if(tex instanceof ImageTex && t instanceof AffineTransform){
			ctx.renderImage(((ImageTex)tex).i,(AffineTransform)t,shape);
			return;
		}
		boolean java2dPaint ;
		Texture tex = this.tex;
		if(tex instanceof RepeatingImage && t instanceof AffineTransform && ((AffineTransform)t).isTranslation()){

			ctx.setPaint(((RepeatingImage)tex).getTranslatedPaint(t));
			java2dPaint = true;
		} else {
			tex = Combinators.transform(t, this.tex);
			
			java2dPaint =  tex instanceof Java2DTexture;
			
			if(java2dPaint){
				ctx.setPaint(((Java2DTexture)tex).getPaint());
			} else {
				ctx.setPaint(java.awt.Color.white);
			}
		}

		if(!java2dPaint){
			ctx.setShape(t, shape);
			ScanLiner it = new ScanLiner(ctx.area, actual);

			int toX = actual.getXInt() + actual.getWidthInt();
			int toY = actual.getYInt() + actual.getHeightInt();
			for (int iy = actual.getYInt(); iy < toY; iy++) {
				for (int ix = actual.getXInt(); ix < toX; ix++) {
					int alpha = ctx.getAlpha(it.curFill);

					if(alpha!= 0){
						Color c= tex.sample(new deform.Vec(ix + 0.5, iy + 0.5)).mul(alpha);
						
						ctx.addElem(it.cur, c);
					}
					it.increment();
				}
			}
		} else {
			ctx.directShape(t,shape);
		}
	}

	@Override
	public BBox getBBox() {
		return shape.bbox;
	}

	@Override
	public boolean isJava2DRenderable() {
		return tex instanceof Java2DTexture;
	}
	
	
	

}
