package nogbeter.transform.nonlinear.ffd;

import java.util.ArrayList;
import java.util.List;

import nogbeter.points.twod.Vec;

public class CubicFFD extends QuadFFD{
	
	

	private static final int CubicPadding = 6;

	public static QuadFFD makeQuadFFD(double fadeDist, double[] gridX, double[] gridY, Vec[][] cps){
		List<Double> lgridX = makeCompleteGrid(fadeDist, gridX);
		List<Double> lgridY = makeCompleteGrid(fadeDist, gridY);
		List<List<Vec>> lcps = makeCompleteControlPoints(fadeDist,cps,gridX,gridY);
		return new CubicFFD(lgridX,lgridY,lcps);
	}

	

	private static List<Double> makeCompleteGrid(double fadeDist, double[] grid) {
		List<Double> lgrid = new ArrayList<Double>();
		lgrid.add(Double.NEGATIVE_INFINITY);
		lgrid.add(grid[0] - fadeDist);
		for(double d : grid){
			lgrid.add(d);
		}
		lgrid.add(grid[grid.length-1] + fadeDist);
		lgrid.add(Double.POSITIVE_INFINITY);
		return lgrid;
	}
	
	private static List<List<Vec>> makeCompleteControlPoints(double fadeDist,
			Vec[][] cps, double[] gridX, double[] gridY) {
		for(int y = -CubicPadding ; y < cps.length + CubicPadding; y++){
			for(int x = -CubicPadding ; x < cps.length + CubicPadding; x++){
				getControlPoint(fadeDist,cps,gridX,gridY,y,x);
			}
		}
	}
	
	




	private static Vec getControlPoint(double fadeDist, Vec[][] cps,
			double[] gridX, double[] gridY, int y, int x) {
		if(y < 0 || y > gridY.){
			if(y <= -CubicPadding + 3 ){
				return Vec.ZeroVec;
			} else if( y >= gridY.length -1 + 3){
				return Vec.ZeroVec;
			} else 
		}
		
	}



	private static List<Vec> makeLeftContinusCol(double fadeDist, Vec[][] cps) {
		List<Vec> res = new ArrayList<Vec>();
		res.add(Vec.ZeroVec);
		res.add(Vec.ZeroVec);
		res.add(Vec.ZeroVec);
		for(int i = 0 ; i < cps.length; i++){
			res.add(cps[1][i].mirror(cps[0][i]));
		}
		res.add(Vec.ZeroVec);
		res.add(Vec.ZeroVec);
		res.add(Vec.ZeroVec);
		return res;
	}
	
	private static List<Vec> makeRightContinusCol(double fadeDist, Vec[][] cps) {
		List<Vec> res = new ArrayList<Vec>();
		res.add(Vec.ZeroVec);
		res.add(Vec.ZeroVec);
		res.add(Vec.ZeroVec);
		for(int i = 0 ; i < cps.length; i++){
			res.add(cps[cps.length-1][i].mirror(cps[cps.length-2][i]));
		}
		res.add(Vec.ZeroVec);
		res.add(Vec.ZeroVec);
		res.add(Vec.ZeroVec);
		return res;
	}



	private static List<Vec> interpolateCollumns(List<Vec> first,
			List<Vec> second) {
		List<Vec> res = new ArrayList<Vec>();
		for(int i = 0; i < first.size() ; i++){
			res.add(first.get(i).interpolate(0.5, second.get(i)));
		}
		return res;
	}

	private static List<Vec> makeEmptyCollumn(int gridSize){
		List<Vec> res=  new ArrayList<Vec>();
		for(int i = 0 ; i < 7 + gridSize*2  ; i++){
			res.add(Vec.ZeroVec);
		}
		return res;
	}
	
	private static double lerp(double x, double t, double x2){
		return x * (1-t) + x2 * t;
	}

	private static List<Vec> makeNormalCollumn(double fadeDist,double y, double[] gridX) {
		List<Vec> res = new ArrayList<Vec>();
		res.add(Vec.ZeroVec);
		res.add(Vec.ZeroVec);
		res.add(Vec.ZeroVec);
		res.add(new Vec(gridX[0]- fadeDist,y));
		double prev = gridX[0] - fadeDist;
		for(double d : gridX){
			res.add(new Vec(lerp(prev,1.0/3.0,d),y));
			res.add(new Vec(lerp(prev,2.0/3.0,d),y));
			res.add(new Vec(d,y));
			prev = d;
		}
		double d = gridX[gridX.length-1]+ fadeDist;
		res.add(new Vec(lerp(prev,1.0/3.0,d),y));
		res.add(new Vec(lerp(prev,2.0/3.0,d),y));
		res.add(new Vec(d,y));
		res.add(Vec.ZeroVec);
		res.add(Vec.ZeroVec);
		res.add(Vec.ZeroVec);
		return res;
	}

	private static List<Vec> makeCollumn(double fadeDist, double y, double[] gridX, Vec[][] cps, int i, int j) {
		List<Vec> res = new ArrayList<Vec>();
		res.add(Vec.ZeroVec);
		res.add(Vec.ZeroVec);
		Vec start = new Vec(gridX[0]-fadeDist,y);
		res.add(start);
		res.add(start.interpolate(0.5, vecs[0]));
		for(Vec v : vecs){
			res.add(v);
		}
		Vec end = new Vec(gridX[gridX.length-1]+fadeDist,y);
		res.add(end.interpolate(0.5, vecs[vecs.length-1]));
		res.add(end);
		res.add(Vec.ZeroVec);
		res.add(Vec.ZeroVec);
		return res;
	}

}
