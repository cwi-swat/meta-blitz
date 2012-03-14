package bezier.image.raster;
import bezier.image.sample.*;
import bezier.image.*;
public class Raster3 implements BoundedImage<Sample3>{
	private final double[] data;
	private final int size;
	public final int w,h;
	public final int x,y;

	public Raster3(int x, int y, int w, int h, double[] data){
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		this.size = w * h * 3;
		this.data = data;
	}	

    public Raster3(int x, int y, int w, int h, Image<Sample3> img){
		this(x,y,w,h,getData(x,y,w,h,img));
	}
	
	public Raster3(BoundedImage<Sample3> img){
		this(img.getX(),img.getY(),img.getWidth(),img.getHeight(),img);
	}
	private static double[] getData(int x, int y, int w, int h, Image<Sample3> img){
		double[] data = new double[w * h * 3];
		int z = 0;
		for(int j = y ; j < h ; j++){
			for(int i = x ; i < w ; i++){
				Sample3 s = img.get(i,j);
				 data[z++] = s.a;
				data[z++] = s.b;
				data[z++] = s.c;
				
			}
		}
		return data;
	}
	public Sample3 get(double x, double y){
		int start = ((int)y * w  + (int)x) * 3 ;
		if(start < 0 || start >= size){
			return new Sample3(0,0,0);
		}
		return new Sample3(data[start + 0],data[start + 1],data[start + 2]);
	}
	public int getX(){ return x; }
 	public int getY(){ return y; }
	public int getWidth(){ return w; }
	public int getHeight() { return h; } 
}