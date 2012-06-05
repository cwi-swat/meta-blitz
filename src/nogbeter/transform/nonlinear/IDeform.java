package nogbeter.transform.nonlinear;

import nogbeter.paths.Path;
import nogbeter.points.twod.BBox;
import nogbeter.transform.IToTransform;

public interface IDeform{
	Path deform(Path p);
	IDeform subDeform(BBox b);
	boolean isSimple();
	boolean isSimpleX();
	boolean isSimpleY();
	double getSplitPointX();
	double getSplitPointY();
}
