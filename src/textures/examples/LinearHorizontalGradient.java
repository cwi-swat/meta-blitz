package textures.examples;

import static textures.Util.lerp;
import textures.sample.Color;

public class LinearHorizontalGradient extends Texture<Color> {

	final Color left, right;

	public LinearHorizontalGradient(Color left, Color right) {
		this.left = left;
		this.right = right;
	}

	@Override
	public Color sample(double x, double y) {
		return lerp(left, x, right);
	}

}
