package paths.paths.paths;

import static paths.paths.results.transformers.TupleTransformers.left;
import static paths.paths.results.transformers.TupleTransformers.right;
import paths.paths.paths.compound.ShapeSet;
import paths.paths.paths.simple.Line;
import paths.paths.results.intersections.IIntersections;
import paths.paths.results.intersections.Intersections;
import paths.paths.results.project.BestProject;
import paths.paths.results.project.BestProjectTup;
import paths.paths.results.transformers.IPathIndexTransformer;
import paths.paths.results.transformers.PathIndexTupleTransformer;
import paths.points.twod.Vec;
import util.Tuple;

public abstract class SplittablePath extends Path {

	public abstract IPathIndexTransformer getLeftTransformer();

	public abstract IPathIndexTransformer getRightTransformer();

	// below: memo's transformers
	public abstract PathIndexTupleTransformer getLeftLeftTransformer();

	public abstract PathIndexTupleTransformer getLeftRightTransformer();

	public abstract PathIndexTupleTransformer getRightLeftTransformer();

	public abstract PathIndexTupleTransformer getRightRightTransformer();

	@Override
	public IIntersections intersection(Path other) {
		return other.intersectionLSplittable(this);
	}

	private IIntersections intersectionLine(Line lhs) {
		if (lhs.overlaps(getBBox())) {
			Tuple<Path, Path> sp = splitSimpler();
			return lhs
					.intersection(sp.l)
					.transform(getRightLeftTransformer())
					.append(lhs.intersection(sp.r).transform(
							getRightRightTransformer()));
		} else {
			return Intersections.NoIntersections;
		}
	}

	@Override
	public IIntersections intersectionLLine(Line lhs) {
		return intersectionLine(lhs);
	}

	@Override
	public IIntersections intersectionLSet(ShapeSet lhs) {
		return lhs.intersectionLSplittable(this).flip();
	}

	@Override
	public IIntersections intersectionLSplittable(SplittablePath lhs) {
		if (!lhs.getBBox().overlaps(getBBox())) {
			return Intersections.NoIntersections;
		}
		if (getBBox().area() > lhs.getBBox().area()) {
			Tuple<Path, Path> simp = splitSimpler();
			IIntersections l = lhs.intersection(simp.l).transform(
					getRightLeftTransformer());
			IIntersections r = lhs.intersection(simp.r).transform(
					getRightRightTransformer());
			return l.append(r);
		} else {
			Tuple<Path, Path> simp = lhs.splitSimpler();
			IIntersections l = simp.l.intersection(this).transform(
					lhs.getLeftLeftTransformer());
			IIntersections r = simp.r.intersection(this).transform(
					lhs.getLeftRightTransformer());
			return l.append(r);

		}
	}

	public BestProject project(double best, Vec p) {
		if (getBBox().getNearestPoint(p).distanceSquared(p) > best) {
			return BestProject.noBestYet;
		}
		Tuple<Path, Path> sp = splitSimpler();
		if (sp.l.getBBox().avgDistSquared(p) < sp.r.getBBox().avgDistSquared(p)) {
			BestProject res = sp.l.project(best, p).transform(
					getLeftTransformer());
			return res.choose(sp.r.project(res.distSquared, p).transform(
					getRightTransformer()));
		} else {
			BestProject res = sp.r.project(best, p).transform(
					getRightTransformer());
			return res.choose(sp.l.project(res.distSquared, p).transform(
					getLeftTransformer()));
		}
	}

	private BestProjectTup projectLine(double best, Line lhs) {
		if (best > lhs.minDistSquaredTo(getBBox())) {
			Tuple<Path, Path> sp = splitSimpler();
			if (lhs.distanceSquared(sp.l.getBBox().getMiddle()) < lhs
					.distanceSquared(sp.r.getBBox().getMiddle())) {
				BestProjectTup fsbest = lhs.project(best, sp.l).transform(
						getRightLeftTransformer());
				return fsbest.choose(lhs.project(fsbest.distSquared, sp.r)
						.transform(right(getRightTransformer())));
			} else {
				BestProjectTup fsbest = lhs.project(best, sp.r).transform(
						getRightRightTransformer());
				return fsbest.choose(lhs.project(fsbest.distSquared, sp.l)
						.transform(right(getLeftTransformer())));
			}
		} else {
			return BestProjectTup.noBestYet;
		}
	}

	@Override
	public BestProjectTup project(double best, Path other) {
		return other.projectLSplittable(best, this);
	}

	@Override
	public BestProjectTup projectLLine(double best, Line lhs) {
		return projectLine(best, lhs);
	}

	@Override
	public BestProjectTup projectLSet(double best, ShapeSet lhs) {
		return lhs.projectLSplittable(best, this).flip();
	}

	public BestProjectTup projectLSplittable(double best, SplittablePath lhs) {
		if (best < minDistTo(lhs.getBBox())) {
			return BestProjectTup.noBestYet;
		}
		if (getBBox().area() > lhs.getBBox().area()) {
			Tuple<Path, Path> sp = splitSimpler();
			if (sp.l.getBBox().avgDistSquared(lhs.getBBox().getMiddle()) < sp.l
					.getBBox().avgDistSquared(lhs.getBBox().getMiddle())) {
				BestProjectTup res = lhs.project(best, sp.l).transform(
						getRightLeftTransformer());
				return res.choose(lhs.project(res.distSquared, sp.r).transform(
						getRightRightTransformer()));
			} else {
				BestProjectTup res = lhs.project(best, sp.r).transform(
						getRightRightTransformer());
				return res.choose(lhs.project(res.distSquared, sp.l).transform(
						getRightLeftTransformer()));
			}
		} else {
			Tuple<Path, Path> sp = lhs.splitSimpler();
			if (sp.l.getBBox().avgDistSquared(getBBox().getMiddle()) < sp.l
					.getBBox().avgDistSquared(getBBox().getMiddle())) {
				BestProjectTup res = sp.l.project(best, this).transform(
						left(lhs.getLeftTransformer()));
				return res.choose(sp.r.project(res.distSquared, this)
						.transform(left(lhs.getRightTransformer())));
			} else {
				BestProjectTup res = sp.r.project(best, this).transform(
						left(lhs.getRightTransformer()));
				return res.choose(sp.l.project(res.distSquared, this)
						.transform(left(lhs.getLeftTransformer())));
			}
		}

	}

	public abstract Tuple<Path, Path> splitSimpler();

}
