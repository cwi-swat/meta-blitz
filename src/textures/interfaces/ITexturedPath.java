package textures.interfaces;

import paths.paths.paths.Path;
import paths.points.twod.BBox;
import textures.sample.LocatedBufferedImage;

public interface ITexturedPath<Sample extends ISample<Sample>> {
	Path getPath();

	LocatedBufferedImage render(BBox b);
}
