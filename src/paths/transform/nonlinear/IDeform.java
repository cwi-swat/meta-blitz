package paths.transform.nonlinear;

import paths.paths.paths.Path;
import paths.points.twod.BBox;
import paths.transform.IToTransform;

public interface IDeform{
	Path deform(Path p);
	IDeform subDeform(BBox b);
	boolean isSimple();
	boolean isSimpleX();
	boolean isSimpleY();
	double getSplitPointX();
	double getSplitPointY();
}
