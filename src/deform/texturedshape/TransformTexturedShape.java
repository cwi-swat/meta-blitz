package deform.texturedshape;

import java.awt.Graphics2D;

import deform.BBox;
import deform.Transform;
import deform.render.LocatedImage;
import deform.render.RenderContext;
import deform.shapes.Shape;
import deform.shapes.TransformShape;
import deform.tests.BasicDemo;
import deform.texture.TransformTexture;
import deform.transform.affine.AffineTransform;
import deform.transform.affine.BackwardsAffineTransform;
import deform.transform.affine.IdentityTransform;

public class TransformTexturedShape extends TexturedShape{

	final TexturedShape texs;
	final Transform t;
	public TransformTexturedShape(TexturedShape texs,
			Transform t) {
		this.texs = texs;
		this.t = t;
	}
	
	@Override
	public void render(Transform t,   Shape clip, RenderContext ctx) {
		if(t.transformBBox(getBBox()).overlaps(ctx.area)){
			Transform composed = t.compose(this.t);
				texs.render(composed, clip, ctx);
		} else {
//			System.out.println("Skip!");
		}
		
	}
	@Override
	public BBox getBBox() {
		return t.transformBBox(texs.getBBox());
	}

	
	
}
