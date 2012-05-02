package nogbeter.paths.compound;

import java.util.LinkedList;
import java.util.List;

import bezier.points.Vec;
import bezier.util.Tuple;
import nogbeter.paths.Path;
import nogbeter.paths.PathIndex;
import nogbeter.paths.SplittablePath;
import nogbeter.paths.compound.SplitIndex.SplitChoice;
import nogbeter.paths.results.intersections.IIntersections;
import nogbeter.paths.results.project.BestProjectTup;
import nogbeter.paths.results.transformers.IPathIndexTransformer;
import nogbeter.paths.results.transformers.PITransformers;
import nogbeter.paths.simple.SimplePath;
import nogbeter.paths.simple.SimplePathIndex;
import nogbeter.paths.simple.lines.DiagonalLine;
import nogbeter.paths.simple.lines.HorizontalLine;
import nogbeter.paths.simple.lines.VerticalLine;
import nogbeter.util.BBox;

public abstract class CompoundSplittablePath<LSimp extends Path, RSimp extends Path>
		extends SplittablePath<SplitIndex, LSimp, RSimp> {

	public final LSimp left;
	public final RSimp right;

	public CompoundSplittablePath(LSimp left, RSimp right) {
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
	public Tuple<LSimp, RSimp> splitSimpler() {
		return new Tuple<LSimp, RSimp>(left, right);
	}
	
	public int nrChildren(){
		return 2;
	}
	
	public Path getChild(int i){
		return i == 0 ? left : right;
	}
	
	@Override
	public IPathIndexTransformer<SplitIndex> getLeftTransformer() {
		return PITransformers.splitLeft;
	}

	@Override
	public IPathIndexTransformer<SplitIndex> getRightTransformer() {
		return PITransformers.splitRight;
	}

	
}
