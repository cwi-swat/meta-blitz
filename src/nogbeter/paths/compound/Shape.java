package nogbeter.paths.compound;

import java.util.List;

import bezier.points.Vec;
import bezier.util.Tuple;
import nogbeter.paths.BestProjectTup;
import nogbeter.paths.Path;
import nogbeter.paths.PathIndex;
import nogbeter.paths.simple.SimplePathIndex;
import nogbeter.paths.simple.lines.DiagonalLine;
import nogbeter.paths.simple.lines.HorizontalLine;
import nogbeter.paths.simple.lines.VerticalLine;
import nogbeter.util.BBox;


public class Shape<LSimp extends Path, RSimp extends Path> extends Path<ShapeIndex, LSimp, RSimp> {

	LSimp border;
	RSimp inner;
	
	@Override
	public BBox makeBBox() {
		return border.getBBox();
	}

	@Override
	public Vec getAt(ShapeIndex t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Vec getTangentAt(ShapeIndex t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Tuple<LSimp, RSimp> splitSimpler() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <RPP, RLSimp extends Path, RRSimp extends Path> Tuple<List<ShapeIndex>, List<RPP>> intersection(
			Path<RPP, RLSimp, RRSimp> other) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Tuple<List<SimplePathIndex>, List<ShapeIndex>> intersectionLDiaLine(
			DiagonalLine lhs) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Tuple<List<SimplePathIndex>, List<ShapeIndex>> intersectionLHorLine(
			HorizontalLine lhs) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Tuple<List<SimplePathIndex>, List<ShapeIndex>> intersectionLVerLine(
			VerticalLine lhs) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <LPI> Tuple<List<LPI>, List<ShapeIndex>> prependRightListRhs(
			Tuple<List<LPI>, List<? extends PathIndex>> intersections) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <LPI> Tuple<List<LPI>, List<ShapeIndex>> prependLeftListRhs(
			Tuple<List<LPI>, List<? extends PathIndex>> intersections) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <RPP, RLS extends Path, RRS extends Path> BestProjectTup<ShapeIndex, RPP> project(
			double best, Path<RPP, RLS, RRS> other) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BestProjectTup<SimplePathIndex, ShapeIndex> projectLDiaLine(
			double best, DiagonalLine lhs) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BestProjectTup<SimplePathIndex, ShapeIndex> projectLHorLine(
			double best, HorizontalLine lhs) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BestProjectTup<SimplePathIndex, ShapeIndex> projectLVerLine(
			double best, VerticalLine lhs) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <LPI> BestProjectTup<LPI, ShapeIndex> prependLeftBestTupRhs(
			BestProjectTup<LPI, ? extends PathIndex> projectSimplerTup) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <LPI> BestProjectTup<LPI, ShapeIndex> prependRightBestTupRhs(
			BestProjectTup<LPI, ? extends PathIndex> projectSimplerTup) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <LPI> BestProjectTup<ShapeIndex, LPI> prependLeftBestTupLhs(
			BestProjectTup<? extends PathIndex, LPI> projectSimplerTup) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <LPI> BestProjectTup<ShapeIndex, LPI> prependRightBestTupLhs(
			BestProjectTup<? extends PathIndex, LPI> projectSimplerTup) {
		// TODO Auto-generated method stub
		return null;
	}

}
