package textures.old.functions;

import textures.old.Image;
import textures.old.PixelArea;
import textures.old.Sample;


public class Clamp<A extends Sample<A>> implements Image<A>{

	public final Image<A> img;
	public Clamp(Image<A> img) {
		this.img = img;
	}

	@Override
	public A get(double x, double y) {
		return img.get(x, y).clamp();
	}

	@Override
	public PixelArea getArea() {
		return img.getArea();
	}
	
	
}
