package transform.nonlinear.ffd;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import paths.Constants;
import paths.paths.factory.PathFactory;
import paths.paths.paths.Path;
import paths.paths.paths.simple.Line;
import paths.points.oned.Interval;
import paths.points.twod.BBox;
import paths.points.twod.Vec;

import transform.nonlinear.IDeform;
import transform.nonlinear.ILineTransformer;
import util.BinarySearches;
import util.Tuple;



public class QuadFFD implements IDeform, ILineTransformer{

	final  List<Double> gridX, gridY;
	
	final List<List<Vec>> controlPoints;

	private QuadFFD(List<Double> gridX, List<Double> gridY, List<List<Vec>> controlPoints) {
		this.gridX = gridX;
		this.gridY = gridY;
		this.controlPoints = controlPoints;
		
		
	}
	
	int fromGridToControlPointIndex(int i){
		return 2 * i;
	}

	private static Tuple<Integer,Integer> getSubList(List<Double> coords, Interval interval){
//		for(double d: coords){
//			System.out.print(d + " ");
//		}
//		System.out.printf("Searching for %s \n", interval);
	
		if(interval.high < coords.get(0) || interval.low > coords.get(coords.size()-1)){
			return null;
		}
		int start = BinarySearches.floorBinarySearch(coords, interval.low);
		int end = BinarySearches.floorBinarySearch(coords, interval.high) ;
		if(coords.get(end) < interval.high){
			end+=1;
		}
		end++;
		if(start == end-1){
			if(end == coords.size()){
				start--;
			} else {
				end++;
			}
		}
//		System.out.printf("Found %d %d \n", start, end);
		return new Tuple<Integer, Integer>(start,end);
	}

	public List<List<Vec>> getSubControlPoints(Tuple<Integer,Integer> xSub,
			Tuple<Integer,Integer> ySub){
		List<List<Vec>> res = new ArrayList<List<Vec>>();
		for(int i = fromGridToControlPointIndex(ySub.l); i < fromGridToControlPointIndex(ySub.r); i++){
			res.add(controlPoints.get(i).subList(
					fromGridToControlPointIndex(xSub.l), controlPoints.get(i).size()));
		}
		return res;
	}
	
	@Override
	public IDeform subDeform(BBox b) {
		Tuple<Integer,Integer> xSub = getSubList(gridX, b.xInterval);
		Tuple<Integer,Integer> ySub = getSubList(gridY, b.yInterval);
		if(xSub == null || ySub == null){
			return IdentityDeform.instance;
		}
		return makeFFD(gridX.subList(xSub.l, xSub.r), 
				gridY.subList(ySub.l, ySub.r), xSub,ySub);
	}

	private IDeform makeFFD(List<Double> gridX, List<Double> gridY,
			Tuple<Integer,Integer> xSub,Tuple<Integer,Integer> ySub ) {
		if(gridX.size() == 2 && gridY.size() == 2 && 
				(gridX.get(0) == Double.NEGATIVE_INFINITY || 
				gridX.get(gridX.size()-1) == Double.POSITIVE_INFINITY || 
				gridY.get(0) == Double.NEGATIVE_INFINITY || 
				gridY.get(gridY.size()-1) == Double.POSITIVE_INFINITY)){
			return IdentityDeform.instance;
			}
		return new QuadFFD(gridX, gridY, getSubControlPoints(xSub, ySub));
	}

	@Override
	public boolean isSimple() {
		return isSimpleX() && isSimpleY();
	}

	@Override
	public boolean isSimpleX() {
		return gridX.size() == 2;
	}

	@Override
	public boolean isSimpleY() {
		return gridY.size() == 2;
	}

	@Override
	public double getSplitPointX() {
		return gridX.get(gridX.size()/2);
	}

	@Override
	public double getSplitPointY() {
		return gridY.get(gridY.size()/2);
	}

	public Vec to(Vec d) {
		Vec c = d;
		d = d.sub(new Vec(gridX.get(0),gridY.get(0)));
		double w = gridX.get(1) - gridX.get(0);
		double h = gridY.get(1) - gridY.get(0);
		d = new Vec(d.x / w, d.y / h);
		Vec b = evaluateQuadBezierSurface(d,controlPoints);
//	System.out.printf("Control %d %d!",controlPoints.size(),controlPoints.get(0).size());
//	for(List<Vec> v : controlPoints){
//		for(Vec vv : v){
//			System.out.printf("%s ", vv);
//		}
//		System.out.println();
//	}
//	System.out.println();
//	System.out.println("Grid!");
//	for(double y : gridY){
//		for(double x : gridX){
//			System.out.printf("<%f,%f> ", x ,y);
//		}
//		System.out.println();
//	}
//		System.out.printf("%s  %s %f %f %f %f-> %s\n", c,d, gridX.get(0), gridY.get(0),w,h,  b);
		return b;
//		return c;
	}

