module GenerateVectors

import String;
import List;
import Set;

public list[str] getVars(size) = [stringChar(head(chars("a")) + i) | i <- [0..size-1]];

public str generateVector(int sizev, list[int] sizes){
	list[str] vars = getVars(sizev);
	
	return "public static class Sample<sizev> implements Sample\<Sample<sizev>\>{
		   '	public final double <intercalate(",",vars)>;
		   '
		   '	public Sample<sizev>(<intercalate(",",[ "double <v>" | v <- vars])>){
		   '		<intercalate("\n",["this.<v> = <v>;" | v <- vars])>
		   '	}	
		   '
		   '    public Sample<sizev> add(Sample<sizev> rhs){
		   '		return new Sample<sizev>(<intercalate(",",["this.<v> + rhs.<v>" | v <- vars])>);
		   '	}
		   '
		   '    public Sample<sizev> sub(Sample<sizev> rhs){
		   '		return new Sample<sizev>(<intercalate(",",["this.<v> - rhs.<v>" | v <- vars])>);
		   '	}
		   ' 
		   '    public Sample<sizev> mul(Sample<sizev> rhs){
		   '		return new Sample<sizev>(<intercalate(",",["this.<v> * rhs.<v>" | v <- vars])>);
		   '	}
		   '
		   '	public Sample<sizev> mul(double d){
		   '		return new Sample<sizev>(<intercalate(",",["this.<v> * d" | v <- vars])>);
		   '	}
		   '
		   '	public Sample<sizev> div(double d){
		   '		return new Sample<sizev>(<intercalate(",",["this.<v> / d" | v <- vars])>);
		   '	}
		   '
		   '	public double last(){ return <vars[sizev-1]>; }
		   '
		   '	public Sample<sizev> lerp(double d,Sample<sizev> rhs){
		   '		double od = 1.0 - d;
		   '		return new Sample<sizev>(<intercalate(",",["od * this.<v> + d * rhs.<v>" | v <- vars])>);
		   '	}
		   '
		   '	<for(ss <- power(toSet([1..sizev])), s := sort(toList(ss)), [e,g*] := s, size(s) != sizev) {> public Sample<sizev - size(s)> del<intercalate("",s)>(){
		   '		return new Sample<sizev - size(s)>(<intercalate(",",[vars[j-1] | j <- sort(toList(toSet([1..sizev] - s)))])>);
		   '	}<}>
		   '
		   '	<for(s <- power(toSet([1..sizev])), size(s) > 0) {> public Sample<sizev> clamp<size(s) == sizev ? "" : intercalate("",toList(s))>(){
		   '		return new Sample<sizev>(<intercalate(",",[i in s ? "Util.clamp(<vars[i-1]>)" : vars[i-1] | i <- [1..sizev]])>);
		   '	}<}>
		   '	<for(s <- sizes, sizev + s <= List::size(sizes)){>
		   '	public Sample<sizev + s> concat(Sample<s> rhs) {
		   '		return new Sample<sizev + s>(<intercalate(",",["this.<v>" | v <- vars])>,<intercalate(",",["rhs.<v>" | v <- getVars(s)])>);
		   '	}<}>
		   '	
		   '}";
}

public str generateVectors(int maxSize){
	return "public class SampleInstances{
	'
	'	<for(i <- [1..maxSize]){>
	'	<generateVector(i,[1..maxSize])>;
	'	<}>
	";
}

public str generateRaster(int size){
	list[str] vars = getVars(size);
	return "
		   'public static class Raster<size> implements BoundedImage\<Sample<size>\>{
		   '	private final double[] data;
		   '	private final int size;
		   '	public final int w,h;
		   '	public final int x,y;
		   '
		   '	public Raster<size>(int x, int y, int w, int h, double[] data){
		   '		this.x = x;
		   '		this.y = y;
		   '		this.w = w;
		   '		this.h = h;
		   '		this.size = w * h * <size>;
		   '		this.data = data;
		   '	}	
		   '
		   '    public Raster<size>(int x, int y, int w, int h, Image\<Sample<size>\> img){
		   '		this(x,y,w,h,getData(x,y,w,h,img));
		   '	}
		   '	
		   '	public Raster<size>(BoundedImage\<Sample<size>\> img){
		   '		this(img.getX(),img.getY(),img.getWidth(),img.getHeight(),img);
		   '	}
		   '	private static double[] getData(int x, int y, int w, int h, Image\<Sample<size>\> img){
		   '		double[] data = new double[w * h * <size>];
		   '		int z = 0;
		   '		for(int j = y ; j \< h ; j++){
		   '			for(int i = x ; i \< w ; i++){
		   '				Sample<size> s = img.get(i,j);
		   '				 <for(v <- vars){>data[z++] = s.<v>;
		   '				<}>
		   '			}
		   '		}
		   '		return data;
		   '	}
		   '	public Sample<size> get(double x, double y){
		   '		int start = ((int)y * w  + (int)x) * <size> ;
		   '		if(start \< 0 || start \>= size){
		   '			return new Sample<size>(<intercalate(",",["0" | i <- [0..size-1]])>);
		   '		}
		   '		return new Sample<size>(<intercalate(",",["data[start + <i>]" | i <- [0..size-1]])>);
		   '	}
		   '	public int getX(){ return x; }
		   ' 	public int getY(){ return y; }
		   '	public int getWidth(){ return w; }
		   '	public int getHeight() { return h; } 
		   '}";
}

