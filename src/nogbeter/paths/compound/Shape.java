package nogbeter.paths.compound;

import bezier.points.Vec;
import nogbeter.paths.BestProjectTup;
import nogbeter.paths.Path;
import nogbeter.util.BBox;


public class Shape<LSimp extends Path, RSimp extends Path> extends SplittableCompoundPath<LSimp, RSimp> {

	
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
	public Path<CompoundSplitIndex, LSimp, RSimp> getWithAdjustedStartPoint(
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


}
