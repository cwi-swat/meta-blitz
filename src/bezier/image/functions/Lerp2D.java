package bezier.image.functions;

import bezier.image.Image;
import bezier.image.PixelArea;
import bezier.image.Sample;
import bezier.image.generated.SampleInstances.Sample1;
import bezier.image.generated.SampleInstances.Sample2;

public class Lerp2D<A extends Sample<A>> implements Image<A>{

	final Image<Sample2> dImg;
	final Image<A> xa, xb;
	final Image<A> ya;
	final PixelArea area;
	
	
	public Lerp2D(Image<Sample2> dImg, Image<A> xa, Image<A> xb, Image<A> ya) {
		this.dImg = dImg;
		this.xa = xa;
		this.xb = xb;
		this.ya = ya;
		area = PixelArea.merge(dImg.getArea(),xa.getArea(),xb.getArea(),ya.getArea());
	}
	@Override
	public A get(double x, double y) {
		Sample2 samp = dImg.get(x, y);
		return xa.get(x, y).lerp(samp.a, xb.get(x,y)).lerp(samp.b, ya.get(x, y));
	}
	@Override
	public PixelArea getArea() {
		return area;
	}

}
