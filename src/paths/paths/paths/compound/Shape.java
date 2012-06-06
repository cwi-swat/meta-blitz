package paths.paths.paths.compound;


import java.util.List;
import java.util.Set;

import javax.swing.border.Border;

import paths.paths.paths.Path;
import paths.paths.paths.PathIndex;
import paths.paths.paths.compound.SplitIndex.SplitChoice;
import paths.paths.results.project.BestProjectTup;
import paths.paths.results.transformers.IPathIndexTransformer;
import paths.paths.results.transformers.PITransformers;
import paths.paths.results.transformers.PathIndexTupleTransformer;
import paths.paths.results.transformers.TupleTransformers;
import paths.points.angles.AngularInterval;
import paths.points.twod.BBox;
import paths.points.twod.Vec;
import paths.transform.IToTransform;
import paths.transform.nonlinear.IDeform;
import paths.transform.nonlinear.ILineTransformer;
import paths.transform.nonlinear.pathdeform.PathDeform;

import util.Tuple;




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
	public Shape transform(IToTransform t) {
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
	public List<Vec> getTangents(ShapeIndex t) {
		if(t.choice == SplitChoice.Left){
			return left.getTangents(t.next);
		} else {
			return right.getTangents(t.next);
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
	public Path<PathIndex> reverse() {
		throw new Error("Shape has no start or end");
	}
	
	public Vec getArbPoint(){ return left.getArbPoint();}
	public Vec getArbPointTan(){ return left.getArbPointTan();}

	@Override
	public Shape deformActual(IDeform p) {
			return new Shape(left.deform(p), right.deform(p));
	}



	@Override
	public Path transformApproxLines(ILineTransformer t) {
		return new Shape(left.transformApproxLines(t), right.transformApproxLines(t));
	}


	

	
}
