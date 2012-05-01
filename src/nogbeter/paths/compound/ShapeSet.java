package nogbeter.paths.compound;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import bezier.points.Vec;
import bezier.util.Tuple;
import bezier.util.Util;
import nogbeter.paths.BestProjectTup;
import nogbeter.paths.Path;
import nogbeter.paths.PathIndex;
import nogbeter.paths.simple.SimplePathIndex;
import nogbeter.paths.simple.lines.DiagonalLine;
import nogbeter.paths.simple.lines.HorizontalLine;
import nogbeter.paths.simple.lines.VerticalLine;
import nogbeter.util.BBox;

public class ShapeSet extends Path<SetIndex, Path, Path>{

	public final List<Path> shapes;
	
	public ShapeSet(List<Path> shapes) {
		this.shapes = shapes;
	}

	@Override
	public BBox makeBBox() {
		BBox total = BBox.emptyBBox;
		for(Path p : shapes){
			total = total.union(p.getBBox());
		}
		return total;
	}

	@Override
	public Vec getAt(SetIndex t) {
		return shapes.get(t.choice).getAt(t.next);
	}

	@Override
	public Vec getTangentAt(SetIndex t) {
		return shapes.get(t.choice).getTangentAt(t.next);
	}

	@Override
	public Tuple<Path, Path> splitSimpler() {
		throw new Error("Not splitting set!");
	}

	@Override
	public <RPP, RLSimp extends Path, RRSimp extends Path> Tuple<List<SetIndex>, List<RPP>> intersection(
			Path<RPP, RLSimp, RRSimp> other) {
		return other.intersectionLSet(this);
	}

	private <PPI,LS extends Path,RS extends Path> 
	Tuple<List<PPI>, List<SetIndex>> intersections(Path<PPI,LS,RS> lhs){
		
		Tuple<List<PPI>, List<SetIndex>> res = 
				new Tuple<List<PPI>, List<SetIndex>>(
						Collections.EMPTY_LIST, Collections.EMPTY_LIST);
		if(lhs.getBBox().overlaps(getBBox())){
			for(int i = 0 ; i < shapes.size() ; i++){
				Path p = shapes.get(i);
				res = Util.appendTupList(res,prependIndexListRhs(i,lhs.intersection(p)));
			}
		}
		return res;
	}
	
	private <PPI> Tuple<List<PPI>, List<SetIndex>> prependIndexListRhs(
			int i, Tuple<List<PPI>, List<PathIndex>> intersections) {
		List<SetIndex> resr = new ArrayList<SetIndex>(intersections.r.size());
		for(PathIndex p : intersections.r){
			resr.add(new SetIndex(i, p));
		}
		return new Tuple<List<PPI>, List<SetIndex>>(intersections.l, resr);
	}

	@Override
	public Tuple<List<SimplePathIndex>, List<SetIndex>> intersectionLDiaLine(
			DiagonalLine lhs) {
		return intersections(lhs);
	}

	@Override
	public Tuple<List<SimplePathIndex>, List<SetIndex>> intersectionLHorLine(
			HorizontalLine lhs) {
		return intersections(lhs);
	}

	@Override
	public Tuple<List<SimplePathIndex>, List<SetIndex>> intersectionLVerLine(
			VerticalLine lhs) {
		return intersections(lhs);
	}
	

	@Override
	public Tuple<List<SetIndex>, List<SetIndex>> intersectionLSet(ShapeSet lhs) {
		return intersection(lhs);
	}

	@Override
	public <LPI> Tuple<List<LPI>, List<SetIndex>> prependRightListRhs(
			Tuple<List<LPI>, List<? extends PathIndex>> intersections) {
		throw new Error("Must implement this method because of lack of mixins!");
	}

	@Override
	public <LPI> Tuple<List<LPI>, List<SetIndex>> prependLeftListRhs(
			Tuple<List<LPI>, List<? extends PathIndex>> intersections) {
		throw new Error("Must implement this method because of lack of mixins!");
	}

	@Override
	public <RPP, RLS extends Path, RRS extends Path> BestProjectTup<SetIndex, RPP> project(
			double best, Path<RPP, RLS, RRS> other) {
		// TODO Auto-generated method stub
		return null;
	}


	private <RPP, LS extends Path, RS extends Path>
	BestProjectTup<RPP,SetIndex>  projects(double best, Path<RPP,LS,RS> lhs){
		
		BestProjectTup<RPP,SetIndex> res = BestProjectTup.noBestYet;
		if(minDistTo(lhs.getBBox()) < best){
			for(int i = 0 ; i < shapes.size() ; i++){
				Path p = shapes.get(i);
				res = res.choose(prependIndexBestRhs(i,lhs.project(best,p)));
				best = Math.min(best, res.distSquared);
			}
		}
		return res;
	}
	
	
	private <RPP, P extends PathIndex> BestProjectTup<RPP, SetIndex> prependIndexBestRhs(int i,
			BestProjectTup<RPP,P> project) {
		return new BestProjectTup<RPP, SetIndex>(project.distSquared, project.t.l, 
				new SetIndex(i, project.t.r));
	}

	@Override
	public BestProjectTup<SimplePathIndex, SetIndex> projectLDiaLine(
			double best, DiagonalLine lhs) {
		return projects(best,lhs);
	}

	@Override
	public BestProjectTup<SimplePathIndex, SetIndex> projectLHorLine(
			double best, HorizontalLine lhs) {
		return projects(best,lhs);
	}

	@Override
	public BestProjectTup<SimplePathIndex, SetIndex> projectLVerLine(
			double best, VerticalLine lhs) {
		return projects(best,lhs);
	}
	
	@Override
	public BestProjectTup<SetIndex, SetIndex> projectLSet(double best,
			ShapeSet lhs) {
		return projects(best,lhs);
	}

	@Override
	public <LPI> BestProjectTup<LPI, SetIndex> prependLeftBestTupRhs(
			BestProjectTup<LPI, ? extends PathIndex> projectSimplerTup) {
		throw new Error("Must implement this method because of lack of mixins!");
	}

	@Override
	public <LPI> BestProjectTup<LPI, SetIndex> prependRightBestTupRhs(
			BestProjectTup<LPI, ? extends PathIndex> projectSimplerTup) {
		throw new Error("Must implement this method because of lack of mixins!");
	}

	@Override
	public <LPI> BestProjectTup<SetIndex, LPI> prependLeftBestTupLhs(
			BestProjectTup<? extends PathIndex, LPI> projectSimplerTup) {
		throw new Error("Must implement this method because of lack of mixins!");
	}

	@Override
	public <LPI> BestProjectTup<SetIndex, LPI> prependRightBestTupLhs(
			BestProjectTup<? extends PathIndex, LPI> projectSimplerTup) {
		throw new Error("Must implement this method because of lack of mixins!");
	}




}
