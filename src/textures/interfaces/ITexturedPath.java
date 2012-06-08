package textures.interfaces;

import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;

import paths.paths.paths.Path;
import textures.sample.Image;

public interface ITexturedPath<Sample extends ISample<Sample>> extends ITexture<Sample> {
	Path getPath();
	private void render(int fromX, int fromY, int width, int height);
	BufferedImage getImage();
}
