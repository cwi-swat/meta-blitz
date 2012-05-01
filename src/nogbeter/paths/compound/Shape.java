package nogbeter.paths.compound;

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


}
