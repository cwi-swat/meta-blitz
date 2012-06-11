package transform.nonlinear.ffd;

import java.util.ArrayList;
import java.util.List;

import paths.points.twod.Vec;

public class CubicFFDPadder {
	List<Double> gridX, gridY;
	List<List<Vec>> controlPoints;
	double fadeDist;

	public CubicFFDPadder(List<Double> gridX, List<Double> gridY,
			List<List<Vec>> controlPoints, double fadeDist) {
		this.gridX = gridX;
		this.gridY = gridY;
		this.controlPoints = controlPoints;
		this.fadeDist = fadeDist;
	}

	public void padAll() {
		padControls();
		gridX = padGrid(fadeDist, gridX);
		gridY = padGrid(fadeDist, gridY);
	}

	private List<Double> padGrid(double fadeDist2, List<Double> gridX) {
		List<Double> res = new ArrayList<Double>();
		res.add(Double.NEGATIVE_INFINITY);
		res.add(gridX.get(0) - fadeDist);
		res.addAll(gridX);
		res.add(gridX.get(gridX.size() - 1) + fadeDist);
		res.add(Double.POSITIVE_INFINITY);
		return res;
	}

	void padControls() {
		List<List<Vec>> cornerLU = getCornerLeftUp();
		List<List<Vec>> cornerLD = getCornerLeftDown();
		List<List<Vec>> cornerRU = getCornerRightUp();
		List<List<Vec>> cornerRD = getCornerRightDown();
		List<List<Vec>> left = getLeftPadding();
		List<List<Vec>> up = getUpperPadding();
		List<List<Vec>> right = getRightPadding();
		List<List<Vec>> down = getBottomPadding();
		List<List<Vec>> oldControls = this.controlPoints;
		controlPoints = new ArrayList<List<Vec>>();
		for (int i = 0; i < cornerLU.size(); i++) {
			List<Vec> row = new ArrayList<Vec>();
			row.addAll(cornerLU.get(i));
			row.addAll(up.get(i));
			row.addAll(cornerRU.get(i));
			controlPoints.add(row);
		}
		for (int i = 0; i < oldControls.size(); i++) {
			List<Vec> row = new ArrayList<Vec>();
			row.addAll(left.get(i));
			row.addAll(oldControls.get(i));
			row.addAll(right.get(i));
			controlPoints.add(row);
		}
		for (int i = 0; i < cornerLD.size(); i++) {
			List<Vec> row = new ArrayList<Vec>();
			row.addAll(cornerLD.get(i));
			row.addAll(down.get(i));
			row.addAll(cornerRD.get(i));
			controlPoints.add(row);
		}

	}

	List<List<Vec>> transpose(List<List<Vec>> l) {
		List<List<Vec>> res = new ArrayList<List<Vec>>();
		for (int i = 0; i < l.get(0).size(); i++) {
			List<Vec> c = new ArrayList<Vec>();
			for (int j = 0; j < l.size(); j++) {
				c.add(l.get(j).get(i));
			}
			res.add(c);
		}
		return res;
	}

