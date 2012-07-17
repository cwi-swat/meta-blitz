package textures.examples;

import deform.Vec;
import textures.interfaces.ITexture;
import textures.interfaces.Sample;
import transform.IBackTransform;

public class TransformedTexture implements ITexture {

	final ITexture real;
	final IBackTransform transform;

	public TransformedTexture(IBackTransform transform, ITexture real) {
		this.real = real;
		this.transform = transform;
	}

	@Override
	public Sample sample(Vec v) {
		Vec w = transform.from(v);
		return real.sample(w);
	}

}
