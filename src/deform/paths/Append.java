package deform.paths;

import java.util.List;

import paths.paths.factory.QueryPathFactory;
import paths.paths.paths.QueryPath;


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
	public String toString() {
		return "Append [" + left + ", " + right + "]";
	}

	@Override
	public
	QueryPath toQueryPath() {
		return QueryPathFactory.createAppends(left.toQueryPath(),right.toQueryPath());
	}

	@Override
	void render(BBox area, Transform t, SegmentsMaker res) {
		left.render(area, t,res);
		right.render(area, t,res);
		
	}
}
