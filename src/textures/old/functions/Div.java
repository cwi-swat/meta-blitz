package textures.old.functions;

import textures.old.Image;
import textures.old.PixelArea;
import textures.old.Sample;

public class Div<A extends Sample<A>> implements Image<A>{

	public final Image<A> img;
	public final double d;

	public Div(Image<A> img, double d) {
		this.img = img;
		this.d = d;
	}

	@Override
	public A get(double x, double y) {
		return img.get(x, y).div(d);
	}

	@Override
	public PixelArea getArea() {
		return img.getArea();
	}
	
	
}