	List<List<Vec>> getCornerLeftUp() {
		List<List<Vec>> res = new ArrayList<List<Vec>>();
		List<Vec> infinitePad = repeat(Vec.ZeroVec, 6);
		res.add(infinitePad);
		res.add(infinitePad);
		res.add(infinitePad);
		List<Vec> l1 = new ArrayList<Vec>();
		l1.addAll(repeat(Vec.ZeroVec, 3));
		double y = gridY.get(0) - fadeDist;
		Double gridXLeft = gridX.get(0);
		l1.add(new Vec(gridXLeft - fadeDist, y));
		l1.add(new Vec(gridXLeft - (2.0 / 3.0) * fadeDist, y));
		l1.add(new Vec(gridXLeft - (1.0 / 3.0) * fadeDist, y));
		res.add(l1);
		l1 = new ArrayList<Vec>();
		l1.addAll(repeat(Vec.ZeroVec, 3));
		y = gridY.get(0) - (2.0 / 3.0) * fadeDist;
		l1.add(new Vec(gridXLeft - fadeDist, y));
		l1.add(new Vec(gridXLeft - (2.0 / 3.0) * fadeDist, y));
		l1.add(new Vec(gridXLeft - (1.0 / 3.0) * fadeDist, y));
		res.add(l1);
		l1 = new ArrayList<Vec>();
		l1.addAll(repeat(Vec.ZeroVec, 3));
		y = gridY.get(0) - (1.0 / 3.0) * fadeDist;
		l1.add(new Vec(gridXLeft - fadeDist, y));
		l1.add(new Vec(gridXLeft - (2.0 / 3.0) * fadeDist, y));
		l1.add(controlPoints.get(0).get(0).mirror(controlPoints.get(1).get(1)));
		res.add(l1);
		return res;
	}

	List<List<Vec>> getCornerLeftDown() {
		List<List<Vec>> res = new ArrayList<List<Vec>>();
		List<Vec> infinitePad = repeat(Vec.ZeroVec, 6);

		List<Vec> l1 = new ArrayList<Vec>();
		double y;
		l1 = new ArrayList<Vec>();
		l1.addAll(repeat(Vec.ZeroVec, 3));
		Double gridYDown = gridY.get(gridY.size() - 1);
		y = gridYDown + (1.0 / 3.0) * fadeDist;
		Double gridXLeft = gridX.get(0);
		l1.add(new Vec(gridXLeft - fadeDist, y));
		l1.add(new Vec(gridXLeft - (2.0 / 3.0) * fadeDist, y));
		Vec a = controlPoints.get(controlPoints.size() - 1).get(0);
		Vec b = controlPoints.get(controlPoints.size() - 2).get(1);
		l1.add(a.mirror(b));
		res.add(l1);
		l1 = new ArrayList<Vec>();
		y = gridYDown + (2.0 / 3.0) * fadeDist;
		l1.addAll(repeat(Vec.ZeroVec, 3));
		l1.add(new Vec(gridXLeft - fadeDist, y));
		l1.add(new Vec(gridXLeft - (2.0 / 3.0) * fadeDist, y));
		l1.add(new Vec(gridXLeft - (1.0 / 3.0) * fadeDist, y));
		res.add(l1);
		y = gridYDown + fadeDist;
		l1 = new ArrayList<Vec>();
		l1.addAll(repeat(Vec.ZeroVec, 3));
		l1.add(new Vec(gridXLeft - fadeDist, y));
		l1.add(new Vec(gridXLeft - (2.0 / 3.0) * fadeDist, y));
		l1.add(new Vec(gridXLeft - (1.0 / 3.0) * fadeDist, y));
		res.add(l1);
		res.add(infinitePad);
		res.add(infinitePad);
		res.add(infinitePad);
		return res;
	}

	List<List<Vec>> getCornerRightUp() {
		List<List<Vec>> res = new ArrayList<List<Vec>>();
		List<Vec> infinitePad = repeat(Vec.ZeroVec, 6);
		res.add(infinitePad);
		res.add(infinitePad);
		res.add(infinitePad);
		List<Vec> l1 = new ArrayList<Vec>();
		Double gridYUp = gridY.get(0);
		double y = gridYUp - fadeDist;
		Double gridXRight = gridX.get(gridX.size() - 1);
		l1.add(new Vec(gridXRight + (1.0 / 3.0) * fadeDist, y));
		l1.add(new Vec(gridXRight + (2.0 / 3.0) * fadeDist, y));
		l1.add(new Vec(gridXRight + fadeDist, y));
		l1.addAll(repeat(Vec.ZeroVec, 3));
		res.add(l1);
		l1 = new ArrayList<Vec>();
		y = gridYUp - (2.0 / 3.0) * fadeDist;
		l1.add(new Vec(gridXRight + (1.0 / 3.0) * fadeDist, y));
		l1.add(new Vec(gridXRight + (2.0 / 3.0) * fadeDist, y));
		l1.add(new Vec(gridXRight + fadeDist, y));
		l1.addAll(repeat(Vec.ZeroVec, 3));
		res.add(l1);
		l1 = new ArrayList<Vec>();
		y = gridYUp - (1.0 / 3.0) * fadeDist;
		Vec a = controlPoints.get(0).get(controlPoints.get(0).size() - 1);
		Vec b = controlPoints.get(1).get(controlPoints.get(1).size() - 2);
		l1.add(a.mirror(b));
		l1.add(new Vec(gridXRight + (2.0 / 3.0) * fadeDist, y));
		l1.add(new Vec(gridXRight + fadeDist, y));
		l1.addAll(repeat(Vec.ZeroVec, 3));
		res.add(l1);
		return res;
	}

