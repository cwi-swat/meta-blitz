package textures.old.functions;

import textures.old.Image;
import textures.old.PixelArea;
import textures.old.generated.SampleInstances.Sample1;

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