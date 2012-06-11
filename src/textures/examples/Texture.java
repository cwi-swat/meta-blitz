package textures.examples;

import textures.interfaces.ISample;
import transform.IBackTransform;

public abstract class Texture<Sample extends ISample<Sample>> {

	public abstract Sample sample(double x, double y);

	public Texture<Sample> transform(IBackTransform t) {
		return new TransformedTexture<Sample>(t, this);
	}
}
