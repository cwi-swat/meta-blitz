package bezier.image;

import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.util.Arrays;

import bezier.points.Vec;
import bezier.util.BBox;

public final class RasterImage implements Image{
	
	public static final int ALPHA = 0;
	public static final int BLUE = 1;
	public static final int GREEN = 2;
	public static final int RED = 3;

	
	public final int x, y;
	public final int width;
	public final int height;
	public final int channels;
	public final int totalSize;
	private final double[] data;
	
	RasterImage(int x, int y, int width, int height, int channels, double[] data){
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.channels = channels;
		this.data = data;
		this.totalSize= width *height * channels;
	}

	
	
	public double get(int x, int y, int channel){
		int i = y * width  * channels + x * channels + channel;
		if(i < 0 || i >= totalSize ){
			return 0.0;
		}
		return data[y * width  * channels + x * channels + channel ]/255.0;
	}
	
	private int getIndex(int x, int y , int channel){
		return y * width  * channels + x * channels + channel;
	}
	
	public BufferedImage toAWT(){
		BufferedImage res;
		if(channels == 1){
			res = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);
		} else if(channels == 3){
			res = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
		} else {
			res = new BufferedImage(width, height, BufferedImage.TYPE_4BYTE_ABGR);
		}
		WritableRaster r = res.getRaster();
		r.setPixels(0, 0, width, height, data);
		return res;
	}


	@Override
	public Sample getSample(Vec p) {

		int xc = (int)p.x - x;
		int yc = (int)p.y - y;
		if(xc < 0 || xc >= width || yc < 0 || yc >= height){
//			System.out.printf("Not inside %s\n", p);
			return Sample.TRANSPARENT;
		}
			int start = getIndex(xc,yc, 0);
			int end = start + channels;
			
			Sample res =  new Sample(Arrays.copyOfRange(data, start, end));
			return res;
	}
	
	@Override
	public String toString(){
		StringBuffer b = new StringBuffer();
		for(int x = 0 ; x < width ; x++){
			for(int y = 0 ; y < height ; y++){
				for(int c = 0 ; c < channels; c++){
					b.append(data[getIndex(x, y, c)]);
					b.append(' ');
				}
			}
			b.append('\n');
		}
		return b.toString();
	}


	@Override
	public BBox getBBox() {
		return new BBox(x,y,x + width,y + height);
	}


	@Override
	public int nrChannels() {
		return channels;
	}
}
