package textures.functions;

import textures.Image;
import textures.PixelArea;
import textures.generated.SampleInstances.Sample1;

public class GiveY implements Image<Sample1> {

	@Override
	public Sample1 get(double x, double y) {
		return new Sample1(y);
	}

	@Override
	public PixelArea getArea() {
		return null;
	}

}