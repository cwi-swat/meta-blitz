package transform.nonlinear;

import paths.paths.paths.Path;
import paths.paths.paths.simple.Line;

public interface ILineTransformer {
	Path transform(Line l);
}
