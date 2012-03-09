package bezier.image;

import bezier.points.Vec;
import bezier.util.BBox;

public class ApplyOnNotFullAlpha implements Image{
	
	public final Image onNotFullAlpha;
	public final Image reference;
	public ApplyOnNotFullAlpha(Image onNotFullAlpha, Image reference) {
		this.onNotFullAlpha = onNotFullAlpha;
		this.reference = reference;
	}
	@Override
	public BBox getBBox() {
		return onNotFullAlpha.getBBox();
	}
	@Override
	public int nrChannels() {
		return reference.nrChannels();
	}
	@Override
	public Sample getSample(Vec p) {
		Sample ref = reference.getSample(p);
		if(ref.getAlpha() < 255.0){
			return onNotFullAlpha.getSample(p);
		} else {
			return ref;
		}
	}
	
	
	
	

}
