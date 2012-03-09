package bezier.image;

import bezier.points.Vec;
import bezier.util.BBox;
import bezier.util.Util;

public class Gradient implements Image {

	public final Sample from, to;

	public Gradient(Sample from, Sample to) {
		this.from = from;
		this.to = to;
	}

	@Override
	public Sample getSample(Vec p) {
		double t = Util.clamp(p.x);
		return from.interpolate(t, to);
	}

	@Override
	public BBox getBBox() {
		return null;
	}

	@Override
	public int nrChannels() {
		return from.nrChannels();
	}
}
