package bezier.image;

import bezier.points.Vec;
import bezier.util.BBox;

public class HorizontalConvolution implements Image{
	
	public final Image below;
	public final Image kernel;
	
	private final int sx, ex;
	
	
	public HorizontalConvolution(Image below, Image kernel) {
		this.below = below;
		this.kernel = kernel;
		this.sx = (int)kernel.getBBox().x;
		ex = (int)Math.ceil(kernel.getBBox().xr);
	}
	@Override
	public BBox getBBox() {
		BBox result =  new BBox(below.getBBox().getLeftUp().add(new Vec(sx,0)),
				below.getBBox().getRightDown().add(new Vec(ex-1,0)));
		return result;
	}
	@Override
	public Sample getSample(Vec p) {
		Sample res = new Sample(below.nrChannels());
		for(double x = sx ; x < ex ; x++){
				Vec tran = new Vec(x,0);
				res = res.add(below.getSample(p.add(tran)).mul(kernel.getSample(tran).getChannel(0)));
		}
		return res;
	}
	@Override
	public int nrChannels() {
		return below.nrChannels();
	}
	
	

}
