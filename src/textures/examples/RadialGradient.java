package textures.examples;

import paths.points.twod.Vec;
import textures.Util;
import textures.interfaces.ITexture;
import textures.sample.Color;

public class RadialGradient implements ITexture<Color>{

	final Color inner , outer;

	public RadialGradient(Color inner, Color outer) {
		this.inner = inner;
		this.outer = outer;
	}

	@Override
	public Color sample(double x, double y) {
		Vec v = new Vec(x,y);
		double l = v.normSquared();
		if(l <= 1) return Util.lerp(inner,l,outer);
		else return outer;
	}
	
	
	
}
