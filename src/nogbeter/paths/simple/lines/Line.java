package nogbeter.paths.simple.lines;

import java.util.List;

import nogbeter.paths.BestProjectTup;
import nogbeter.paths.Path;
import nogbeter.paths.PathFactory;
import nogbeter.paths.PathIndex;
import nogbeter.paths.compound.CompoundSplitIndex;
import nogbeter.paths.compound.SplittableCompound;
import nogbeter.paths.simple.SimplePath;
import nogbeter.paths.simple.SimplePathIndex;
import nogbeter.util.BBox;
import nogbeter.util.Interval;
import bezier.points.Vec;
import bezier.util.Tuple;
import bezier.util.Util;


public abstract class Line extends SimplePath {

	public Line(Interval tInterval) {
		super(tInterval);
	}
	
	@Override
	public BBox makeBBox(){
		return BBox.fromPoints(getStartPoint(), getEndPoint());
	}
	@Override
	public SimplePath getWithAdjustedStartPoint(Vec newStartPoint) {
		return PathFactory.createLine(newStartPoint, getEndPoint());
	}
	
	abstract double minDistSquaredTo(BBox b);
	

	@Override
	public <LPI,LLSimp extends Path,LRSimp extends Path> 
			Tuple<List<LPI>, List<SimplePathIndex>> 
		intersectionLSplittable(
			Path<LPI,LLSimp,LRSimp> lhs){
		if(overlaps(lhs.getBBox())){
			Tuple<LLSimp,LRSimp> sp = lhs.splitSimpler();
			return Util.appendTupList(
					sp.l.intersection(this),
					sp.r.intersection(this));
		} else {
			return Util.emptyTupleList;
		}
	}
	
	@Override
	public <LPI,LLS extends Path,LRS extends Path>  BestProjectTup<LPI, SimplePathIndex> projectLSplittable(
			double best,
			Path<LPI,LLS,LRS> lhs) {
		if(best > minDistSquaredTo(lhs.getBBox())){
			Tuple<LLS,LRS> sp = lhs.splitSimpler();
			if(distanceSquared(sp.l.getBBox().getMiddle()) <
					distanceSquared(sp.r.getBBox().getMiddle())){
				BestProjectTup<LPI, SimplePathIndex> fsbest = 
						lhs.prependLeftBestTupLhs(sp.l.project(best, this));
				return fsbest.choose(lhs.prependRightBestTupLhs(sp.r.project(fsbest.distSquared, this)));
			} else {
				BestProjectTup<LPI, SimplePathIndex> fsbest =
						lhs.prependRightBestTupLhs(sp.r.project(best, this));
				return fsbest.choose(
						lhs.prependLeftBestTupLhs(sp.l.project(fsbest.distSquared, this)));
			}
		} else {
			return BestProjectTup.noBestYet;
		}
	}

	abstract double distanceSquared(Vec v) ;
	
	abstract boolean overlaps(BBox b) ;
	
	@Override
	public Tuple<SimplePath,SimplePath> splitSimpler() {
		throw new Error("Cannot make" + this + "simpler!");
	}
	
	public static Line createLine(Vec start, Vec end, Interval interval){
		if (start.x == end.x) {
			if (start.y < end.y) {
				return new VerticalUDLine(start.x, new Interval(
						start.y, end.y), interval);
			} else {
				return new VerticalDULine(start.x, new Interval(end.y,
						start.y), interval);
			}
		} else if (start.y == end.y) {
			if (start.x < end.x) {
				return new HorizontalLRLine(new Interval(start.x,
						end.x), start.y, interval);
			} else {
				return new HorizontalRLLine(new Interval(start.x,
						end.x), start.y, interval);
			}
		} else {
			return new DiagonalLine(start, end, interval);
		}
	}
	

	@Override
	public <LPI> Tuple<List<LPI>, List<SimplePathIndex>> prependRightListRhs(
			Tuple<List<LPI>, List<? extends PathIndex>> intersections) {
		throw new Error("Cannot make line simpler!");
	}

	@Override
	public <LPI> Tuple<List<LPI>, List<SimplePathIndex>> prependLeftListRhs(
			Tuple<List<LPI>, List<? extends PathIndex>> intersections) {
		throw new Error("Cannot make line simpler!");
	}

	@Override
	public <LPI> BestProjectTup<LPI, SimplePathIndex> prependLeftBestTupRhs(
			BestProjectTup<LPI,? extends PathIndex> projectSimplerTup) {
		throw new Error("Cannot make line simpler!");
	}

	@Override
	public <LPI> BestProjectTup<LPI, SimplePathIndex> prependRightBestTupRhs(
			BestProjectTup<LPI,? extends PathIndex> projectSimplerTup) {
		throw new Error("Cannot make line simpler!");
	}
	

	public <LPI> BestProjectTup<SimplePathIndex,LPI> prependLeftBestTupLhs(
			BestProjectTup<? extends PathIndex,LPI> projectSimplerTup){
		throw new Error("Cannot make line simpler!");
	}
	
	public <LPI> BestProjectTup<SimplePathIndex,LPI> prependRightBestTupLhs(
			BestProjectTup<? extends PathIndex,LPI> projectSimplerTup){
		throw new Error("Cannot make line simpler!");
	}
	

	
}
