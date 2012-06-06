package textures.functions;


import textures.Image;
import textures.PixelArea;
import textures.Sample;
import textures.generated.SampleInstances.Sample1;

public class Lerp<A extends Sample<A>> implements Image<A>{
	
	Image<Sample1> dImg;
	Image<A> a, b;
	PixelArea area;
	
	
	public Lerp(Image<Sample1> dImg, Image<A> a, Image<A> b) {
		this.dImg = dImg;
		this.a = a;
		this.b = b;
		area = PixelArea.merge(dImg.getArea(),a.getArea(),b.getArea());
	}
	@Override
	public A get(double x, double y) {
		return a.get(x, y).lerp(dImg.get(x, y).a, b.get(x,y));
	}
	@Override
	public PixelArea getArea() {
		return area;
	}
	

}