public str generateRasters(int maxSize){
	return "public class RasterInstances{
	'
	'	<for(i <- [1..maxSize]){>
	'	<generateRaster(i)>;
	'	<}>
	";
}

public str generateAppendImage(list[int] sizes){
	return 
	"import bezier.image.sample.*;
	'import bezier.image.raster.*;
	'public class ImageSampleOpers{
	'		
	'	<for(sizev <- sizes){>
	'		<for(i <- [1,2..sizev-1],j := sizev - i){>
	'		public static Image\<Sample<sizev>\> append<i><j>(final Image\<Sample<i>\> l, final Image\<Sample<j>\> r){
	'			return new Image\<Sample<sizev>\>(){
	'				public Sample<sizev> get(double x, double y){
	'					return l.get(x,y).concat(r.get(x,y));
	'				}
	'			};
	'		}
	'
	'
	'
	'	private static class ConcatBoundedImg<i><j> implements BoundedImage\<Sample<sizev>\>{
	'		int x, y, w, h;
	'		Image\<Sample<i>\> l;
	'		Image\<Sample<j>\> r;
	'		
	'		ConcatBoundedImg<i><j>(int x, int y, int w, int h, Image\<Sample<i>\> l, Image\<Sample<j>\> r){
	'			this.x = x;
	'			this.y = y;
	'			this.w = w;
	'			this.h = h;
	'			this.l = l;
	'			this.r = r;
	'		}
	'
	'		public int getX() { return x; }
	'		public int getY() { return y; }
	'		public int getWidth() { return w; }
	'		public int getHeight() { return h; }
	'		public Sample<sizev> get(double x, double y){
	'			if(x \< this.x || y \< this.y || x \> this.x + this.w -1 || y \> this.y + this.h -1){
	'				return new Sample<sizev>(<intercalate(",",["0" | z <- [1..sizev]])>);
	'			}
	'			return l.get(x,y).concat(r.get(x,y));
	'		}
	'	
	'	}
	'	public static  BoundedImage\<Sample<sizev>\> appendb<i><j>(final BoundedImage\<Sample<i>\> l, final Image\<Sample<j>\> r){
	'		return new ConcatBoundedImg<i><j>(l.getX(),l.getY(),l.getWidth(),l.getHeight(),l,r);
	'	}
	'
	'	public static  BoundedImage\<Sample<sizev>\> append<i>b<j>(final Image\<Sample<i>\> l, final BoundedImage\<Sample<j>\> r){
	'		return new ConcatBoundedImg<i><j>(r.getX(),r.getY(),r.getWidth(),r.getHeight(),l,r);
	'	}
	'
	'	public static BoundedImage\<Sample<sizev>\> appendb<i>b<j>(final BoundedImage\<Sample<i>\> l, final BoundedImage\<Sample<j>\> r){
	'		return new ConcatBoundedImg<i><j>(Math.max(l.getX(),r.getX()),Math.max(l.getY(),r.getY()),Math.min(l.getWidth(),r.getWidth()),Math.min(l.getHeight(),r.getHeight()),l,r);
	'	}
	'	<}>
	'
	'
	'		<for(s <- power(toSet([1..sizev])), size(s) < sizev, size(s) != 0){>
	'		public static Image\<Sample<sizev - size(s)>\> del<sizev>_<intercalate("",sort(toList(s)))>(final Image\<Sample<sizev>\> l){
	'			return new Image\<Sample<sizev -  size(s)>\>(){
	'				public Sample<sizev -  size(s)> get(double x, double y){
	'					return l.get(x,y).del<intercalate("",sort(toList(s)))>();
	'				}
	'			};
	'		}
	'		
	'		public static Image\<Sample<sizev -  size(s)>\> delb<sizev>_<intercalate("",sort(toList(s)))>(final BoundedImage\<Sample<sizev>\> l){
	'			return new BoundedImage\<Sample<sizev -  size(s)>\>(){
	'				public Sample<sizev -  size(s)> get(double x, double y){
	'					return l.get(x,y).del<intercalate("",sort(toList(s)))>();
	'				}
	'			
	'			public int getX() { return l.getX(); }
	'			public int getY() { return l.getY(); }
	'			public int getWidth() { return l.getWidth(); }
	'			public int getHeight() { return l.getHeight(); }
	'			};
	'		}
	'
	'
	'
	'	<}>
	'	<}>
	'}
	";
}