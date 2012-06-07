package textures.old.functions;

import paths.points.twod.Vec;
import paths.transform.AffineTransformation;
import textures.old.Image;
import textures.old.PixelArea;
import textures.old.Sample;

public class ImageTransformation<A extends Sample<A>> implements Image<A> {

	public final Image<A> img;
	public final AffineTransformation trans;
	public final PixelArea area;
	
	
	
	public ImageTransformation(Image<A> img, AffineTransformation trans) {
		this.img = img;
		this.trans = trans;
		if(img.getArea() == null){
			area = null;
		} else {
			area = new PixelArea(
					trans.back.mul(new Vec(img.getArea().x,img.getArea().y)),
					trans.back.mul(new Vec(img.getArea().x,img.getArea().y + img.getArea().height)),
					trans.back.mul(new Vec(img.getArea().x + img.getArea().width,img.getArea().y)),
					trans.back.mul(new Vec(img.getArea().x + img.getArea().width,img.getArea().y + img.getArea().height)));
		}
	}

	@Override
	public A get(double x, double y) {
		Vec a = trans.back.mul(new Vec(x,y));
		return img.get(a.x, a.y);
	}

	@Override
	public PixelArea getArea() {
		return area;
	}
	
	

}
