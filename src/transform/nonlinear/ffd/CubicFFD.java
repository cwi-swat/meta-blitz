package transform.nonlinear.ffd;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import deform.BBox;
import deform.Vec;

import paths.Constants;
import paths.paths.factory.PathFactory;
import paths.paths.paths.Path;
import paths.paths.paths.simple.Line;
import paths.points.oned.Interval;
import transform.nonlinear.IDeform;
import transform.nonlinear.ILineTransformer;
import util.BinarySearches;
import util.Tuple;

public class CubicFFD implements IDeform, ILineTransformer {

	final List<Double> gridX, gridY;

	final List<List<Vec>> controlPoints;

	public CubicFFD(double fadeDist, List<Double> gridX, List<Double> gridY,
			List<List<Vec>> controlPoints) {
		CubicFFDPadder pad = new CubicFFDPadder(gridX, gridY, controlPoints,
				fadeDist);
		pad.padAll();

		this.gridX = pad.gridX;
		this.gridY = pad.gridY;
		this.controlPoints = pad.controlPoints;
		// for(List<Vec> vv : this.controlPoints){
		// System.out.printf("Size %d ",vv.size());
		// for(Vec v : vv){
		// System.out.print(v + " ");
		// }
		// System.out.println();
		// }
		// System.out.println();
		// System.out.println();
	}

	public CubicFFD(List<Double> gridX, List<Double> gridY,
			List<List<Vec>> controlPoints) {
		this.gridX = gridX;
		this.gridY = gridY;
		this.controlPoints = controlPoints;
	}

	int fromGridToControlPointIndex(int i) {
		return 3 * i;
	}

	private static Tuple<Integer, Integer> getSubList(List<Double> coords,
			Interval interval) {
		if (interval.high < coords.get(0)
				|| interval.low > coords.get(coords.size() - 1)) {
			return null;
		}
		int start = BinarySearches.floorBinarySearch(coords, interval.low);
		int end = BinarySearches.floorBinarySearch(coords, interval.high);
		if (coords.get(end) < interval.high) {
			end += 1;
		}
		end++;
		if (start == end - 1) {
			if (end == coords.size()) {
				start--;
			} else {
				end++;
			}
		}
		return new Tuple<Integer, Integer>(start, end);
	}

	public List<List<Vec>> getSubControlPoints(Tuple<Integer, Integer> xSub,
			Tuple<Integer, Integer> ySub) {
		List<List<Vec>> res = new ArrayList<List<Vec>>();
		for (int i = fromGridToControlPointIndex(ySub.l); i < fromGridToControlPointIndex(ySub.r) - 2; i++) {
			res.add(controlPoints.get(i).subList(
					fromGridToControlPointIndex(xSub.l),
					controlPoints.get(i).size()));
		}
		return res;
	}

	@Override
	public IDeform subDeform(BBox b) {
		Tuple<Integer, Integer> xSub = getSubList(gridX, b.xInterval);
		Tuple<Integer, Integer> ySub = getSubList(gridY, b.yInterval);
		if (xSub == null || ySub == null) {
			return IdentityDeform.instance;
		}
		return makeFFD(gridX.subList(xSub.l, xSub.r),
				gridY.subList(ySub.l, ySub.r), xSub, ySub);
	}

	private IDeform makeFFD(List<Double> gridX, List<Double> gridY,
			Tuple<Integer, Integer> xSub, Tuple<Integer, Integer> ySub) {
		if (gridX.size() == 2
				&& gridY.size() == 2
				&& (gridX.get(0) == Double.NEGATIVE_INFINITY
						|| gridX.get(gridX.size() - 1) == Double.POSITIVE_INFINITY
						|| gridY.get(0) == Double.NEGATIVE_INFINITY || gridY
						.get(gridY.size() - 1) == Double.POSITIVE_INFINITY)) {
			return IdentityDeform.instance;
		}
		return new CubicFFD(gridX, gridY, getSubControlPoints(xSub, ySub));
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
		return gridX.get(gridX.size() / 2);
	}

	@Override
	public double getSplitPointY() {
		return gridY.get(gridY.size() / 2);
	}

