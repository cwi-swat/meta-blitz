package textures.interfaces;

import java.awt.Graphics2D;

import paths.paths.paths.Path;
import paths.points.twod.BBox;
import textures.sample.LocatedBufferedImage;

public interface ITexturedPath<Sample extends ISample<Sample>> {
	Path getPath();

	void render(Graphics2D g, BBox b);
}
