package bezier.image;

import bezier.points.Vec;
import bezier.util.BBox;
import bezier.util.Util;

public class CircularGradient implements Image{

	public final Sample center, border;
	
	public CircularGradient(Sample center, Sample border) {
		this.center = center;
		this.border = border;
	}

	@Override
	public BBox getBBox() {
		return null;
	}

	@Override
	public Sample getSample(Vec p) {
		return center.interpolate(Util.clamp(p.sub(new Vec(0.5,0.5)).mul(2).normSquared()),border);
	}

	@Override
	public int nrChannels() {
		return center.nrChannels();
	}

}
