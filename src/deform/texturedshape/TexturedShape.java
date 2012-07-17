package deform.texturedshape;

import java.awt.Graphics2D;
import java.awt.Image;

import deform.BBox;
import deform.Transform;

public interface TexturedShape {

	BBox getBBox();
	LocatedImage render(Transform t, BBox b) ;

}
