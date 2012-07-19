package transform.nonlinear;

import paths.paths.paths.QueryPath;
import paths.paths.paths.simple.Line;

public interface ILineTransformer {
	QueryPath transform(Line l);
}
