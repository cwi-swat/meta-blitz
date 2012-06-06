package textures.functions;

import textures.Image;
import textures.PixelArea;

public final class Constant<A> implements Image<A>{

	public final A val;
	
	public Constant(A val){
		this.val = val;
	}
	
	@Override
	public A get(double x, double y) {
		return val;
	}

	@Override
	public PixelArea getArea() {
		return null;
	}

}
