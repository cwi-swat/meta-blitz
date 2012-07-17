package textures.texturedpaths;

import java.awt.Graphics2D;

import paths.points.twod.BBox;
import textures.interfaces.ITexture;

public interface ITexturedPath extends ITexture{
	public void render(Graphics2D gb, BBox b) ;
}
