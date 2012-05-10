package nogbeter.paths.compound;

import java.util.List;
import java.util.Set;

import javax.swing.border.Border;

import bezier.util.Tuple;

import nogbeter.paths.Path;
import nogbeter.paths.PathIndex;
import nogbeter.paths.compound.SplitIndex.SplitChoice;
import nogbeter.paths.results.project.BestProjectTup;
import nogbeter.paths.results.transformers.IPathIndexTransformer;
import nogbeter.paths.results.transformers.PITransformers;
import nogbeter.paths.results.transformers.PathIndexTupleTransformer;
import nogbeter.paths.results.transformers.TupleTransformers;
import nogbeter.points.angles.AngularInterval;
import nogbeter.points.twod.BBox;
import nogbeter.points.twod.Vec;
import nogbeter.transform.AffineTransformation;


public class Shape extends CompoundSplittablePath<ShapeIndex> {

	
	public Shape(Path border, Path inside) {
		super(border,inside); // border is left, inside is right!
	}

	public Path getBorder(){
		return left;
	}
	
	public Path getInside(){
		return right;
	}
	
	@Override
	public BBox makeBBox() {
		return getBorder().getBBox();
	}

	@Override
	public Path<ShapeIndex> getWithAdjustedStartPoint(
			Vec newStartPoint) {
		throw new Error("Must implement , no mixins");
	}

	@Override
	public Vec getStartPoint() {
		throw new Error("Must implement , no mixins");
	}

	@Override
	public Vec getEndPoint() {
		throw new Error("Must implement , no mixins");
	}
	
	public boolean isClosed(){
		return false;
	}

	@Override
	public Shape transform(AffineTransformation t) {
		return new Shape(left.transform(t),right.transform(t));
	}


	public String toString(){
		return "Shape(" + left.toString() + "--" + right.toString() + ")";
	}

	@Override
	public Tuple<Path<ShapeIndex>, Double> normaliseToLength(double prevLength) {
		throw new Error("Cannot length normalise shape!");
	}

	@Override
	public IPathIndexTransformer<ShapeIndex> getLeftTransformer() {
		return PITransformers.shapeBorder;
	}

	@Override
	public IPathIndexTransformer<ShapeIndex> getRightTransformer() {
		return PITransformers.shapeInside;
	}

	@Override
	public <B extends PathIndex> PathIndexTupleTransformer<ShapeIndex, B> getLeftLeftTransformer() {
		return (PathIndexTupleTransformer<ShapeIndex, B>) TupleTransformers.leftBorder;
	}

	@Override
	public <B extends PathIndex> PathIndexTupleTransformer<ShapeIndex, B> getLeftRightTransformer() {
		return (PathIndexTupleTransformer<ShapeIndex, B>) TupleTransformers.leftInside;
	}

	@Override
	public <B extends PathIndex> PathIndexTupleTransformer<B, ShapeIndex> getRightLeftTransformer() {
		return (PathIndexTupleTransformer<B, ShapeIndex>) TupleTransformers.rightBorder;
	}

	@Override
	public <B extends PathIndex> PathIndexTupleTransformer<B, ShapeIndex> getRightRightTransformer() {
		return (PathIndexTupleTransformer<B, ShapeIndex>) TupleTransformers.rightInside;
	}


	@Override
	public void getSubPath(ShapeIndex from, ShapeIndex to, List<Path> result) {
		if(from.choice == SplitChoice.Left){
			left.getSubPath(from.next, to.next, result);
		} else {
			right.getSubPath(from.next, to.next, result);
		}
		
	}

	@Override
	public void getSubPathFrom(ShapeIndex from, List<Path> result) {
		throw new Error("Shape is not a segment!");
	}

	@Override
	public void getSubPathTo(ShapeIndex to, List<Path> result) {
		throw new Error("Shape is not a segment!");
	}



	@Override
	public AngularInterval getAngularInsideInterval(ShapeIndex t) {
		if(t.choice == SplitChoice.Left){
			return left.getAngularInsideInterval(t.next);
		} else {
			return right.getAngularInsideInterval(t.next);
		}
	}

	@Override
	public Vec getStartTan() {
		throw new Error("Shape has no start or end");
	}

	@Override
	public Vec getEndTan() {
		throw new Error("Shape has no start or end");
	}

	@Override
	public boolean isCyclicBorder(ShapeIndex p) {
		throw new Error("Shape has no start or end");
	}


	@Override
	public Path<PathIndex> reverse() {
		throw new Error("Shape has no start or end");
	}
	
	public Vec getArbPoint(){ return left.getArbPoint();}
	public Vec getArbPointTan(){ return left.getArbPointTan();}

}
