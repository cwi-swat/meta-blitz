package deform.texturedshape;

import java.awt.image.BufferedImage;

import deform.BBox;
import deform.Texture;
import deform.Transform;
import deform.Vec;
import deform.render.RenderContext;
import deform.shapes.Shape;
import deform.transform.affine.AffineTransform;
import deform.transform.affine.IdentityTransform;
import deform.transform.affine.Matrix;

public class MemoTexturedShape extends TexturedShape{

	final TexturedShape actual;
	BufferedImage img;
	BBox prevArea;
	
	public MemoTexturedShape(TexturedShape s) {
		this.actual = s;
	}

	public void render(Transform t,RenderContext ctx) {
		BBox me = t.transformBBox(actual.getBBox());
		if(!me.overlaps(ctx.area)){
			return;
		}
		if(t instanceof IdentityTransform){
			if(img == null || !prevArea.encloses(ctx.size)){
				RenderContext ctxlocal = new RenderContext(ctx.size, ctx.getTransform(),null);
				actual.render(t,ctxlocal);
				this.img = ctxlocal.img;
			} 
			ctx.renderMemo(new Vec(0,0),img);
			this.prevArea = ctx.size;
		} else {
			actual.render(t, ctx);
		}
		
	}

	@Override
	public BBox getBBox() {
		return actual.getBBox();
	}
	
}
