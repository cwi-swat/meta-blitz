package textures.functions;

import textures.Image;
import textures.PixelArea;
import textures.Sample;
import textures.generated.SampleInstances.Sample1;

public class Mask <A extends Sample<A>> implements Image<A>{

	Image<Sample1> mask;
	Image<A> img;
	A def;
	
	
	
	public Mask(Image<Sample1> mask, Image<A> img, A def) {
		this.mask = mask;
		this.img = img;
		this.def = def;
	}

	@Override
	public A get(double x, double y) {
		if(mask.get(x, y).a > 0){
			return img.get(x, y);
		} else {
			return def;
		}
	}

	@Override
	public PixelArea getArea() {
		return mask.getArea();
	}

}