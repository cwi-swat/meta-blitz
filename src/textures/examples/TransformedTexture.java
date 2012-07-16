package textures.examples;

import deform.Vec;
import textures.interfaces.ISample;
import transform.IBackTransform;

public class TransformedTexture<Sample extends ISample<Sample>> extends
		Texture<Sample> {

	final Texture<Sample> real;
	final IBackTransform transform;

	public TransformedTexture(IBackTransform transform, Texture<Sample> real) {
		this.real = real;
		this.transform = transform;
	}

	@Override
	public Sample sample(double x, double y) {
		Vec v = transform.from(new Vec(x, y));
		return real.sample(v.x, v.y);
	}

}
