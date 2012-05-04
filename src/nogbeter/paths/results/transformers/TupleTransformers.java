package nogbeter.paths.results.transformers;

import static nogbeter.paths.results.transformers.PITransformers.*;
import nogbeter.paths.Path;
import nogbeter.paths.PathIndex;
import nogbeter.paths.compound.AppendIndex;
import nogbeter.paths.compound.SetIndex;
import nogbeter.paths.compound.ShapeIndex;
import nogbeter.paths.compound.SplitIndex;
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
