package nogbeter.paths.compound;

import javax.swing.border.Border;

import bezier.util.Tuple;

import nogbeter.paths.Path;
import nogbeter.paths.results.project.BestProjectTup;
import nogbeter.points.twod.BBox;
import nogbeter.points.twod.Vec;
import nogbeter.transform.AffineTransformation;


public class Shape extends CompoundSplittablePath {

	
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
	public Path<SplitIndex> getWithAdjustedStartPoint(
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
	public Tuple<Path<SplitIndex>, Double> normaliseToLength(double prevLength) {
		throw new Error("Cannot length normalise shape!");
	}
}
