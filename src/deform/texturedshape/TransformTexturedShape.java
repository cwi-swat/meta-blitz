package deform.texturedshape;

import java.awt.Graphics2D;

import deform.BBox;
import deform.Transform;
import deform.shapes.TransformShape;
import deform.texture.TransformTexture;
import deform.transform.TransformBBox;

public class TransformTexturedShape implements TexturedShape{

	final TexturedShape texs;
	final Transform t;
	public TransformTexturedShape(TexturedShape texs,
			Transform t) {
		this.texs = texs;
		this.t = t;
	}
	@Override
	public LocatedImage render(Transform t, BBox b) {
		if(TransformBBox.transformBBox(t, getBBox()).overlaps(b)){
			return texs.render(t.compose(this.t), b);
		} else {
			return LocatedImage.empty;
		}
		
	}
	@Override
	public BBox getBBox() {
		return TransformBBox.transformBBox(t, texs.getBBox());
	}
	
	
	
}
