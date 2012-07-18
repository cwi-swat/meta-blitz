package deform.texturedshape;

import java.awt.Graphics2D;
import java.awt.Image;

import deform.BBox;
import deform.Transform;

public abstract class TexturedShape {

	abstract BBox getBBox();
	abstract LocatedImage render(Transform t, BBox b,  java.awt.geom.AffineTransform trans) ;
	
	public LocatedImage render(Transform t,BBox b) {
		return render(t,b,null);
	}
	boolean isJava2DRenderable(){
		return false;
	}

}
