package bezier.image.functions;

import bezier.image.Image;
import bezier.image.PixelArea;
import bezier.image.Sample;


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
