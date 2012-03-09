package bezier.image;

import bezier.points.Vec;
import bezier.util.BBox;

public class UseAlphaLeft implements Image{

	public final Image alpha;
	public final Image img;
	public UseAlphaLeft(Image alpha, Image img) {
		this.alpha = alpha;
		this.img = img;
	}
	@Override
	public Sample getSample(Vec p) {
		double al = alpha.getSample(p).getAlpha();
//		System.out.printf("Alpha %f\n", al);
		Sample samp = img.getSample(p);
		return new Sample(samp.getBlue() * 255,samp.getGreen() * 255,samp.getRed() * 255,al);
	}
	@Override
	public BBox getBBox() {
		return alpha.getBBox();
	}
	@Override
	public int nrChannels() {
		return Sample.NR_CHANNELS;
	}
	
	
	
}
