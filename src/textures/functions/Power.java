package textures.functions;

import textures.Image;
import textures.PixelArea;
import textures.generated.SampleInstances.Sample1;

public class Power implements Image<Sample1>{

	public final Image<Sample1> img;
	public final double d;

	public Power(Image<Sample1> img, double d) {
		this.img = img;
		this.d = d;
	}

	@Override
	public Sample1 get(double x, double y) {
		return new Sample1(Math.pow(img.get(x, y).a,d));
	}

	@Override
	public PixelArea getArea() {
		return img.getArea();
	}
	
	
}

