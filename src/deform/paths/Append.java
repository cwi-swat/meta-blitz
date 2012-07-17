package deform.paths;

import java.util.List;


import deform.BBox;
import deform.Transform;
import deform.Vec;
import deform.segments.Segment;
import deform.segments.SegmentsMaker;

public class Append extends Path{

	final Path left, right;

	public Append(Path left, Path right) {
		super(left.bbox.union(right.bbox));
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
	void renderAffine(Transform t, SegmentsMaker res) {
		left.renderAffine(t, res);
		right.renderAffine(t, res);
	}

	@Override
	void renderNonAffine(Transform t, SegmentsMaker res) {
		left.render(t, res);
		right.render(t, res);
		
	}
	
}
