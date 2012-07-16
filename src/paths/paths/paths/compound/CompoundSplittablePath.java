package paths.paths.paths.compound;

import deform.BBox;
import deform.Vec;
import paths.paths.paths.Path;
import paths.paths.paths.PathIndex;
import paths.paths.paths.SplittablePath;
import util.Tuple;

public abstract class CompoundSplittablePath extends SplittablePath {

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
	public Vec getAt(PathIndex t) {
		switch (((SplitIndex) t).choice) {
		case Left:
			return left.getAt(t.next);
		case Right:
			return right.getAt(t.next);
		}
		throw new Error("Unkown split choice:" + this + "\n");
	}

	@Override
	public Vec getTangentAt(PathIndex t) {
		switch (((SplitIndex) t).choice) {
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

	public int nrChildren() {
		return 2;
	}

	public Path getChild(int i) {
		return i == 0 ? left : right;
	}

	public PathIndex minPathIndex() {
		return getLeftTransformer().transform(left.minPathIndex());
	}

	public PathIndex maxPathIndex() {
		return getRightTransformer().transform(right.maxPathIndex());
	}
}
