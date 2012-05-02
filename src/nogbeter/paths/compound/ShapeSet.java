package nogbeter.paths.compound;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import bezier.util.Tuple;
import bezier.util.Util;
import nogbeter.paths.Path;
import nogbeter.paths.PathIndex;
import nogbeter.paths.SplittablePath;
import nogbeter.paths.results.intersections.IIntersections;
import nogbeter.paths.results.intersections.Intersections;
import nogbeter.paths.results.project.BestProject;
import nogbeter.paths.results.project.BestProjectTup;
import nogbeter.paths.results.transformers.IPathIndexTransformer;
import nogbeter.paths.results.transformers.PITransformers;
import nogbeter.paths.simple.SimplePathIndex;
import nogbeter.paths.simple.lines.DiagonalLine;
import nogbeter.paths.simple.lines.HorizontalLine;
import nogbeter.paths.simple.lines.VerticalLine;
import nogbeter.points.twod.BBox;
import nogbeter.points.twod.Vec;
import nogbeter.transform.AffineTransformation;

import static nogbeter.paths.results.transformers.TupleTransformers.*;
public class ShapeSet extends Path<SetIndex, Path, Path>{

	public final List<Path> shapes;
	
	public ShapeSet(List<Path> shapes) {
		this.shapes = shapes;
	}
	
	public ShapeSet(Path[] shapes) {
		this(Arrays.asList(shapes));
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
	public <RPP extends PathIndex, RLSimp extends Path, RRSimp extends Path> IIntersections<SetIndex, RPP> intersection(
			Path<RPP, RLSimp, RRSimp> other) {
		return other.intersectionLSet(this);
	}

	private <PPI extends PathIndex,LS extends Path,RS extends Path> 
	IIntersections<PPI,SetIndex>  intersections(Path<PPI,LS,RS> lhs){
		IIntersections<PPI,SetIndex> res = Intersections.NoIntersections;
		if(lhs.getBBox().overlaps(getBBox())){
			for(int i = 0 ; i < shapes.size() ; i++){
				Path p = shapes.get(i);
				res = res.append(lhs.intersection(p).transform(setRight(i)));
			}
		}
		return res;
	}

	@Override
	public IIntersections<SimplePathIndex, SetIndex> intersectionLDiaLine(
			DiagonalLine lhs) {
		return intersections(lhs);
	}

	@Override
	public IIntersections<SimplePathIndex, SetIndex> intersectionLHorLine(
			HorizontalLine lhs) {
		return intersections(lhs);
	}

	@Override
	public IIntersections<SimplePathIndex, SetIndex> intersectionLVerLine(
			VerticalLine lhs) {
		return intersections(lhs);
	}
	

	@Override
	public IIntersections<SetIndex, SetIndex> intersectionLSet(ShapeSet lhs) {
		return intersections(lhs);
	}
	

	@Override
	public <PPI extends PathIndex,LS extends Path,RS extends Path> 
		IIntersections<PPI,SetIndex> intersectionLSplittable(SplittablePath<PPI, LS, RS> lhs) {
		return intersections(lhs);
	}


	@Override
	public BestProject<SetIndex> project(double best, Vec p) {


		if(getBBox().getNearestPoint(p).distanceSquared(p) > best){
			return BestProject.noBestYet;
		}
		BestProject<SetIndex> res = new BestProject(best);
		for(int i = 0 ; i < shapes.size() ; i++){
			Path path = shapes.get(i);
			res = res.choose(path.project(res.distSquared,p).transform(PITransformers.setTrans(i)));
		}
		return res;
	}

	@Override
	public <RPP extends PathIndex, RLS extends Path, RRS extends Path> BestProjectTup<SetIndex, RPP> project(
			double best, Path<RPP, RLS, RRS> other) {
		return other.projectLSet(best, this);
	}


	private <RPP extends PathIndex, LS extends Path, RS extends Path>
	BestProjectTup<RPP,SetIndex>  projects(double best, Path<RPP,LS,RS> lhs){
		
		BestProjectTup<RPP,SetIndex> res = new BestProjectTup(best);
		if(minDistTo(lhs.getBBox()) < best){
			for(int i = 0 ; i < shapes.size() ; i++){
				Path p = shapes.get(i);
				res = res.choose(lhs.project(best,p).transform(setRight(i)));
			}
		}
		return res;
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
	public <LPI extends PathIndex, LLS extends Path, LRS extends Path> BestProjectTup<LPI, SetIndex> projectLSplittable(
			double best, Path<LPI, LLS, LRS> lhs) {
		if(minDistTo(lhs.getBBox()) > best){
			return BestProjectTup.noBestYet;
		}
		// each shape is bigger that half the split right (heurisitic), 
		// lets iterate
		if(getBBox().area()/shapes.size() > lhs.getBBox().area()/2.0 ){
			return projects(best, lhs);
		} else {
			Tuple<LLS,LRS> sp = lhs.splitSimpler();
			if(sp.l.getBBox().avgDistSquared(getBBox().getMiddle()) <
					sp.l.getBBox().avgDistSquared(getBBox().getMiddle())){
				BestProjectTup<LPI, SetIndex> res = sp.l.project(best, this).transform(leftLeft());
				return res.choose(sp.r.project(res.distSquared, this).transform(leftRight()));
			} else {
				BestProjectTup<LPI, SetIndex> res = sp.r.project(best, this).transform(leftRight());
				return res.choose(sp.l.project(res.distSquared, this).transform(leftLeft()));
			}
		}
		
	}

	public int nrChildren(){
		return shapes.size();
	}
	
	public Path getChild(int i){
		return shapes.get(i);
	}
	

	@Override
	public Path getWithAdjustedStartPoint(
			Vec newStartPoint) {
		throw new Error("Must implement , no mixins");
	}

	@Override
	public Vec getStartPoint() {
		throw new Error("Must implement , no mixins");
	}

	@Override
	public Vec getEndPoint() {
		throw new Error("Must implement , no mixins");
	}


	public boolean isClosed(){
		return false;
	}

	@Override
	public Path<SetIndex, Path, Path> transform(AffineTransformation t) {
		List<Path> res = new ArrayList<Path>(shapes.size());
		for(Path p : shapes){
			res.add(p.transform(t));
		}
		return new ShapeSet(res);
	}	
	
	public String toString(){
		StringBuilder build = new StringBuilder();
		build.append("ShapeSet(");
		for(Path p : shapes){
			build.append(p.toString());
			build.append("\n, ");
		}
		build.append(")");
		return build.toString();
	}
}
