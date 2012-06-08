package paths.paths.paths.compound;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import paths.paths.paths.Path;
import paths.paths.paths.PathIndex;
import paths.paths.paths.SplittablePath;
import paths.paths.paths.compound.SplitIndex.SplitChoice;
import paths.paths.paths.simple.Line;
import paths.paths.paths.simple.SimplePath;
import paths.paths.paths.simple.SimplePathIndex;
import paths.paths.results.intersections.IIntersections;
import paths.paths.results.project.BestProjectTup;
import paths.paths.results.transformers.IPathIndexTransformer;
import paths.paths.results.transformers.PITransformers;
import paths.paths.results.transformers.PathIndexTupleTransformer;
import paths.points.twod.BBox;
import paths.points.twod.Vec;

import transform.AffineTransformation;
import util.Tuple;


import static paths.paths.results.transformers.TupleTransformers.*;

public abstract class CompoundSplittablePath<P extends SplitIndex>
		extends SplittablePath<P> {

	public final Path left;
	public final Path right;

	public CompoundSplittablePath(Path left, Path right) {
		this.left = left;
		this.right = right;
	}

	@Override
	public BBox makeBBox() {
		return left.getBBox().union(right.getBBox());
	}

	@Override
	public Vec getAt(SplitIndex t) {
		switch (t.choice) {
		case Left:
			return left.getAt(t.next);
		case Right:
			return right.getAt(t.next);
		}
		throw new Error("Unkown split choice:" + this + "\n");
	}

	@Override
	public Vec getTangentAt(SplitIndex t) {
		switch (t.choice) {
		case Left:
			return left.getTangentAt(t.next);
		case Right:
			return right.getTangentAt(t.next);
		}
		throw new Error("Unkown split choice:" + this + "\n");
	}

	@Override
	public Tuple<Path, Path> splitSimpler() {
		return new Tuple<Path, Path>(left, right);
	}
	
	public int nrChildren(){
		return 2;
	}
	
	public Path getChild(int i){
		return i == 0 ? left : right;
	}
	

	@Override
	public Path getClosedPath(P p) {
		switch (p.choice) {
		case Left:
			return left.getClosedPath(p.next);
		case Right:
			return right.getClosedPath(p.next);
		}
		throw new Error("Unkown split choice:" + this + "\n");
	}
	
	@Override
	public void getClosedSegmentsNotInSet(Set<Path> segments, List<ClosedPath> res){
		left.getClosedSegmentsNotInSet(segments, res);
		right.getClosedSegmentsNotInSet(segments, res);
	}
	
	public P minPathIndex(){ return getLeftTransformer().transform(left.minPathIndex()); }
	public P maxPathIndex(){ return getRightTransformer().transform(right.maxPathIndex()); }
}
