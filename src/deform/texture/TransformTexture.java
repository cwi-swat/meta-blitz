package deform.texture;

import deform.BBox;
import deform.Color;
import deform.Texture;
import deform.Transform;
import deform.Vec;

public class TransformTexture implements Texture{

	final Transform t;
	final Texture tex;
	
	public TransformTexture(Transform t, Texture tex){
		this.t = t;
		this.tex = tex;
	}
	
	@Override
	public Color sample(Vec point) {
		Vec p = t.from(point);
		return tex.sample(p);
	}

}
