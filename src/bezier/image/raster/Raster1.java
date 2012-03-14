package bezier.image.raster;
import bezier.image.sample.*;
import bezier.image.*;
import bezier.image.sample.*;
import bezier.image.*;
public class Raster1 implements BoundedImage<Sample1>{
	private final double[] data;
	private final int size;
	public final int w,h;
	public final int x,y;

	public Raster1(int x, int y, int w, int h, double[] data){
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		this.size = w * h * 1;
		this.data = data;
	}	

    public Raster1(int x, int y, int w, int h, Image<Sample1> img){
		this(x,y,w,h,getData(x,y,w,h,img));
	}
	
	public Raster1(BoundedImage<Sample1> img){
		this(img.getX(),img.getY(),img.getWidth(),img.getHeight(),img);
	}
	private static double[] getData(int x, int y, int w, int h, Image<Sample1> img){
		double[] data = new double[w * h * 1];
		int z = 0;
		for(int j = y ; j < h ; j++){
			for(int i = x ; i < w ; i++){
				Sample1 s = img.get(i,j);
				 data[z++] = s.a;
				
			}
		}
		return data;
	}
	public Sample1 get(double x, double y){
		int start = ((int)y * w  + (int)x) * 1 ;
		if(start < 0 || start >= size){
			return new Sample1(0);
		}
		return new Sample1(data[start + 0]);
	}
	public int getX(){ return x; }
 	public int getY(){ return y; }
	public int getWidth(){ return w; }
	public int getHeight() { return h; } 
}