	public static Vec evaluateQuadBezierSurface(Vec d, List<List<Vec>> controlPoints) {
		// see http://en.wikipedia.org/wiki/B%C3%A9zier_surface
		double[] bus = getQuadBernstein(d.x); 
		double[] bvs = getQuadBernstein(d.y);
		Vec a = controlPoints.get(0).get(0).mul(bus[0]).add(
		controlPoints.get(0).get(1).mul(bus[1]).add(
		controlPoints.get(0).get(2).mul(bus[2]))).mul(bvs[0]);
		Vec b = controlPoints.get(1).get(0).mul(bus[0]).add(
				controlPoints.get(1).get(1).mul(bus[1]).add(
						controlPoints.get(1).get(2).mul(bus[2]))).mul(bvs[1]);
		Vec c = controlPoints.get(2).get(0).mul(bus[0]).add(
				controlPoints.get(2).get(1).mul(bus[1]).add(
						controlPoints.get(2).get(2).mul(bus[2]))).mul(bvs[2]);
		return a.add(b).add(c);
	}

	private static double[] getQuadBernstein(double x) {
//		double x2 = x * x;
//		double x3 = x2 *x;
//		double rx = 1.0 - x;
//		double rx2 = rx * rx;
//		double rx3 = rx2 * rx;
//		return new double[]{
//				x3,
//				3 * x * rx2,
//				3 * x2 * rx,
//				rx3};
		double x2 = x * x;
		double rx = 1.0 - x;
		double rx2 = rx*rx;
		return new double[]{
				rx2,
				2 * x * rx,
				x2
				};
	}

	@Override
	public Path deform(Path p) {
		return p.transformApproxLines(this);
	}

	@Override
	public Path transform(Line l) {
		return transform(l.start,l.end);
	}
	
	public Path transform(Vec start, Vec end){
		Vec a = to(start);
		Vec b= to(end);
		if(a.distanceSquared(b) <= Constants.MAX_ERROR_FFD){
			return PathFactory.createLine(a, b);
		} else {
			Vec mid = start.add(end).div(2);
			return PathFactory.createAppends
					(transform(start,mid),transform(mid,end));
		}
	}
	
	public static QuadFFD makeQuadFFD(double fadeDist, double[] gridX, double[] gridY, Vec[][] cps){
		List<Double> lgridX = makeCompleteGrid(fadeDist, gridX);
		List<Double> lgridY = makeCompleteGrid(fadeDist, gridY);
		List<List<Vec>> lcps = makeCompleteControlPoints(fadeDist,cps,gridX,gridY);
		return new QuadFFD(lgridX,lgridY,lcps);
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
		List<List<Vec>> res = new ArrayList<List<Vec>>();
		List<Vec> empty = makeEmptyCollumn(gridY.length);
		res.add(empty);
		res.add(empty);
		List<Vec> first = makeNormalCollumn(fadeDist,gridY[0]-fadeDist,gridX);
		res.add(first);
		res.add(interpolateCollumns(first,makeCollumn(fadeDist,gridY[0], gridX, cps[0])));
		for(int i = 0 ; i < cps.length ; i++){
			double y;
			if(i % 2 == 0){
				y = gridY[i/2];
			} else {
				y = (gridY[i/2] + gridY[i/2 + 1]) / 2;
			}
			res.add(makeCollumn(fadeDist,y, gridX, cps[i]));
		}
		List<Vec> end = makeNormalCollumn(fadeDist,gridY[gridY.length-1] + fadeDist,gridX);
		res.add(interpolateCollumns(res.get(res.size()-1),end));
		res.add(end);
		res.add(empty);
		res.add(empty);
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

	private static List<Vec> makeNormalCollumn(double fadeDist,double y, double[] gridX) {
		List<Vec> res = new ArrayList<Vec>();
		res.add(Vec.ZeroVec);
		res.add(Vec.ZeroVec);
		
		res.add(new Vec(gridX[0]- fadeDist,y));
		double prev = gridX[0] - fadeDist;
		for(double d : gridX){
			res.add(new Vec((d+prev)/2,y));
			res.add(new Vec(d,y));
			prev = d;
		}
		double endX = gridX[gridX.length-1]+ fadeDist;
		res.add(new Vec((endX+prev)/2,y));
		res.add(new Vec(endX,y));
		res.add(Vec.ZeroVec);
		res.add(Vec.ZeroVec);
		return res;
	}

	private static List<Vec> makeCollumn(double fadeDist, double y, double[] gridX, Vec[] vecs) {
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
