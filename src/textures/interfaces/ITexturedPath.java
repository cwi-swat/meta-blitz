package textures.interfaces;

import paths.paths.paths.Path;
import textures.sample.Image;

public interface ITexturedPath<Sample extends ISample<Sample>> extends ITexture<Sample> {
	Path getPath();
	Image<Sample> getImage();
}
