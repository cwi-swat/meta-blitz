package textures.old.generated;

import textures.old.Image;
import textures.old.PixelArea;
import static textures.old.generated.SampleInstances.*;
public class RasterInstances{

	
	
	 public static class Raster1 implements Image<Sample1>{
	 	public final double[] data;
	     public final PixelArea area;
	 	public final int size;
	 
	 	public Raster1(PixelArea area, double[] data){
	 		this.area = area;
	 		this.size = area.width * area.height * 1;
	 		this.data = data;
	 	}	
	 
	     public Raster1(Image<Sample1> img){
	 		this(img.getArea(),getData(img.getArea(),img));
	 	}
	 	
	 
	 	public static double[] getData(PixelArea area, Image<Sample1> img){
	 		if(area == null) throw new Error("Cannot get pixels of undefined area!");
	 		double[] data = new double[area.width * area.height * 1];
	 		int z = 0;
	 		for(int j = area.y ; j < area.y + area.height ; j++){
	 			for(int i = area.x ; i < area.x + area.width ; i++){
	 				Sample1 s = img.get(i,j);
	 				 data[z++] = s.a;
	 				
	 			}
	 		}
	 		return data;
	 	}
	 
	 
	 	public Sample1 getMax() {
	 		int size = area.width * area.height * 1 ;
	 		Sample1 max = new Sample1(Double.NEGATIVE_INFINITY);
	 		for(int i = 0 ; i < size ; i+=1){
	 			max = max.max(new Sample1(data[i + 0]));
	 		}
	 		return max;
	 	}
	 
	 	public Sample1 getMin() {
	 		Sample1 min = new Sample1(Double.POSITIVE_INFINITY);
	 		for(int i = 0 ; i < size ; i+=1){
	 			min = min.min(new Sample1(data[i + 0]));
	 		}
	 		return min;
	 	}
	 
	 	public Sample1 get(double x, double y){
	 		x-=area.x; y-=area.y;
	 		int start = ((int)y * area.width  + (int)x) * 1 ;
	 		if(start < 0 || start >= size){
	 			return new Sample1(0);
	 		}
	 		return new Sample1(data[start + 0]);
	 	}
	 
	 	public PixelArea getArea(){
	 		return area;
	 	}
	 };
		
		
	 public static class Raster2 implements Image<Sample2>{
	 	public final double[] data;
	     public final PixelArea area;
	 	public final int size;
	 
	 	public Raster2(PixelArea area, double[] data){
	 		this.area = area;
	 		this.size = area.width * area.height * 2;
	 		this.data = data;
	 	}	
	 
	     public Raster2(Image<Sample2> img){
	 		this(img.getArea(),getData(img.getArea(),img));
	 	}
	 	
	 
	 	public static double[] getData(PixelArea area, Image<Sample2> img){
	 		if(area == null) throw new Error("Cannot get pixels of undefined area!");
	 		double[] data = new double[area.width * area.height * 2];
	 		int z = 0;
	 		for(int j = area.y ; j < area.y + area.height ; j++){
	 			for(int i = area.x ; i < area.x + area.width ; i++){
	 				Sample2 s = img.get(i,j);
	 				 data[z++] = s.a;
	 				data[z++] = s.b;
	 				
	 			}
	 		}
	 		return data;
	 	}
	 
	 
	 	public Sample2 getMax() {
	 		int size = area.width * area.height * 2 ;
	 		Sample2 max = new Sample2(Double.NEGATIVE_INFINITY,Double.NEGATIVE_INFINITY);
	 		for(int i = 0 ; i < size ; i+=2){
	 			max = max.max(new Sample2(data[i + 0],data[i + 1]));
	 		}
	 		return max;
	 	}
	 
	 	public Sample2 getMin() {
	 		Sample2 min = new Sample2(Double.POSITIVE_INFINITY,Double.POSITIVE_INFINITY);
	 		for(int i = 0 ; i < size ; i+=2){
	 			min = min.min(new Sample2(data[i + 0],data[i + 1]));
	 		}
	 		return min;
	 	}
	 
	 	public Sample2 get(double x, double y){
	 		x-=area.x; y-=area.y;
	 		int start = ((int)y * area.width  + (int)x) * 2 ;
	 		if(start < 0 || start >= size){
	 			return new Sample2(0,0);
	 		}
	 		return new Sample2(data[start + 0],data[start + 1]);
	 	}
	 
