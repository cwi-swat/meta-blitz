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
		   '	public Sample<sizev> max(Sample<sizev> rhs){
		   '		return new Sample<sizev>(<intercalate(",",["Math.max(this.<v> , rhs.<v>)" | v <- vars])>);
		   '	}
		   '
		   '	public Sample<sizev> min(Sample<sizev> rhs){
		   '		return new Sample<sizev>(<intercalate(",",["Math.min(this.<v> , rhs.<v>)" | v <- vars])>);
		   '	}
		   '
		   '    public Sample<sizev> div(Sample<sizev> rhs){
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
		   'public static class Raster<size> implements Image\<Sample<size>\>{
		   '	public final double[] data;
		   '    public final PixelArea area;
		   '	public final int size;
		   '
		   '	public Raster<size>(PixelArea area, double[] data){
		   '		this.area = area;
		   '		this.size = area.width * area.height * <size>;
		   '		this.data = data;
		   '	}	
		   '
		   '    public Raster<size>(Image\<Sample<size>\> img){
		   '		this(img.getArea(),getData(img.getArea(),img));
		   '	}
		   '	
		   '
		   '	public static double[] getData(PixelArea area, Image\<Sample<size>\> img){
		   '		if(area == null) throw new Error(\"Cannot get pixels of undefined area!\");
		   '		double[] data = new double[area.width * area.height * <size>];
		   '		int z = 0;
		   '		for(int j = area.y ; j \< area.y + area.height ; j++){
		   '			for(int i = area.x ; i \< area.x + area.width ; i++){
		   '				Sample<size> s = img.get(i,j);
		   '				 <for(v <- vars){>data[z++] = s.<v>;
		   '				<}>
		   '			}
		   '		}
		   '		return data;
		   '	}
		   '
		   '
		   '	public Sample<size> getMax() {
		   '		int size = area.width * area.height * <size> ;
		   '		Sample<size> max = new Sample<size>(<intercalate(",",["Double.NEGATIVE_INFINITY"| i <- [1..size]])>);
		   '		for(int i = 0 ; i \< size ; i+=<size>){
		   '			max = max.max(new Sample<size>(<intercalate(",",["data[i + <i>]" | i <- [1..size]])>));
		   '		}
		   '		return max;
		   '	}
		   '
		   '	public Sample<size> getMin() {
		   '		Sample<size> min = new Sample<size>(<intercalate(",",["Double.POSITIVE_INFINITY"| i <- [1..size]])>);
		   '		for(int i = 0 ; i \< size ; i+=<size>){
		   '			min = min.min(new Sample<size>(<intercalate(",",["data[i + <i>]" | i <- [1..size]])>));
		   '		}
		   '		return min;
		   '	}
		   '
		   '	public Sample<size> get(double x, double y){
		   '		x-=area.x; y-=area.y;
		   '		int start = ((int)y * area.width  + (int)x) * <size> ;
		   '		if(start \< 0 || start \>= size){
		   '			return new Sample<size>(<intercalate(",",["0" | i <- [0..size-1]])>);
		   '		}
		   '		return new Sample<size>(<intercalate(",",["data[start + <i>]" | i <- [0..size-1]])>);
		   '	}
		   '
		   '	public PixelArea getArea(){
		   '		return area;
		   '	}
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
	'			final PixelArea area = PixelArea.merge(l.getArea(),r.getArea());
	'			return new Image\<Sample<sizev>\>(){
	'				
	'				public PixelArea getArea(){
	'					return area;
	'				}
	'				
	'				public Sample<sizev> get(double x, double y){
	'					return l.get(x,y).concat(r.get(x,y));
	'				}
	'			};
	'		}
	'		<}>
	'
	'
	'
	'
	'		<for(s <- power(toSet([1..sizev])), size(s) < sizev, size(s) != 0){>
	'		public static Image\<Sample<sizev - size(s)>\> del<sizev>_<intercalate("",sort(toList(s)))>(final Image\<Sample<sizev>\> l){
	'			return new Image\<Sample<sizev -  size(s)>\>(){
	'
	'				public PixelArea getArea(){ return l.getArea(); }
	'
	'				public Sample<sizev -  size(s)> get(double x, double y){
	'					return l.get(x,y).del<intercalate("",sort(toList(s)))>();
	'				}
	'			};
	'		}
	'		
	'
	'
	'
	'	<}>
	'	<}>
	'}
	";
}