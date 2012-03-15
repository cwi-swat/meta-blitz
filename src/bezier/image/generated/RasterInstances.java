package bezier.image.generated;

import bezier.image.BoundedImage;
import bezier.image.Image;
import static bezier.image.generated.SampleInstances.*;	
public class RasterInstances{



 public static class Raster1 implements BoundedImage<Sample1>{
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
 };
	
	
 public static class Raster2 implements BoundedImage<Sample2>{
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
 };
	
	
 public static class Raster3 implements BoundedImage<Sample3>{
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
 };
	
	
 public static class Raster4 implements BoundedImage<Sample4>{
 	private final double[] data;
 	private final int size;
 	public final int w,h;
 	public final int x,y;
 
 	public Raster4(int x, int y, int w, int h, double[] data){
 		this.x = x;
 		this.y = y;
 		this.w = w;
 		this.h = h;
 		this.size = w * h * 4;
 		this.data = data;
 	}	
 
     public Raster4(int x, int y, int w, int h, Image<Sample4> img){
 		this(x,y,w,h,getData(x,y,w,h,img));
 	}
 	
 	public Raster4(BoundedImage<Sample4> img){
 		this(img.getX(),img.getY(),img.getWidth(),img.getHeight(),img);
 	}
 	private static double[] getData(int x, int y, int w, int h, Image<Sample4> img){
 		double[] data = new double[w * h * 4];
 		int z = 0;
 		for(int j = y ; j < h ; j++){
 			for(int i = x ; i < w ; i++){
 				Sample4 s = img.get(i,j);
 				 data[z++] = s.a;
 				data[z++] = s.b;
 				data[z++] = s.c;
 				data[z++] = s.d;
 				
 			}
 		}
 		return data;
 	}
 	public Sample4 get(double x, double y){
 		int start = ((int)y * w  + (int)x) * 4 ;
 		if(start < 0 || start >= size){
 			return new Sample4(0,0,0,0);
 		}
 		return new Sample4(data[start + 0],data[start + 1],data[start + 2],data[start + 3]);
 	}
 	public int getX(){ return x; }
  	public int getY(){ return y; }
 	public int getWidth(){ return w; }
 	public int getHeight() { return h; } 
 };
	
	
 public static class Raster5 implements BoundedImage<Sample5>{
 	private final double[] data;
 	private final int size;
 	public final int w,h;
 	public final int x,y;
 
 	public Raster5(int x, int y, int w, int h, double[] data){
 		this.x = x;
 		this.y = y;
 		this.w = w;
 		this.h = h;
 		this.size = w * h * 5;
 		this.data = data;
 	}	
 
     public Raster5(int x, int y, int w, int h, Image<Sample5> img){
 		this(x,y,w,h,getData(x,y,w,h,img));
 	}
 	
 	public Raster5(BoundedImage<Sample5> img){
 		this(img.getX(),img.getY(),img.getWidth(),img.getHeight(),img);
 	}
 	private static double[] getData(int x, int y, int w, int h, Image<Sample5> img){
 		double[] data = new double[w * h * 5];
 		int z = 0;
 		for(int j = y ; j < h ; j++){
 			for(int i = x ; i < w ; i++){
 				Sample5 s = img.get(i,j);
 				 data[z++] = s.a;
 				data[z++] = s.b;
 				data[z++] = s.c;
 				data[z++] = s.d;
 				data[z++] = s.e;
 				
 			}
 		}
 		return data;
 	}
 	public Sample5 get(double x, double y){
 		int start = ((int)y * w  + (int)x) * 5 ;
 		if(start < 0 || start >= size){
 			return new Sample5(0,0,0,0,0);
 		}
 		return new Sample5(data[start + 0],data[start + 1],data[start + 2],data[start + 3],data[start + 4]);
 	}
 	public int getX(){ return x; }
  	public int getY(){ return y; }
 	public int getWidth(){ return w; }
 	public int getHeight() { return h; } 
 };
	
	
 public static class Raster6 implements BoundedImage<Sample6>{
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
 };
	
	
 public static class Raster7 implements BoundedImage<Sample7>{
 	private final double[] data;
 	private final int size;
 	public final int w,h;
 	public final int x,y;
 
 	public Raster7(int x, int y, int w, int h, double[] data){
 		this.x = x;
 		this.y = y;
 		this.w = w;
 		this.h = h;
 		this.size = w * h * 7;
 		this.data = data;
 	}	
 
     public Raster7(int x, int y, int w, int h, Image<Sample7> img){
 		this(x,y,w,h,getData(x,y,w,h,img));
 	}
 	
 	public Raster7(BoundedImage<Sample7> img){
 		this(img.getX(),img.getY(),img.getWidth(),img.getHeight(),img);
 	}
 	private static double[] getData(int x, int y, int w, int h, Image<Sample7> img){
 		double[] data = new double[w * h * 7];
 		int z = 0;
 		for(int j = y ; j < h ; j++){
 			for(int i = x ; i < w ; i++){
 				Sample7 s = img.get(i,j);
 				 data[z++] = s.a;
 				data[z++] = s.b;
 				data[z++] = s.c;
 				data[z++] = s.d;
 				data[z++] = s.e;
 				data[z++] = s.f;
 				data[z++] = s.g;
 				
 			}
 		}
 		return data;
 	}
 	public Sample7 get(double x, double y){
 		int start = ((int)y * w  + (int)x) * 7 ;
 		if(start < 0 || start >= size){
 			return new Sample7(0,0,0,0,0,0,0);
 		}
 		return new Sample7(data[start + 0],data[start + 1],data[start + 2],data[start + 3],data[start + 4],data[start + 5],data[start + 6]);
 	}
 	public int getX(){ return x; }
  	public int getY(){ return y; }
 	public int getWidth(){ return w; }
 	public int getHeight() { return h; } 
 };
}
	