	 	public PixelArea getArea(){
	 		return area;
	 	}
	 };
		
		
	 public static class Raster3 implements Image<Sample3>{
	 	public final double[] data;
	     public final PixelArea area;
	 	public final int size;
	 
	 	public Raster3(PixelArea area, double[] data){
	 		this.area = area;
	 		this.size = area.width * area.height * 3;
	 		this.data = data;
	 	}	
	 
	     public Raster3(Image<Sample3> img){
	 		this(img.getArea(),getData(img.getArea(),img));
	 	}
	 	
	 
	 	public static double[] getData(PixelArea area, Image<Sample3> img){
	 		if(area == null) throw new Error("Cannot get pixels of undefined area!");
	 		double[] data = new double[area.width * area.height * 3];
	 		int z = 0;
	 		for(int j = area.y ; j < area.y + area.height ; j++){
	 			for(int i = area.x ; i < area.x + area.width ; i++){
	 				Sample3 s = img.get(i,j);
	 				 data[z++] = s.a;
	 				data[z++] = s.b;
	 				data[z++] = s.c;
	 				
	 			}
	 		}
	 		return data;
	 	}
	 
	 
	 	public Sample3 getMax() {
	 		int size = area.width * area.height * 3 ;
	 		Sample3 max = new Sample3(Double.NEGATIVE_INFINITY,Double.NEGATIVE_INFINITY,Double.NEGATIVE_INFINITY);
	 		for(int i = 0 ; i < size ; i+=3){
	 			max = max.max(new Sample3(data[i + 0],data[i + 1],data[i + 2]));
	 		}
	 		return max;
	 	}
	 
	 	public Sample3 getMin() {
	 		Sample3 min = new Sample3(Double.POSITIVE_INFINITY,Double.POSITIVE_INFINITY,Double.POSITIVE_INFINITY);
	 		for(int i = 0 ; i < size ; i+=3){
	 			min = min.min(new Sample3(data[i + 0],data[i + 1],data[i + 2]));
	 		}
	 		return min;
	 	}
	 
	 	public Sample3 get(double x, double y){
	 		x-=area.x; y-=area.y;
	 		int start = ((int)y * area.width  + (int)x) * 3 ;
	 		if(start < 0 || start >= size){
	 			return new Sample3(0,0,0);
	 		}
	 		return new Sample3(data[start + 0],data[start + 1],data[start + 2]);
	 	}
	 
	 	public PixelArea getArea(){
	 		return area;
	 	}
	 };
		
		
	 public static class Raster4 implements Image<Sample4>{
	 	public final double[] data;
	     public final PixelArea area;
	 	public final int size;
	 
	 	public Raster4(PixelArea area, double[] data){
	 		this.area = area;
	 		this.size = area.width * area.height * 4;
	 		this.data = data;
	 	}	
	 
	     public Raster4(Image<Sample4> img){
	 		this(img.getArea(),getData(img.getArea(),img));
	 	}
	 	
	 
	 	public static double[] getData(PixelArea area, Image<Sample4> img){
	 		if(area == null) throw new Error("Cannot get pixels of undefined area!");
	 		double[] data = new double[area.width * area.height * 4];
	 		int z = 0;
	 		for(int j = area.y ; j < area.y + area.height ; j++){
	 			for(int i = area.x ; i < area.x + area.width ; i++){
	 				Sample4 s = img.get(i,j);
	 				 data[z++] = s.a;
	 				data[z++] = s.b;
	 				data[z++] = s.c;
	 				data[z++] = s.d;
	 				
	 			}
	 		}
	 		return data;
	 	}
	 
	 
	 	public Sample4 getMax() {
	 		int size = area.width * area.height * 4 ;
	 		Sample4 max = new Sample4(Double.NEGATIVE_INFINITY,Double.NEGATIVE_INFINITY,Double.NEGATIVE_INFINITY,Double.NEGATIVE_INFINITY);
	 		for(int i = 0 ; i < size ; i+=4){
	 			max = max.max(new Sample4(data[i + 0],data[i + 1],data[i + 2],data[i + 3]));
	 		}
	 		return max;
	 	}
	 
