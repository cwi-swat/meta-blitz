package bezier.image.raster;
import bezier.image.sample.*;
import bezier.image.*;
public class Raster6 implements BoundedImage<Sample6>{
	private final double[] data;
	private final int size;
	public final int w,h;
	public final int x,y;

	public Raster6(int x, int y, int w, int h, double[] data){
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		this.size = w * h * 6;
		this.data = data;
	}	

    public Raster6(int x, int y, int w, int h, Image<Sample6> img){
		this(x,y,w,h,getData(x,y,w,h,img));
	}
	
	public Raster6(BoundedImage<Sample6> img){
		this(img.getX(),img.getY(),img.getWidth(),img.getHeight(),img);
	}
	private static double[] getData(int x, int y, int w, int h, Image<Sample6> img){
		double[] data = new double[w * h * 6];
		int z = 0;
		for(int j = y ; j < h ; j++){
			for(int i = x ; i < w ; i++){
				Sample6 s = img.get(i,j);
				 data[z++] = s.a;
				data[z++] = s.b;
				data[z++] = s.c;
				data[z++] = s.d;
				data[z++] = s.e;
				data[z++] = s.f;
				
			}
		}
		return data;
	}
	public Sample6 get(double x, double y){
		int start = ((int)y * w  + (int)x) * 6 ;
		if(start < 0 || start >= size){
			return new Sample6(0,0,0,0,0,0);
		}
		return new Sample6(data[start + 0],data[start + 1],data[start + 2],data[start + 3],data[start + 4],data[start + 5]);
	}
	public int getX(){ return x; }
 	public int getY(){ return y; }
	public int getWidth(){ return w; }
	public int getHeight() { return h; } 
}