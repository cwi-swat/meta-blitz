package transform.nonlinear;

import deform.BBox;
import paths.paths.paths.QueryPath;

public interface IDeform {
	QueryPath deform(QueryPath p);

	IDeform subDeform(BBox b);

	boolean isSimple();

	boolean isSimpleX();

	boolean isSimpleY();

	double getSplitPointX();

	double getSplitPointY();
}
