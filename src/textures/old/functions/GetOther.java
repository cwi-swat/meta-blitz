package textures.old.functions;

import textures.old.Image;
import textures.old.PixelArea;
import textures.old.Sample;
import textures.old.generated.SampleInstances.Sample2;

public class GetOther<A extends Sample<A>> implements Image<A>{

	final Image<Sample2> ref;
	final Image<A> get;
	final PixelArea area;
	public GetOther(Image<Sample2> ref, Image<A> get) {
		this.ref = ref;
		this.get = get;
		this.area = PixelArea.merge(ref.getArea(),get.getArea());
	}
	@Override
	public A get(double x, double y) {
		Sample2 refC = ref.get(x, y);
		return get.get(refC.a, refC.b);
	}
	@Override
	public PixelArea getArea() {
		return area;
	}
	
	
	
}
