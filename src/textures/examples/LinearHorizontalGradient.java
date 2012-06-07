package textures.examples;

import textures.interfaces.ITexture;
import textures.sample.Color;
import static textures.Util.*;

public class LinearHorizontalGradient implements ITexture<Color>{

	final Color left , right;
	
	public LinearHorizontalGradient(Color left, Color right) {
		this.left = left;
		this.right = right;
	}
	
	@Override
	public Color sample(double x, double y) {
		return lerp(left,x,right); 
	}

}
