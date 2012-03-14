module GenerateVectors

import String;
import List;

public list[str] getVars(size) = [stringChar(head(chars("a")) + i) | i <- [0..size-1]];

public str generateVector(int size, list[int] sizes){
	list[str] vars = getVars(size);
	
	return "public class Sample<size>{
		   '	public final double <intercalate(",",vars)>;
		   '
		   '	public Sample<size>(<intercalate(",",[ "double <v>" | v <- vars])>){
		   '		<intercalate("\n",["this.<v> = <v>;" | v <- vars])>
		   '	}	
		   '
		   '    public Sample<size> add(Sample<size> rhs){
		   '		return new Sample<size>(<intercalate(",",["this.<v> + rhs.<v>" | v <- vars])>);
		   '	}
		   ' 
		   '    public Sample<size> mul(Sample<size> rhs){
		   '		return new Sample<size>(<intercalate(",",["this.<v> * rhs.<v>" | v <- vars])>);
		   '	}
		   '
		   '	public Sample<size> mul(double d){
		   '		return new Sample<size>(<intercalate(",",["this.<v> * d" | v <- vars])>);
		   '	}
		   '
		   '	public Sample<size> div(double d){
		   '		return new Sample<size>(<intercalate(",",["this.<v> / d" | v <- vars])>);
		   '	}
		   '
		   '	public Sample<size> lerp(double d,Sample<size> rhs){
		   '		double od = 1.0 - d;
		   '		return new Sample<size>(<intercalate(",",["od * this.<v> + d * rhs.<v>" | v <- vars])>);
		   '	}
		   '	<for(s <- sizes, size + s <= List::size(sizes)){>
		   '	public Sample<size + s> concat(Sample<s> rhs) {
		   '		return new Sample<size + s>(<intercalate(",",["this.<v>" | v <- vars])>,<intercalate(",",["rhs.<v>" | v <- getVars(s)])>);
		   '	}<}>
		   '	
		   '}";
}

public str generateRaster(int size){
	list[str] vars = getVars(size);
	return "import bezier.image.sample.*;
		   'import bezier.image.*;
		   'public class Raster<size> implements BoundedImage\<Sample<size>\>{
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

public str generateAppendImage(list[int] sizes){
	return 
	
	"import bezier.image.sample.*;
	'import bezier.image.raster.*;
	'public class AppendImage{
	'		
	'	<for(size <- sizes){>
	'		<for(i <- [1,2..size-1],j := size - i){>
	'		public static Image\<Sample<size>\> append<i><j>(final Image\<Sample<i>\> l, final Image\<Sample<j>\> r){
	'			return new Image\<Sample<size>\>(){
	'				public Sample<size> get(double x, double y){
	'					return l.get(x,y).concat(r.get(x,y));
	'				}
	'			};
	'		}
	'
	'
	'
	'	public static class ConcatBoundedImg<i><j> implements BoundedImage\<Sample<size>\>{
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
	'		public Sample<size> get(double x, double y){
	'			if(x \< this.x || y \< this.y || x \> this.x + this.w -1 || y \> this.y + this.h -1){
	'				return new Sample<size>(<intercalate(",",["0" | z <- [1..size]])>);
	'			}
	'			return l.get(x,y).concat(r.get(x,y));
	'		}
	'	
	'	}
	'	public static  BoundedImage\<Sample<size>\> appendb<i><j>(final BoundedImage\<Sample<i>\> l, final Image\<Sample<j>\> r){
	'		return new ConcatBoundedImg<i><j>(l.getX(),l.getY(),l.getWidth(),l.getHeight(),l,r);
	'	}
	'
	'	public static  BoundedImage\<Sample<size>\> append<i>b<j>(final Image\<Sample<i>\> l, final BoundedImage\<Sample<j>\> r){
	'		return new ConcatBoundedImg<i><j>(r.getX(),r.getY(),r.getWidth(),r.getHeight(),l,r);
	'	}
	'
	'	public static BoundedImage\<Sample<size>\> appendb<i>b<j>(final BoundedImage\<Sample<i>\> l, final BoundedImage\<Sample<j>\> r){
	'		return new ConcatBoundedImg<i><j>(Math.max(l.getX(),r.getX()),Math.max(l.getY(),r.getY()),Math.min(l.getWidth(),r.getWidth()),Math.min(l.getHeight(),r.getHeight()),l,r);
	'	}
	'	<}>
	'	<}>
	'}
	";
}