package textures.examples;

import deform.Vec;
import textures.Util;
import textures.sample.Color;

public class RadialGradient extends Texture<Color> {

	final Color inner, outer;

	public RadialGradient(Color inner, Color outer) {
		this.inner = inner;
		this.outer = outer;
	}

	@Override
	public Color sample(double x, double y) {
		Vec v = new Vec(x, y);
		double l = v.normSquared();
		if (l <= 1)
			return Util.lerp(inner, l, outer);
		else
			return outer;
	}

}
