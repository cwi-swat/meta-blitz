package nogbeter.transform.nonlinear;

import nogbeter.paths.Path;
import nogbeter.paths.simple.Line;

public interface ILineTransformer {
	Path transform(Line l);
}
