package paths.paths.results.intersections;

import java.util.Iterator;

import paths.paths.paths.PathIndex;
import paths.paths.results.transformers.IPathIndexTransformer;
import paths.paths.results.transformers.PathIndexTupleTransformer;


public interface IIntersections<LI extends PathIndex,RI extends PathIndex> extends Iterable<Intersection<LI,RI>> {
	
	IIntersections<LI,RI> appendFlipped(FlippedIntersections<LI,RI> lhs);
	IIntersections<LI,RI> appendNorm(Intersections<LI,RI> lhs);
	IIntersections<LI,RI> append(IIntersections<LI,RI> r);
	Iterator<Intersection<LI,RI>> iterator();
	<LPI extends PathIndex,RPI extends PathIndex> 
		IIntersections<LPI,RPI> transform(PathIndexTupleTransformer<LPI,RPI> trans);
	IIntersections<RI,LI> flip();
}
