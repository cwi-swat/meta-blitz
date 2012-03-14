package bezier.image.raster;
import bezier.image.sample.*;
import bezier.image.*;
public class Raster2 implements BoundedImage<Sample2>{
	private final double[] data;
	private final int size;
	public final int w,h;
	public final int x,y;

	public Raster2(int x, int y, int w, int h, double[] data){
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		this.size = w * h * 2;
		this.data = data;
	}	

    public Raster2(int x, int y, int w, int h, Image<Sample2> img){
		this(x,y,w,h,getData(x,y,w,h,img));
	}
	
	public Raster2(BoundedImage<Sample2> img){
		this(img.getX(),img.getY(),img.getWidth(),img.getHeight(),img);
	}
	private static double[] getData(int x, int y, int w, int h, Image<Sample2> img){
		double[] data = new double[w * h * 2];
		int z = 0;
		for(int j = y ; j < h ; j++){
			for(int i = x ; i < w ; i++){
				Sample2 s = img.get(i,j);
				 data[z++] = s.a;
				data[z++] = s.b;
				
			}
		}
		return data;
	}
	public Sample2 get(double x, double y){
		int start = ((int)y * w  + (int)x) * 2 ;
		if(start < 0 || start >= size){
			return new Sample2(0,0);
		}
		return new Sample2(data[start + 0],data[start + 1]);
	}
	public int getX(){ return x; }
 	public int getY(){ return y; }
	public int getWidth(){ return w; }
	public int getHeight() { return h; } 
}