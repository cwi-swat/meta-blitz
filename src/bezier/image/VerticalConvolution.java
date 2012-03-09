package bezier.image;

import bezier.points.Vec;
import bezier.util.BBox;

public class VerticalConvolution implements Image{
	
	public final Image below;
	public final Image kernel;
	
	private final int  sy,  ey;
	
	public VerticalConvolution(Image below, Image kernel) {
		this.below = below;
		this.kernel = kernel;
		sy = (int)kernel.getBBox().y;
		ey = (int)Math.ceil(kernel.getBBox().yd);
	}
	@Override
	public BBox getBBox() {
		return new BBox(below.getBBox().getLeftUp().add(new Vec(0,sy)),
				below.getBBox().getRightDown().add(new Vec(0,ey-1)));
	}
	@Override
	public Sample getSample(Vec p) {
		Sample res = new Sample(below.nrChannels());
		for(double y = sy ; y < ey ; y++){
			Vec tran = new Vec(0,y);
			res = res.add(below.getSample(p.add(tran)).mul(kernel.getSample(tran).getChannel(0)));
		}
	
		return res;
	}
	@Override
	public int nrChannels() {
		return below.nrChannels();
	}
	
	

}
