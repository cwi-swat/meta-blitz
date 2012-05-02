package nogbeter.paths.compound;

import javax.swing.border.Border;

import nogbeter.paths.Path;
import nogbeter.paths.results.project.BestProjectTup;
import nogbeter.points.twod.BBox;
import nogbeter.points.twod.Vec;
import nogbeter.transform.AffineTransformation;


public class Shape<LSimp extends Path, RSimp extends Path> extends CompoundSplittablePath<LSimp, RSimp> {

	
	public Shape(LSimp border, RSimp inside) {
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
	public Path<SplitIndex, LSimp, RSimp> getWithAdjustedStartPoint(
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
	public Path<SplitIndex, LSimp, RSimp> transform(AffineTransformation t) {
		return new Shape(left.transform(t),right.transform(t));
	}


	public String toString(){
		return "Shape(" + left.toString() + "--" + right.toString() + ")";
	}
}