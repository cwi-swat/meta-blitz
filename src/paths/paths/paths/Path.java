package paths.paths.paths;

import static util.Util.square;

import java.util.ArrayList;
import java.util.List;

import paths.paths.factory.PathFactory;
import paths.paths.iterators.ConnectedIterator;
import paths.paths.paths.compound.ClosedPath;
import paths.paths.paths.compound.ShapeSet;
import paths.paths.paths.simple.Line;
import paths.paths.paths.simple.curve.Curve;
import paths.paths.results.intersections.IIntersections;
import paths.paths.results.project.BestProject;
import paths.paths.results.project.BestProjectTup;
import paths.points.twod.BBox;
import paths.points.twod.Vec;
import transform.IToTransform;
import transform.nonlinear.IDeform;
import transform.nonlinear.ILineTransformer;
import transform.nonlinear.pathdeform.PathDeform;
import util.Tuple;

public abstract class Path {

	protected BBox bbox;
	private ExtraMemo extraMemo; // additional memo properties that are not used
									// that often

	public abstract BBox makeBBox();

	public BBox getBBox() {
		if (bbox == null) {
			bbox = makeBBox();
		}
		return bbox;
	}

	public abstract Vec getAt(PathIndex t);

	public abstract Vec getTangentAt(PathIndex t);

	public abstract IIntersections intersection(Path other);

	public abstract IIntersections intersectionLLine(Line lhs);

	public IIntersections intersectionLCurve(Curve lhs) {
		return intersectionLSplittable(lhs);
	}

	public abstract IIntersections intersectionLSet(ShapeSet lhs);

	public abstract IIntersections intersectionLSplittable(SplittablePath lhs);

	public BestProject project(Vec p) {
		;
		return project(Double.POSITIVE_INFINITY, p);
	}

	public abstract BestProject project(double best, Vec p);

	public BestProjectTup project(Path other) {
		return project(Double.POSITIVE_INFINITY, other);
	}

	public abstract BestProjectTup project(double best, Path other);

	public abstract BestProjectTup projectLLine(double best, Line lhs);

	public BestProjectTup projectLCurve(double best, Curve lhs) {
		return projectLSplittable(best, lhs);
	}

	public abstract BestProjectTup projectLSet(double best, ShapeSet lhs);

	public abstract BestProjectTup projectLSplittable(double best,
			SplittablePath lhs);

	protected double minDistTo(BBox br) {
		BBox bl = getBBox();
		double xDist = square(bl.xInterval.minDistance(br.xInterval));
		double yDist = square(bl.yInterval.minDistance(br.yInterval));
		return xDist + yDist;
	}

	public abstract int nrChildren();

	public abstract Path getChild(int i);

	public abstract Path transform(IToTransform t);

	public double length() {
		normaliseToLength();
		return extraMemo.length;
	}

	private void makeExtraMemo() {
		if (extraMemo == null) {
			extraMemo = new ExtraMemo();
		}
	}

	public Path normaliseToLength() {
		makeExtraMemo();
		if (extraMemo.lengthNormalized == null) {
			Tuple<Path, Double> tp = normaliseToLength(0);
			extraMemo.lengthNormalized = tp.l;
			extraMemo.length = tp.r;
		}
		return extraMemo.lengthNormalized;
	}

	public abstract Tuple<Path, Double> normaliseToLength(double prevLength);

	public abstract PathIndex minPathIndex();

	public abstract PathIndex maxPathIndex();

	public Path pathDeform(Path p) {
		ConnectedIterator it = new ConnectedIterator(p);
		List<Path> res = new ArrayList<Path>();
		while (it.hasNext()) {
			res.add(deform(new PathDeform(it.next())));
		}
		return PathFactory.createSet(res);
	}

	public abstract Path transformApproxLines(ILineTransformer t);

	public abstract Path deformActual(IDeform p);

	public Path deform(IDeform p) {
		return deformActual(p.subDeform(getBBox()));
	}

	public Vec getStartPoint() {
		return getAt(minPathIndex());
	}

	public Vec getEndPoint() {
		return getAt(maxPathIndex());
	}

	public boolean isClosed() {
		return this instanceof ClosedPath;
	}

}
