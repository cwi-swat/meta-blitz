package bezier.image.functions;

import bezier.composite.Path;
import bezier.composite.ProjectPath;
import bezier.image.Image;
import bezier.image.PixelArea;
import bezier.image.generated.RasterInstances.Raster1;
import bezier.image.generated.RasterInstances.Raster2;
import bezier.image.generated.SampleInstances.Sample1;
import bezier.image.generated.SampleInstances.Sample2;
import bezier.points.Vec;
import bezier.segment.BestProjection;
import bezier.segment.LengthMap;
import bezier.segment.curve.Curve;
import bezier.segment.curve.CurveApproxTree;
import bezier.util.Util;

public class ProjectOnPath{

	public static Raster2 projectLength(Image<Sample1> mask, Path p){
		PixelArea area = mask.getArea();
		if(area == null){
			throw new Error("Cannot project without mask!");
		}
		double[] data = new double[area.width * area.height *2];
		int z = 0;
		double prev = 0;
		double prevRow = 0;
		ProjectPath pp = p.getProjectPath();
		Vec prevV = p.getStartPoint();
		for(int y = area.y ; y < area.y + area.height ; y++){
			prev = prevRow;
			for(int x = area.x ; x < area.x + area.width ; x++){
				if(mask.get(x, y).last() <= 0){
					z+=2;
					continue;
				}
				Vec v = new Vec(x,y);
				BestProjection<Double> best = new BestProjection<Double>(prev,prevV.distanceSquared(v),prevV);
				best = pp.project(v);
				double d = best.t;
				if(best.v != null){
					prevV = best.v;
				}
				prev = d;
				if(x == area.x){
					prevRow = prev;
				}
				data[z++] = d ;
				data[z++] = best.distanceSquaredUpperbound;
			}
		}
		return new Raster2(mask.getArea(), data);
	}
	
}
