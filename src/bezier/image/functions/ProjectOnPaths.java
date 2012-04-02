package bezier.image.functions;

import bezier.composite.Paths;
import bezier.composite.TPaths;
import bezier.image.Image;
import bezier.image.PixelArea;
import bezier.image.generated.RasterInstances.Raster1;
import bezier.image.generated.SampleInstances.Sample1;
import bezier.paths.util.BestProjection;
import bezier.points.Vec;

public class ProjectOnPaths {
	public static Raster1 projectDist(Image<Sample1> mask, Paths p){
		PixelArea area = mask.getArea();
		if(area == null){
			throw new Error("Cannot project without mask!");
		}
		double[] data = new double[area.width * area.height];
		int z = 0;
		TPaths prev = new TPaths(0, 0);
		TPaths prevRow =  new TPaths(0, 0);
		for(int y = area.y ; y < area.y + area.height ; y++){
			prev = prevRow;
			for(int x = area.x ; x < area.x + area.width ; x++){
				if(mask.get(x, y).last() <= 0){
					z++;
					continue;
				}
				Vec v = new Vec(x,y);
				Vec pv = p.getAt(prev);
				BestProjection<TPaths> best = new BestProjection<TPaths>(prev,pv.distanceSquared(v),pv);
				p.projectNoSort(v,best);
				if(x == area.x){
					prevRow = prev;
				}
				data[z++] =best.distanceSquaredUpperbound;
			}
		}
		return new Raster1(mask.getArea(), data);
	}
}
