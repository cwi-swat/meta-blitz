package nogbeter.paths.results.transformers;

import static nogbeter.paths.results.transformers.PITransformers.*;
import nogbeter.paths.Path;
import nogbeter.paths.PathIndex;
import nogbeter.paths.compound.SetIndex;
import nogbeter.paths.compound.SplitIndex;
public class TupleTransformers {

	
	public static <A extends PathIndex, B extends PathIndex> 
		PathIndexTupleTransformer<A,B> unitTup() {
		return new PathIndexTupleTransformer(unit,unit);
	}
	public static <B extends PathIndex> 
		PathIndexTupleTransformer<SplitIndex,B> leftLeft() { 
		return new PathIndexTupleTransformer(splitLeft, unit);
	}
	public static <B extends PathIndex> 
		PathIndexTupleTransformer<SplitIndex,B> leftRight(){
		return new PathIndexTupleTransformer(splitRight, unit);
	}
	
	public static <A extends PathIndex> 
		PathIndexTupleTransformer<A,SplitIndex> rightLeft (){
			return new PathIndexTupleTransformer(unit, splitLeft);
	}
	public static <A extends PathIndex> 
		PathIndexTupleTransformer<A,SplitIndex> rightRight(){
			return new PathIndexTupleTransformer(unit, splitRight);
	}
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
