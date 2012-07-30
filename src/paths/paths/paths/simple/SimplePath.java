package paths.paths.paths.simple;

import deform.Vec;
import paths.paths.paths.QueryPath;
import paths.paths.paths.SimplyIndexedPath;
import paths.paths.paths.compound.Append;
import paths.paths.results.intersections.IIntersections;
import paths.paths.results.intersections.IntersectionType;
import paths.paths.results.intersections.Intersections;
import paths.paths.results.project.BestProjectTup;
import paths.points.oned.Interval;
import transform.nonlinear.IDeform;
import util.Tuple;

public abstract class SimplePath extends SimplyIndexedPath {

	public SimplePath(Interval tInterval) {
		super(tInterval);
	}

	public Vec getStartPoint() {
		return getAtLocal(0.0);
	}

	public Vec getEndPoint() {
		return getAtLocal(1.0);
	}

	public abstract Vec getAtLocal(double t);

	public abstract Vec getTangentAtLocal(double t);

	@Override
	public Vec getAtSimply(double t) {
		return getAtLocal(tInterval.getFactorForPoint(t));
	}

	@Override
	public Vec getTangentAtSimply(double t) {
		return getTangentAtLocal(tInterval.getFactorForPoint(t));
	}

	protected IIntersections makeIntersectionResult(SimplePath lhs, double tl,
			double tr) {
		SimplePathIndex l = lhs.makeGlobalPathIndexFromLocal(tl);
		SimplePathIndex r = makeGlobalPathIndexFromLocal(tr);
		return new Intersections(l, r, lhs.getAtLocal(tl), getAtLocal(tr),
				lhs.getTangentAtLocal(tl), getTangentAtLocal(tr),
				lhs.getIntersectionType(l), this.getIntersectionType(r));
	}

	private IntersectionType getIntersectionType(SimplePathIndex l) {
		if (l.t == tInterval.high) {
			return IntersectionType.BorderLeft;
		} else if (l.t == tInterval.low) {
			return IntersectionType.BorderRight;
		} else {
			return IntersectionType.Normal;
		}
	}

	public SimplePathIndex makeGlobalPathIndexFromLocal(double t) {
		return new SimplePathIndex(tInterval.getAtFactor(t));
	}

	public BestProjectTup makeBestProject(double dist, SimplePath lhs,
			double tl, double tr) {
		return new BestProjectTup(dist, lhs.makeGlobalPathIndexFromLocal(tl),
				makeGlobalPathIndexFromLocal(tr));
	}

	public int nrChildren() {
		return 0;
	}

	public QueryPath getChild(int i) {
		return null;
	}

	public abstract int awtCurSeg(float[] coords, int x, int y);

	public abstract Tuple<QueryPath, Double> normaliseToLength(double prevLength);

	public abstract SimplePath getWithAdjustedStartPointMono(Vec mid);

	public abstract SimplePath getWithAdjustedEndPointMono(Vec v);

	public abstract Tuple<SimplePath, SimplePath> splitSimp(double midt);

	public abstract double findXFast(double splitPoint);

	public abstract double findYFast(double splitPoint);

}
