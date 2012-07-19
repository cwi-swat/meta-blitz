package textures.interfaces;

import java.awt.Graphics2D;

import deform.BBox;

import paths.paths.paths.QueryPath;
import textures.sample.LocatedBufferedImage;

public interface ITexturedPath<Sample extends ISample<Sample>> {
	QueryPath getPath();

	void render(Graphics2D g, BBox b);
}
