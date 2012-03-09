package bezier.image;

import bezier.points.Vec;
import bezier.util.BBox;

public class GaussianBlur{
	
	static final double CUTOFF = 0.01;

	
	static Image horizontalKernel(double stdDef){
		double[] data = createKernel(stdDef);
		int size = data.length / 2;
		return new RasterImage(-size , 0, data.length, 1, 1, data);
	}
	
	static Image verticalKernel(double stdDef){
		double[] data = createKernel(stdDef);
		int size = data.length / 2;
		return new RasterImage(0,-size, 1, data.length, 1, data);
	}
	
	static double[] createKernel(double stdDef){
		if(stdDef == 0.0){
			return new double[]{1.0};
		}
		double ssize = Math.sqrt(-Math.log(CUTOFF*stdDef*Math.sqrt(2.0*Math.PI))*2.0 * stdDef*stdDef);
		int size = (int) Math.floor(ssize);
		double[] res = new double[size*2 + 1];
		double fac = 1.0/(stdDef*Math.sqrt(2*Math.PI));
		double d = 1.0 /(2*stdDef * stdDef);
		double total = 0;
		for(int i = 0; i < res.length; i++){
			double dd= i - size;
			res[i] = fac * Math.exp(-(dd*dd)*d);
			total+=res[i];
		}
		for(int i = 0; i < res.length; i++){
			res[i]/=total;
		}
		
		return res;
	}
	
	public static RasterImage blur(Image original, double stdDef){
		RasterImage afterHor = Rasterize.rasterize(new VerticalConvolution(original, verticalKernel(stdDef)));
		return Rasterize.rasterize(new HorizontalConvolution(afterHor, horizontalKernel(stdDef)));
//		return afterHor;
	}

}
