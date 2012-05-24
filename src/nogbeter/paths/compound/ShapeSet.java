package nogbeter.paths.compound;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import bezier.util.Tuple;
import bezier.util.Util;
import nogbeter.paths.Path;
import nogbeter.paths.PathIndex;
import nogbeter.paths.SplittablePath;
import nogbeter.paths.compound.SplitIndex.SplitChoice;
import nogbeter.paths.results.intersections.IIntersections;
import nogbeter.paths.results.intersections.Intersections;
import nogbeter.paths.results.project.BestProject;
import nogbeter.paths.results.project.BestProjectTup;
import nogbeter.paths.results.transformers.IPathIndexTransformer;
import nogbeter.paths.results.transformers.PITransformers;
import nogbeter.paths.simple.SimplePathIndex;
import nogbeter.paths.simple.lines.Line;
import nogbeter.points.angles.AngularInterval;
import nogbeter.points.twod.BBox;
import nogbeter.points.twod.Vec;
import nogbeter.transform.AffineTransformation;

import static nogbeter.paths.results.transformers.TupleTransformers.*;
public class ShapeSet extends Path<SetIndex>{

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
	public <RPP extends PathIndex> 
	IIntersections<SetIndex, RPP> 
	intersection(Path<RPP> other) {
		return other.intersectionLSet(this);
	}

	private <PPI extends PathIndex> 
	IIntersections<PPI,SetIndex>  intersections(Path<PPI> lhs){
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
	public IIntersections<SimplePathIndex, SetIndex> intersectionLLine(
			Line lhs) {
		return intersections(lhs);
	}

	

	@Override
	public IIntersections<SetIndex, SetIndex> intersectionLSet(ShapeSet lhs) {
		return intersections(lhs);
	}
	

	@Override
	public <PPI extends PathIndex> 
	IIntersections<PPI,SetIndex> 
	intersectionLSplittable(SplittablePath<PPI> lhs) {
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
	public <RPP extends PathIndex>
	BestProjectTup<SetIndex, RPP> 
	project(double best, Path<RPP> other) {
		return other.projectLSet(best, this);
	}


	private <RPP extends PathIndex>
	BestProjectTup<RPP,SetIndex>  projects(double best, Path<RPP> lhs){
		
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
	public BestProjectTup<SimplePathIndex, SetIndex> projectLLine(
			double best, Line lhs) {
		return projects(best,lhs);
	}

	@Override
	public BestProjectTup<SetIndex, SetIndex> projectLSet(double best,
			ShapeSet lhs) {
		return projects(best,lhs);
	}
	

	@Override
	public <LPI extends PathIndex> 
	BestProjectTup<LPI, SetIndex>
	projectLSplittable(double best, SplittablePath<LPI> lhs) {
		if(minDistTo(lhs.getBBox()) > best){
			return BestProjectTup.noBestYet;
		}
		// each shape is bigger that half the split right (heurisitic), 
		// lets iterate
		if(getBBox().area()/shapes.size() > lhs.getBBox().area()/2.0 ){
			return projects(best, lhs);
		} else {
			Tuple<Path,Path> sp = lhs.splitSimpler();
			if(sp.l.getBBox().avgDistSquared(getBBox().getMiddle()) <
					sp.l.getBBox().avgDistSquared(getBBox().getMiddle())){
				BestProjectTup<LPI, SetIndex> res = sp.l.project(best, this).transform(lhs.getLeftLeftTransformer());
				return res.choose(sp.r.project(res.distSquared, this).transform(lhs.getLeftRightTransformer()));
			} else {
				BestProjectTup<LPI, SetIndex> res = sp.r.project(best, this).transform(lhs.getLeftRightTransformer());
				return res.choose(sp.l.project(res.distSquared, this).transform(lhs.getLeftLeftTransformer()));
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
	public ShapeSet transform(AffineTransformation t) {
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
	
	@Override
	public Tuple<Path<SetIndex>, Double> normaliseToLength(double prevLength) {
		throw new Error("Cannot length normalise set!");
	}
	
	@Override
	public void getSubPath(SetIndex from, SetIndex to, List<Path> result) {
		if(from.choice != to.choice){
			throw new Error("Cannot get subpath of two different set elems!");
		}
		shapes.get(from.choice).getSubPath(from.next, to.next, result);
		
	}

	@Override
	public void getSubPathFrom(SetIndex from, List<Path> result) {
		throw new Error("Set is not a segment!");
	}

	@Override
	public void getSubPathTo(SetIndex to, List<Path> result) {
		throw new Error("Set is not a segment!");
	}

	@Override
	public AngularInterval getAngularInsideInterval(SetIndex t) {
		return shapes.get(t.choice).getAngularInsideInterval(t.next);
	}

	@Override
	public Vec getStartTan() {
		throw new Error("Set is not a segment!");
	}

	@Override
	public Vec getEndTan() {
		throw new Error("Set is not a segment!");
	}

	@Override
	public boolean isCyclicBorder(SetIndex p) {
		throw new Error("Shape has no start or end");
	}
	
	@Override
	public Path getSegment(SetIndex p) {
		return shapes.get(p.choice).getSegment(p.next);
	}
	
	@Override
	public void getClosedSegmentsNotInSet(Set<Path> segments, List<Path> res){
		for(Path p : shapes){
			p.getClosedSegmentsNotInSet(segments, res);
		}
	}

	@Override
	public Path<PathIndex> reverse() {
		List<Path> res = new ArrayList<Path>();
		for(Path p : shapes){
			res.add(p.reverse());
		}
		return (Path)new ShapeSet(res);
	}
	
	public Vec getArbPoint(){ return shapes.get(0).getArbPoint();}
	public Vec getArbPointTan(){ return shapes.get(0).getArbPointTan();}
}
