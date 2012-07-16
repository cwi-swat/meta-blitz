package deform.paths;

import java.util.List;


import deform.BBox;
import deform.Transform;
import deform.Vec;
import deform.segments.Segment;

public class Append extends Path{

	final Path left, right;

	public Append(Path left, Path right) {
		this.left = left;
		this.right = right;
	}
	
	private static Path createAppend(List<Path> paths, int start, int end) {
		if (start == end - 1) {
			return paths.get(start);
		} else {
			int mid = (start + end) / 2;
			return new Append(createAppend(paths, start, mid), createAppend(
					paths, mid, end));
		}
	}

	public static Path createAppends(List<Path> paths) {
		return createAppend(paths, 0, paths.size());
	}

	@Override
	public BBox makeBBox() {
		return left.getBBox().union(right.getBBox());
	}

	@Override
	void getSimpleLines(Transform t,List<Path> res) {
		left.getSimpleLines(t,res);
		right.getSimpleLines(t,res);
	}

	@Override
	public Path transformAffine(Transform t) {
		return new Append(left.transformAffine(t), right.transformAffine(t));
	}

	@Override
	public
	void getSegments(List<Segment> res) {
		left.getSegments(res);
		right.getSegments(res);
		
	}

	@Override
	public Vec getStart() {
		return left.getStart();
	}

	@Override
	public Vec getEnd() {
		return right.getEnd();
	}
	
}
