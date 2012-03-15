package bezier.image.functions;

import bezier.composite.Path;
import bezier.image.Image;
import bezier.image.PixelArea;
import bezier.image.generated.RasterInstances.Raster1;
import bezier.image.generated.SampleInstances.Sample1;
import bezier.points.Vec;
import bezier.segment.BestProjection;
import bezier.segment.LengthMap;
import bezier.util.Util;

public class ProjectOnPath{

	public static Raster1 projectLength(Image<Sample1> mask, Path p, LengthMap map){
		PixelArea area = mask.getArea();
		if(area == null){
			throw new Error("Cannot project without mask!");
		}
		double[] data = new double[area.width * area.height];
		int z = 0;
		double total = map.totalLength();
		double prev = 0;
		double prevRow = 0;
		for(int y = area.y ; y < area.y + area.height ; y++){
			prev = prevRow;
			for(int x = area.x ; x < area.x + area.width ; x++){
				Vec v = new Vec(x,y);
				double d = p.project(v,new BestProjection<Double>(prev,p.getAt(prev).distanceSquared(v)));
				prev = d;
				if(x == area.x){
					prevRow = prev;
				}
				data[z++] = map.findLength(d) / total;
			}
		}
		return new Raster1(mask.getArea(), data);
	}
	
}
