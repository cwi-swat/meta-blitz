package bezier.image;

import bezier.points.Transformation;
import bezier.points.Vec;
import bezier.util.BBox;

public class TransformedImage implements Image {

	public final Image img;
	public final Transformation trans;

	public TransformedImage(Image img, Transformation trans) {
		this.img = img;
		this.trans = trans;
	}

	@Override
	public Sample getSample(Vec p) {
		return img.getSample(trans.back.mul(p));
	}

	@Override
	public BBox getBBox() {
		throw new Error("NIY");
	}

	@Override
	public int nrChannels() {
		return img.nrChannels();
	}
}
