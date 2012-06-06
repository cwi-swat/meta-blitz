package paths.paths.results.transformers;

import static paths.paths.results.transformers.PITransformers.*;
import paths.paths.paths.Path;
import paths.paths.paths.PathIndex;
import paths.paths.paths.compound.AppendIndex;
import paths.paths.paths.compound.ClosedPathIndex;
import paths.paths.paths.compound.SetIndex;
import paths.paths.paths.compound.ShapeIndex;
import paths.paths.paths.compound.SplitIndex;
public class TupleTransformers {

	
	public static  PathIndexTupleTransformer unitTup =new PathIndexTupleTransformer(unit,unit);

	public static PathIndexTupleTransformer<ShapeIndex,?> leftBorder = left(shapeBorder);
	
	public static PathIndexTupleTransformer<ShapeIndex,?> leftInside = left(shapeInside);
	
	public static PathIndexTupleTransformer<?,ShapeIndex> rightBorder = right(shapeBorder);
	
	public static PathIndexTupleTransformer<?,ShapeIndex> rightInside = right(shapeInside);
	
	public static PathIndexTupleTransformer<AppendIndex,?> aleftLeft= left(appendLeft);
	
	public static PathIndexTupleTransformer<AppendIndex,?> aleftRight = left(appendRight);
	
	public static PathIndexTupleTransformer<?,AppendIndex> arightLeft = right(appendLeft);
	
	public static PathIndexTupleTransformer<?,AppendIndex> arightRight = right(appendRight);
	

	public static <PI extends PathIndex>  PathIndexTupleTransformer<SetIndex,PI> setLeft(int i){
		return new PathIndexTupleTransformer<SetIndex, PI>(setTrans(i),unit);
	}
	public static <PI extends PathIndex>  PathIndexTupleTransformer<PI,SetIndex> setRight(int i){
		return new PathIndexTupleTransformer<PI,SetIndex>(unit,setTrans(i));
	}
	
	public static <A extends PathIndex, B extends PathIndex>  PathIndexTupleTransformer<A,B> right(IPathIndexTransformer<B> tr){
		return new PathIndexTupleTransformer<A, B>(unit, tr);
	}
	
	public static <A extends PathIndex, B extends PathIndex>  PathIndexTupleTransformer<A,B> left(IPathIndexTransformer<A> tl){
		return new PathIndexTupleTransformer<A, B>(tl, unit);
	}
}
