package textures.examples;

import paths.points.twod.Vec;
import textures.interfaces.ISample;
import textures.interfaces.ITexture;
import transform.IBackTransform;

public class TransformedTexture<Sample extends ISample<Sample>> implements ITexture<Sample> {

	final ITexture<Sample> real;
	final IBackTransform transform;
		
	public TransformedTexture(IBackTransform transform,ITexture<Sample> real) {
		this.real = real;
		this.transform = transform;
	}

	@Override
	public Sample sample(double x, double y) {
		Vec v = transform.from(new Vec(x,y));
		return real.sample(v.x, v.y);
	}

}