	List<List<Vec>> getCornerRightDown() {
		List<List<Vec>> res = new ArrayList<List<Vec>>();
		List<Vec> infinitePad = repeat(Vec.ZeroVec, 6);
		List<Vec> l1 = new ArrayList<Vec>();
		double y;
		l1 = new ArrayList<Vec>();
		Double gridYDown = gridY.get(gridY.size() - 1);
		y = gridYDown + (1.0 / 3.0) * fadeDist;
		Vec a = controlPoints.get(controlPoints.size() - 1).get(
				controlPoints.get(controlPoints.size() - 1).size() - 1);
		Vec b = controlPoints.get(controlPoints.size() - 2).get(
				controlPoints.get(controlPoints.size() - 2).size() - 2);
		l1.add(a.mirror(b));
		Double gridXRight = gridX.get(gridX.size() - 1);
		l1.add(new Vec(gridXRight + (2.0 / 3.0) * fadeDist, y));
		l1.add(new Vec(gridXRight + fadeDist, y));
		l1.addAll(repeat(Vec.ZeroVec, 3));
		res.add(l1);
		l1 = new ArrayList<Vec>();
		y = gridYDown + (2.0 / 3.0) * fadeDist;
		l1.add(new Vec(gridXRight + (1.0 / 3.0) * fadeDist, y));
		l1.add(new Vec(gridXRight + (2.0 / 3.0) * fadeDist, y));
		l1.add(new Vec(gridXRight + fadeDist, y));
		l1.addAll(repeat(Vec.ZeroVec, 3));
		res.add(l1);
		l1 = new ArrayList<Vec>();
		y = gridYDown + fadeDist;
		l1.add(new Vec(gridXRight + (1.0 / 3.0) * fadeDist, y));
		l1.add(new Vec(gridXRight + (2.0 / 3.0) * fadeDist, y));
		l1.add(new Vec(gridXRight + fadeDist, y));
		l1.addAll(repeat(Vec.ZeroVec, 3));
		res.add(l1);
		res.add(infinitePad);
		res.add(infinitePad);
		res.add(infinitePad);
		return res;
	}

	List<List<Vec>> getLeftPadding() {
		List<List<Vec>> res = new ArrayList<List<Vec>>();
		List<Vec> infinitePad = repeat(Vec.ZeroVec, controlPoints.size());
		res.add(infinitePad);
		res.add(infinitePad);
		res.add(infinitePad);
		res.add(makeWithYes(gridX.get(0) - fadeDist));
		res.add(makeWithYes(gridX.get(0) - (2.0 / 3.0) * fadeDist));
		List<Vec> mirr = new ArrayList<Vec>();
		for (int y = 0; y < controlPoints.size(); y++) {
			mirr.add(controlPoints.get(y).get(0)
					.mirror(controlPoints.get(y).get(1)));
		}
		res.add(mirr);
		return transpose(res);
	}