	 	public Sample4 getMin() {
	 		Sample4 min = new Sample4(Double.POSITIVE_INFINITY,Double.POSITIVE_INFINITY,Double.POSITIVE_INFINITY,Double.POSITIVE_INFINITY);
	 		for(int i = 0 ; i < size ; i+=4){
	 			min = min.min(new Sample4(data[i + 0],data[i + 1],data[i + 2],data[i + 3]));
	 		}
	 		return min;
	 	}
	 
	 	public Sample4 get(double x, double y){
	 		x-=area.x; y-=area.y;
	 		int start = ((int)y * area.width  + (int)x) * 4 ;
	 		if(start < 0 || start >= size){
	 			return new Sample4(0,0,0,0);
	 		}
	 		return new Sample4(data[start + 0],data[start + 1],data[start + 2],data[start + 3]);
	 	}
	 
	 	public PixelArea getArea(){
	 		return area;
	 	}
	 };
		
		
	 public static class Raster5 implements Image<Sample5>{
	 	public final double[] data;
	     public final PixelArea area;
	 	public final int size;
	 
	 	public Raster5(PixelArea area, double[] data){
	 		this.area = area;
	 		this.size = area.width * area.height * 5;
	 		this.data = data;
	 	}	
	 
	     public Raster5(Image<Sample5> img){
	 		this(img.getArea(),getData(img.getArea(),img));
	 	}
	 	
	 
	 	public static double[] getData(PixelArea area, Image<Sample5> img){
	 		if(area == null) throw new Error("Cannot get pixels of undefined area!");
	 		double[] data = new double[area.width * area.height * 5];
	 		int z = 0;
	 		for(int j = area.y ; j < area.y + area.height ; j++){
	 			for(int i = area.x ; i < area.x + area.width ; i++){
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
	 
	 
	 	public Sample5 getMax() {
	 		int size = area.width * area.height * 5 ;
	 		Sample5 max = new Sample5(Double.NEGATIVE_INFINITY,Double.NEGATIVE_INFINITY,Double.NEGATIVE_INFINITY,Double.NEGATIVE_INFINITY,Double.NEGATIVE_INFINITY);
	 		for(int i = 0 ; i < size ; i+=5){
	 			max = max.max(new Sample5(data[i + 0],data[i + 1],data[i + 2],data[i + 3],data[i + 4]));
	 		}
	 		return max;
	 	}
	 
	 	public Sample5 getMin() {
	 		Sample5 min = new Sample5(Double.POSITIVE_INFINITY,Double.POSITIVE_INFINITY,Double.POSITIVE_INFINITY,Double.POSITIVE_INFINITY,Double.POSITIVE_INFINITY);
	 		for(int i = 0 ; i < size ; i+=5){
	 			min = min.min(new Sample5(data[i + 0],data[i + 1],data[i + 2],data[i + 3],data[i + 4]));
	 		}
	 		return min;
	 	}
	 
	 	public Sample5 get(double x, double y){
	 		x-=area.x; y-=area.y;
	 		int start = ((int)y * area.width  + (int)x) * 5 ;
	 		if(start < 0 || start >= size){
	 			return new Sample5(0,0,0,0,0);
	 		}
	 		return new Sample5(data[start + 0],data[start + 1],data[start + 2],data[start + 3],data[start + 4]);
	 	}
	 
	 	public PixelArea getArea(){
	 		return area;
	 	}
	 };
		
		
	 public static class Raster6 implements Image<Sample6>{
	 	public final double[] data;
	     public final PixelArea area;
	 	public final int size;
	 
	 	public Raster6(PixelArea area, double[] data){
	 		this.area = area;
	 		this.size = area.width * area.height * 6;
	 		this.data = data;
	 	}	
	 
	     public Raster6(Image<Sample6> img){
	 		this(img.getArea(),getData(img.getArea(),img));
	 	}
	 	
	 
	 	public static double[] getData(PixelArea area, Image<Sample6> img){
	 		if(area == null) throw new Error("Cannot get pixels of undefined area!");
	 		double[] data = new double[area.width * area.height * 6];
	 		int z = 0;
	 		for(int j = area.y ; j < area.y + area.height ; j++){
	 			for(int i = area.x ; i < area.x + area.width ; i++){
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
	 
	 
	 	public Sample6 getMax() {
	 		int size = area.width * area.height * 6 ;
	 		Sample6 max = new Sample6(Double.NEGATIVE_INFINITY,Double.NEGATIVE_INFINITY,Double.NEGATIVE_INFINITY,Double.NEGATIVE_INFINITY,Double.NEGATIVE_INFINITY,Double.NEGATIVE_INFINITY);
	 		for(int i = 0 ; i < size ; i+=6){
	 			max = max.max(new Sample6(data[i + 0],data[i + 1],data[i + 2],data[i + 3],data[i + 4],data[i + 5]));
	 		}
	 		return max;
	 	}
	 
	 	public Sample6 getMin() {
	 		Sample6 min = new Sample6(Double.POSITIVE_INFINITY,Double.POSITIVE_INFINITY,Double.POSITIVE_INFINITY,Double.POSITIVE_INFINITY,Double.POSITIVE_INFINITY,Double.POSITIVE_INFINITY);
	 		for(int i = 0 ; i < size ; i+=6){
	 			min = min.min(new Sample6(data[i + 0],data[i + 1],data[i + 2],data[i + 3],data[i + 4],data[i + 5]));
	 		}
	 		return min;
	 	}
	 
	 	public Sample6 get(double x, double y){
	 		x-=area.x; y-=area.y;
	 		int start = ((int)y * area.width  + (int)x) * 6 ;
	 		if(start < 0 || start >= size){
	 			return new Sample6(0,0,0,0,0,0);
	 		}
	 		return new Sample6(data[start + 0],data[start + 1],data[start + 2],data[start + 3],data[start + 4],data[start + 5]);
	 	}
	 
	 	public PixelArea getArea(){
	 		return area;
	 	}
	 };
		
		
	 public static class Raster7 implements Image<Sample7>{
	 	public final double[] data;
	     public final PixelArea area;
	 	public final int size;
	 
	 	public Raster7(PixelArea area, double[] data){
	 		this.area = area;
	 		this.size = area.width * area.height * 7;
	 		this.data = data;
	 	}	
	 
	     public Raster7(Image<Sample7> img){
	 		this(img.getArea(),getData(img.getArea(),img));
	 	}
	 	
	 
	 	public static double[] getData(PixelArea area, Image<Sample7> img){
	 		if(area == null) throw new Error("Cannot get pixels of undefined area!");
	 		double[] data = new double[area.width * area.height * 7];
	 		int z = 0;
	 		for(int j = area.y ; j < area.y + area.height ; j++){
	 			for(int i = area.x ; i < area.x + area.width ; i++){
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
	 
	 
	 	public Sample7 getMax() {
	 		int size = area.width * area.height * 7 ;
	 		Sample7 max = new Sample7(Double.NEGATIVE_INFINITY,Double.NEGATIVE_INFINITY,Double.NEGATIVE_INFINITY,Double.NEGATIVE_INFINITY,Double.NEGATIVE_INFINITY,Double.NEGATIVE_INFINITY,Double.NEGATIVE_INFINITY);
	 		for(int i = 0 ; i < size ; i+=7){
	 			max = max.max(new Sample7(data[i + 0],data[i + 1],data[i + 2],data[i + 3],data[i + 4],data[i + 5],data[i + 6]));
	 		}
	 		return max;
	 	}
	 
	 	public Sample7 getMin() {
	 		Sample7 min = new Sample7(Double.POSITIVE_INFINITY,Double.POSITIVE_INFINITY,Double.POSITIVE_INFINITY,Double.POSITIVE_INFINITY,Double.POSITIVE_INFINITY,Double.POSITIVE_INFINITY,Double.POSITIVE_INFINITY);
	 		for(int i = 0 ; i < size ; i+=7){
	 			min = min.min(new Sample7(data[i + 0],data[i + 1],data[i + 2],data[i + 3],data[i + 4],data[i + 5],data[i + 6]));
	 		}
	 		return min;
	 	}
	 
	 	public Sample7 get(double x, double y){
	 		x-=area.x; y-=area.y;
	 		int start = ((int)y * area.width  + (int)x) * 7 ;
	 		if(start < 0 || start >= size){
	 			return new Sample7(0,0,0,0,0,0,0);
	 		}
	 		return new Sample7(data[start + 0],data[start + 1],data[start + 2],data[start + 3],data[start + 4],data[start + 5],data[start + 6]);
	 	}
	 
	 	public PixelArea getArea(){
	 		return area;
	 	}
	 };
		
}