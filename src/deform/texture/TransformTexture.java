package deform.texture;

import deform.BBox;
import deform.Color;
import deform.Texture;
import deform.Transform;
import deform.Vec;
import deform.transform.TransformBBox;

public class TransformTexture extends Texture{

	final Transform t;
	final Texture tex;
	
	public TransformTexture(Transform t, Texture tex){
		this.t = t;
		this.tex = tex;
	}
	
	@Override
	public Color sample(Vec point) {
		return tex.sample(t.from(point));
	}
	
	public BBox bounds(){
		return TransformBBox.transformBBox(t, tex.bounds());
	}

}
