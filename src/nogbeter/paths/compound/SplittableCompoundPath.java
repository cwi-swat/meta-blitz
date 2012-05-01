package nogbeter.paths.compound;

import java.util.LinkedList;
import java.util.List;

import bezier.points.Vec;
import bezier.util.Tuple;
import nogbeter.paths.BestProjectTup;
import nogbeter.paths.Path;
import nogbeter.paths.PathIndex;
import nogbeter.paths.compound.CompoundSplitIndex.SplitChoice;
import nogbeter.paths.simple.SimplePath;
import nogbeter.paths.simple.SimplePathIndex;
import nogbeter.paths.simple.lines.DiagonalLine;
import nogbeter.paths.simple.lines.HorizontalLine;
import nogbeter.paths.simple.lines.VerticalLine;
import nogbeter.util.BBox;

public abstract class SplittableCompoundPath<LSimp extends Path, RSimp extends Path>
		extends Path<CompoundSplitIndex, LSimp, RSimp> {

	public final LSimp left;
	public final RSimp right;

	public SplittableCompoundPath(LSimp left, RSimp right) {
		this.left = left;
		this.right = right;
	}

	@Override
	public BBox makeBBox() {
		return left.getBBox().union(right.getBBox());
	}

	@Override
	public Vec getAt(CompoundSplitIndex t) {
		switch (t.choice) {
		case Left:
			return left.getAt(t.next);
		case Right:
			return right.getAt(t.next);
		}
		throw new Error("Unkown split choice:" + this + "\n");
	}

	@Override
	public Vec getTangentAt(CompoundSplitIndex t) {
		switch (t.choice) {
		case Left:
			return left.getTangentAt(t.next);
		case Right:
			return right.getTangentAt(t.next);
		}
		throw new Error("Unkown split choice:" + this + "\n");
	}

	@Override
	public Tuple<LSimp, RSimp> splitSimpler() {
		return new Tuple<LSimp, RSimp>(left, right);
	}

	@Override
	public <RPP, RLSimp extends Path, RRSimp extends Path> Tuple<List<CompoundSplitIndex>, List<RPP>> intersection(
			Path<RPP, RLSimp, RRSimp> other) {
		return other.intersectionLSplittable(this);
	}

	@Override
	public Tuple<List<SimplePathIndex>, List<CompoundSplitIndex>> intersectionLDiaLine(
			DiagonalLine lhs) {
		return lhs.intersectionLSplittable(this).flip();
	}

	@Override
	public Tuple<List<SimplePathIndex>, List<CompoundSplitIndex>> intersectionLHorLine(
			HorizontalLine lhs) {
		return lhs.intersectionLSplittable(this).flip();
	}

	@Override
	public Tuple<List<SimplePathIndex>, List<CompoundSplitIndex>> intersectionLVerLine(
			VerticalLine lhs) {
		return lhs.intersectionLSplittable(this).flip();
	}
	

	@Override
	public Tuple<List<SetIndex>, List<CompoundSplitIndex>> intersectionLSet(
			ShapeSet lhs) {
		return lhs.intersectionLSplittable(this).flip();
	}

	@Override
	public <RPP, RLS extends Path, RRS extends Path> BestProjectTup<CompoundSplitIndex, RPP> project(
			double best, Path<RPP, RLS, RRS> other) {
		return other.projectLSplittable(best, this);
	}

	@Override
	public BestProjectTup<SimplePathIndex, CompoundSplitIndex> projectLDiaLine(
			double best, DiagonalLine lhs) {
		return lhs.projectLSplittable(best, this).flip();
	}

	@Override
	public BestProjectTup<SimplePathIndex, CompoundSplitIndex> projectLHorLine(
			double best, HorizontalLine lhs) {
		return lhs.projectLSplittable(best, this).flip();
	}

	@Override
	public BestProjectTup<SimplePathIndex, CompoundSplitIndex> projectLVerLine(
			double best, VerticalLine lhs) {
		return lhs.projectLSplittable(best, this).flip();
	}

	@Override
	public BestProjectTup<SetIndex, CompoundSplitIndex> projectLSet(
			double best, ShapeSet lhs) {
		return lhs.projectLSplittable(best, this).flip();
	}
	
	@Override
	public <LPI> Tuple<List<LPI>, List<CompoundSplitIndex>> prependLeftListRhs(
			Tuple<List<LPI>, List<? extends PathIndex>> intersections) {
		return prependListRhs(SplitChoice.Left, intersections);
	}

	@Override
	public <LPI> Tuple<List<LPI>, List<CompoundSplitIndex>> prependRightListRhs(
			Tuple<List<LPI>, List<? extends PathIndex>> intersections) {
		return prependListRhs(SplitChoice.Right, intersections);
	}

	private List<CompoundSplitIndex> prependList(SplitChoice choice,
			List<? extends PathIndex> r) {
		List<CompoundSplitIndex> res = new LinkedList<CompoundSplitIndex>();
		for (PathIndex pi : r) {
			res.add(new CompoundSplitIndex(choice, pi));
		}
		return res;
	}

	private <LPI> Tuple<List<LPI>, List<CompoundSplitIndex>> prependListRhs(
			SplitChoice choice,
			Tuple<List<LPI>, List<? extends PathIndex>> intersections) {
		if (intersections.r.isEmpty()) {
			return (Tuple) intersections;
		} else {
			return new Tuple<List<LPI>, List<CompoundSplitIndex>>(
					intersections.l, prependList(choice, intersections.r));
		}
	}

	@Override
	public <LPI> BestProjectTup<LPI, CompoundSplitIndex> prependLeftBestTupRhs(
			BestProjectTup<LPI, ? extends PathIndex> projectSimplerTup) {
		return prependBestTupRhs(SplitChoice.Left, projectSimplerTup);
	}

	@Override
	public <LPI> BestProjectTup<LPI, CompoundSplitIndex> prependRightBestTupRhs(
			BestProjectTup<LPI, ? extends PathIndex> projectSimplerTup) {
		return prependBestTupRhs(SplitChoice.Right, projectSimplerTup);
	}

	private <LPI> BestProjectTup<LPI, CompoundSplitIndex> prependBestTupRhs(
			SplitChoice choice,
			BestProjectTup<LPI, ? extends PathIndex> projectSimplerTup) {
		if (projectSimplerTup.t == null) {
			return (BestProjectTup) projectSimplerTup;
		} else {
			return new BestProjectTup<LPI, CompoundSplitIndex>(
					projectSimplerTup.distSquared, projectSimplerTup.t.l,
					new CompoundSplitIndex(choice, projectSimplerTup.t.r));
		}
	}

	@Override
	public <LPI> BestProjectTup<CompoundSplitIndex, LPI> prependLeftBestTupLhs(
			BestProjectTup<? extends PathIndex, LPI> projectSimplerTup) {
		return prependBestTupLhs(SplitChoice.Left, projectSimplerTup);
	}

	@Override
	public <LPI> BestProjectTup<CompoundSplitIndex, LPI> prependRightBestTupLhs(
			BestProjectTup<? extends PathIndex, LPI> projectSimplerTup) {
		return prependBestTupLhs(SplitChoice.Right, projectSimplerTup);
	}

	private <LPI> BestProjectTup<CompoundSplitIndex, LPI> prependBestTupLhs(
			SplitChoice choice,
			BestProjectTup<? extends PathIndex, LPI> projectSimplerTup) {
		if (projectSimplerTup.t == null) {
			return (BestProjectTup) projectSimplerTup;
		} else {
			return new BestProjectTup<CompoundSplitIndex, LPI>(
					projectSimplerTup.distSquared, new CompoundSplitIndex(
							choice, projectSimplerTup.t.l),
					projectSimplerTup.t.r);
		}
	}
}
