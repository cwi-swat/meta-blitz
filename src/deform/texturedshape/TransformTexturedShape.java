package deform.texturedshape;

import java.awt.Graphics2D;

import deform.BBox;
import deform.Transform;
import deform.shapes.TransformShape;
import deform.tests.BasicDemo;
import deform.texture.TransformTexture;
import deform.transform.affine.AffineTransform;
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
	public LocatedImage render(Transform t, BBox b, java.awt.geom.AffineTransform trans ) {
		if(t.transformBBox(getBBox()).overlaps(b)){
			Transform composed = t.compose(this.t);
			if(BasicDemo.awt && composed instanceof AffineTransform && texs.isJava2DRenderable()){
				return texs.render(IdentityTransform.Instance, b, ((AffineTransform)composed).toJava2DTransform());
			} else {
				return texs.render(t.compose(this.t), b);
			}
		} else {
			return LocatedImage.empty;
		}
		
	}
	@Override
	public BBox getBBox() {
		return t.transformBBox(texs.getBBox());
	}

	
	
}
