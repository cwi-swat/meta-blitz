package deform.texturedshape;

import java.awt.Graphics2D;
import java.awt.Image;

import deform.BBox;
import deform.Transform;
import deform.render.LocatedImage;
import deform.render.RenderContext;

public abstract class TexturedShape {

	public abstract BBox getBBox();
	public abstract void render(Transform t,  RenderContext ctx) ;
}