	public Vec to(Vec d) {
		Vec c = d;
		d = d.sub(new Vec(gridX.get(0), gridY.get(0)));
		double w = gridX.get(1) - gridX.get(0);
		double h = gridY.get(1) - gridY.get(0);
		d = new Vec(d.x / w, d.y / h);
		Vec b = evaluateCubicBezierSurface(d, controlPoints);
		// System.out.printf("Control %d %d!",controlPoints.size(),controlPoints.get(0).size());
		// for(List<Vec> v : controlPoints){
		// for(Vec vv : v){
		// System.out.printf("%s ", vv);
		// }
		// System.out.println();
		// }
		// System.out.println();
		// System.out.println("Grid!");
		// for(double y : gridY){
		// for(double x : gridX){
		// System.out.printf("<%f,%f> ", x ,y);
		// }
		// System.out.println();
		// }
		// System.out.printf("%s  %s %f %f %f %f-> %s\n", c,d, gridX.get(0),
		// gridY.get(0),w,h, b);
		return b;
		// return c;
	}

	public static Vec evaluateCubicBezierSurface(Vec t,
			List<List<Vec>> controlPoints) {
		// see http://en.wikipedia.org/wiki/B%C3%A9zier_surface
		double[] bus = getCubicBernstein(t.x);
		double[] bvs = getCubicBernstein(t.y);
		Vec a = controlPoints
				.get(0)
				.get(0)
				.mul(bus[0])
				.add(controlPoints.get(0).get(1).mul(bus[1])
						.add(controlPoints.get(0).get(2).mul(bus[2]))
						.add(controlPoints.get(0).get(3).mul(bus[3])))
				.mul(bvs[0]);
		Vec b = controlPoints
				.get(1)
				.get(0)
				.mul(bus[0])
				.add(controlPoints.get(1).get(1).mul(bus[1])
						.add(controlPoints.get(1).get(2).mul(bus[2]))
						.add(controlPoints.get(1).get(3).mul(bus[3])))
				.mul(bvs[1]);
		Vec c = controlPoints
				.get(2)
				.get(0)
				.mul(bus[0])
				.add(controlPoints.get(2).get(1).mul(bus[1])
						.add(controlPoints.get(2).get(2).mul(bus[2]))
						.add(controlPoints.get(2).get(3).mul(bus[3])))
				.mul(bvs[2]);
		Vec d = controlPoints
				.get(3)
				.get(0)
				.mul(bus[0])
				.add(controlPoints.get(3).get(1).mul(bus[1])
						.add(controlPoints.get(3).get(2).mul(bus[2]))
						.add(controlPoints.get(3).get(3).mul(bus[3])))
				.mul(bvs[3]);
		return a.add(b).add(c).add(d);
	}

	private static double[] getCubicBernstein(double x) {
		double x2 = x * x;
		double x3 = x2 * x;
		double rx = 1.0 - x;
		double rx2 = rx * rx;
		double rx3 = rx2 * rx;
		return new double[] { rx3, 3 * x * rx2, 3 * x2 * rx, x3 };
	}

	@Override
	public Path deform(Path p) {
		return p.transformApproxLines(this);
	}

	@Override
	public Path transform(Line l) {
		return transform(l.start, l.end);
	}

	public Path transform(Vec start, Vec end) {
		Vec a = to(start);
		Vec b = to(end);
		if (a.distanceSquared(b) <= Constants.MAX_ERROR_FFD) {
			return PathFactory.createLine(a, b);
		} else {
			Vec mid = start.add(end).div(2);
			return PathFactory.createAppends(transform(start, mid),
					transform(mid, end));
		}
	}

	public static IDeform makeFFD(double fadeDist, Double[] gridX,
			Double[] gridY, Vec[][] cps) {
		List<List<Vec>> controls = new ArrayList<List<Vec>>();
		for (Vec[] cp : cps) {
			controls.add(Arrays.asList(cp));
		}
		return new CubicFFD(fadeDist, Arrays.asList(gridX),
				Arrays.asList(gridY), controls);
	}

}