	List<List<Vec>> getRightPadding() {
		List<List<Vec>> res = new ArrayList<List<Vec>>();
		List<Vec> infinitePad = repeat(Vec.ZeroVec, controlPoints.size());

		List<Vec> mirr = new ArrayList<Vec>();

		for (int y = 0; y < controlPoints.size(); y++) {
			List<Vec> controlRow = controlPoints.get(y);
			mirr.add(controlRow.get(controlRow.size() - 1).mirror(
					controlRow.get(controlRow.size() - 2)));
		}
		res.add(mirr);
		res.add(makeWithYes(gridX.get(gridX.size() - 1) + (2.0 / 3.0)
				* fadeDist));
		res.add(makeWithYes(gridX.get(gridX.size() - 1) + fadeDist));
		res.add(infinitePad);
		res.add(infinitePad);
		res.add(infinitePad);
		return transpose(res);
	}

	List<List<Vec>> getUpperPadding() {
		List<List<Vec>> res = new ArrayList<List<Vec>>();
		List<Vec> infinitePad = repeat(Vec.ZeroVec, controlPoints.size());
		res.add(infinitePad);
		res.add(infinitePad);
		res.add(infinitePad);
		res.add(makeWithXes(gridY.get(0) - fadeDist));
		res.add(makeWithXes(gridY.get(0) - (2.0 / 3.0) * fadeDist));
		List<Vec> mirr = new ArrayList<Vec>();
		List<Vec> upperControls = controlPoints.get(0);
		List<Vec> upperControls2 = controlPoints.get(1);
		for (int x = 0; x < controlPoints.get(0).size(); x++) {
			mirr.add(upperControls.get(x).mirror(upperControls2.get(x)));
		}
		res.add(mirr);
		return res;
	}

	List<List<Vec>> getBottomPadding() {
		List<List<Vec>> res = new ArrayList<List<Vec>>();
		List<Vec> mirr = new ArrayList<Vec>();
		List<Vec> bottomControls = controlPoints.get(controlPoints.size() - 1);
		List<Vec> bottomControls2 = controlPoints.get(controlPoints.size() - 2);
		for (int x = 0; x < controlPoints.get(0).size(); x++) {
			mirr.add(bottomControls.get(x).mirror(bottomControls2.get(x)));
		}
		res.add(mirr);
		res.add(makeWithXes(gridY.get(gridY.size() - 1) + (2.0 / 3.0)
				* fadeDist));
		res.add(makeWithXes(gridY.get(gridY.size() - 1) + fadeDist));
		List<Vec> infinitePad = repeat(Vec.ZeroVec, controlPoints.size());
		res.add(infinitePad);
		res.add(infinitePad);
		res.add(infinitePad);
		return res;
	}

	List<Vec> makeWithXes(double y) {
		List<Vec> res = new ArrayList<Vec>();
		double prev = gridX.get(0);
		res.add(new Vec(prev, y));
		for (int i = 1; i < gridX.size(); i++) {
			double cur = gridX.get(i);
			res.add(new Vec((2.0 / 3.0) * prev + (1.0 / 3.0) * cur, y));
			res.add(new Vec((1.0 / 3.0) * prev + (2.0 / 3.0) * cur, y));
			res.add(new Vec(cur, y));
			prev = cur;
		}
		return res;
	}

	List<Vec> makeWithYes(double x) {
		List<Vec> res = new ArrayList<Vec>();
		double prev = gridY.get(0);
		res.add(new Vec(x, prev));
		for (int i = 1; i < gridX.size(); i++) {
			double cur = gridY.get(i);
			res.add(new Vec(x, (2.0 / 3.0) * prev + (1.0 / 3.0) * cur));
			res.add(new Vec(x, (1.0 / 3.0) * prev + (2.0 / 3.0) * cur));
			res.add(new Vec(x, cur));
			prev = cur;
		}
		return res;
	}

	List<Vec> repeat(Vec v, int nr) {
		List<Vec> res = new ArrayList<Vec>();
		for (int i = 0; i < nr; i++) {
			res.add(v);
		}
		return res;
	}

}
