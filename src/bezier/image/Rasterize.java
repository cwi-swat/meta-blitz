package bezier.image;

import bezier.points.Vec;
import bezier.util.BBox;

public class Rasterize {
	
	public static RasterImage rasterize(Image img){
		BBox b = img.getBBox();
		int x = (int)b.x;
		int xr = (int)Math.ceil(b.xr);
		int y = (int)b.y;
		int yd = (int)Math.ceil(b.yd);
		int w = xr - x;
		int h = yd - y;
		int size = w * h * Sample.NR_CHANNELS;
		int xc = x;
		int yc = y;
		int line = w * Sample.NR_CHANNELS;
		double[] data = new double[size];
		for(int i = 0 ; i < size ; i+= Sample.NR_CHANNELS){
			if(i % line == 0){
				xc = x;
				yc++;
			} else {
				xc++;
			}
			Sample s = img.getSample(new Vec(xc,yc));
			data[i] = s.getChannel(0);
			data[i + 1] = s.getChannel(1);
			data[i + 2] = s.getChannel(2);
			data[i + 3] = s.getChannel(3);
			
		}
		return new RasterImage(x, y, w, h, Sample.NR_CHANNELS, data);
	}
	
	static class RasterPart implements Runnable{
		final int istart, iend;
		final int x,y, w,h,c;
		final double[] data;
		final Image img;

		public RasterPart(int istart, int iend, int x, int y, int w, int h, int c,
				double[] data, Image img) {
			this.istart = istart;
			this.iend = iend;
			this.x = x;
			this.y = y;
			this.w = w;
			this.h = h;
			this.data = data;
			this.img = img;
			this.c = c;
		}

		@Override
		public void run() {
			int line = w* c;
			int xc = ((istart) % (w * 4)) / 4 + x;
			int yc = istart / (w * 4) + y;
			for(int i = istart ; i < iend ; i+= Sample.NR_CHANNELS){
				if(i % line == 0){
					xc = x;
					yc++;
				} else {
					xc++;
				}
				Sample s = img.getSample(new Vec(xc,yc));
				data[i] = s.getChannel(0);
				data[i + 1] = s.getChannel(1);
				data[i + 2] = s.getChannel(2);
				data[i + 3] = s.getChannel(3);
			}
		}
		
		
	}
	
	public static RasterImage parallelRasterize(Image img){
		int cores = Runtime.getRuntime().availableProcessors();
		BBox b = img.getBBox();
		int x = (int)b.x;
		int xr = (int)Math.ceil(b.xr);
		int y = (int)b.y;
		int yd = (int)Math.ceil(b.yd);
		int w = xr - x;
		int h = yd - y;
		int c = img.nrChannels();
		int size = w * h * c;

		double[] data = new double[size];
		int pixels = w*h;
		int sizePerProcessor = (pixels / cores)*4;

		int sizeFirst = size  - sizePerProcessor*(cores-1);
		Thread[] ts = new Thread[cores];
		ts[0] = new Thread(new RasterPart(0, sizeFirst,x,y,w,h,c,data,img));
		ts[0].start();
		int prevEnd = sizeFirst;
		for(int i = 1 ; i < cores; i++){
			int curEnd = prevEnd + sizePerProcessor;
			RasterPart p = new RasterPart(prevEnd,curEnd,x,y,w,h,c,data,img);
			p.run();
			ts[i] = new Thread(new RasterPart(prevEnd,curEnd,x,y,w,h,c,data,img));
			ts[i].start();
			prevEnd = curEnd;
		}
		for(Thread t : ts){
			try {
				t.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		return new RasterImage(x, yd, w, h, c, data);

	}

}
