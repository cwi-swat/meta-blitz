package bezier.image.functions;

import bezier.image.Image;
import bezier.image.PixelArea;
import bezier.image.generated.ColorsAlpha;
import bezier.image.generated.SampleInstances.Sample1;
import bezier.image.generated.SampleInstances.Sample4;

public class MulAlpha implements Image<Sample4>{

	final Image<Sample1> mask;
	final Image<Sample4> img;
	final PixelArea area;
	
	public MulAlpha(Image<Sample1> mask, Image<Sample4> img) {
		this.mask = mask;
		this.img = img;
		this.area = PixelArea.merge(mask.getArea(),img.getArea());
	}

	@Override
	public Sample4 get(double x, double y) {
		double alpha = mask.get(x, y).a;
		if(alpha > 0){
			Sample4 samp = img.get(x, y);
			return new Sample4(samp.a,samp.b,samp.c,samp.d * alpha);
		}
		return ColorsAlpha.transparent;
	}

	@Override
	public PixelArea getArea() {
	return area;
	}

	

}
