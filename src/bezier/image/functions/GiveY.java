package bezier.image.functions;

import bezier.image.Image;
import bezier.image.PixelArea;
import bezier.image.generated.SampleInstances.Sample1;

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