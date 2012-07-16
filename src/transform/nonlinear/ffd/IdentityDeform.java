package transform.nonlinear.ffd;

import deform.BBox;
import paths.paths.paths.Path;
import transform.nonlinear.IDeform;

public class IdentityDeform implements IDeform {

	public static IDeform instance = new IdentityDeform();

	@Override
	public Path deform(Path p) {
		return p;
	}

	@Override
	public IDeform subDeform(BBox b) {
		return this;
	}

	@Override
	public boolean isSimple() {
		return true;
	}

	@Override
	public boolean isSimpleX() {
		return true;
	}

	@Override
	public boolean isSimpleY() {
		return true;
	}

	@Override
	public double getSplitPointX() {
		throw new Error("No splitpoint");
	}

	@Override
	public double getSplitPointY() {
		throw new Error("No splitpoint");
	}

}
