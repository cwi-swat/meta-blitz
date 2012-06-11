package paths.paths.results.intersections;

import java.util.Iterator;

import paths.paths.results.transformers.PathIndexTupleTransformer;


public interface IIntersections extends Iterable<Intersection> {
	
	IIntersections appendFlipped(FlippedIntersections lhs);
	IIntersections appendNorm(Intersections lhs);
	IIntersections append(IIntersections r);
	Iterator<Intersection> iterator();
	IIntersections transform(PathIndexTupleTransformer trans);
	IIntersections flip();
}
