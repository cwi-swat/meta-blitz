package bezier.image;

import bezier.points.Vec;
import bezier.util.BBox;

public class Convolution implements Image{
	
	public final Image below;
	public final Image kernel;
	
	private final int sx, sy, ex, ey;
	
	
	public Convolution(Image below, Image kernel) {
		this.below = below;
		this.kernel = kernel;
		this.sx = (int)kernel.getBBox().x;
		sy = (int)kernel.getBBox().x;
		ex = (int)Math.ceil(kernel.getBBox().xr);
		ey = (int)Math.ceil(kernel.getBBox().yd);
	}
	@Override
	public BBox getBBox() {
		return new BBox(below.getBBox().getLeftUp().add(new Vec((int)sx,(int)sy)),
				below.getBBox().getRightDown().add(new Vec(Math.ceil(ex),Math.ceil(ey))));
	}
	@Override
	public Sample getSample(Vec p) {
		Sample res = new Sample(below.nrChannels());
		for(double x = sx ; x <= ex ; x++){
			for(double y = sy ; y <= ey ; y++){
				Vec tran = new Vec(x,y);
				res = res.add(below.getSample(p.add(tran)).mul(kernel.getSample(tran).getChannel(0)));
			}
		}
		return res;
	}
	@Override
	public int nrChannels() {
		return below.nrChannels();
	}
	
	

}
