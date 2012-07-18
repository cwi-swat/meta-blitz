package deform.texturedshape;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import deform.BBox;
import deform.Combinators;
import deform.Texture;
import deform.Transform;
import deform.segments.SegPath;
import deform.segments.ShapesMaker;
import deform.tests.BasicDemo;
import deform.texture.Java2DTexture;

public class Over extends TexturedShape{

	final TexturedShape[] shapes;
	final BBox b;
	final boolean java2d;
	
	public Over(TexturedShape[] shapes){
		this.shapes = shapes;
		BBox tmp = BBox.emptyBBox;
		boolean tmpj2 = true;
		for(TexturedShape ts : shapes){
			tmpj2 = tmpj2 && ts.isJava2DRenderable();
			tmp = tmp.union(ts.getBBox());
		}
		b = tmp;
		java2d = tmpj2;
	}
	
	@Override
	BBox getBBox() {
		return b;
	}

	@Override
	LocatedImage render(Transform t, BBox b, AffineTransform trans) {
		BBox nb = t.transformBBox(this.b);
		if(nb.overlaps(b)){
			nb = nb.intersections(b);
			BufferedImage img = new BufferedImage(nb.getWidthInt(),
					nb.getHeightInt(), BufferedImage.TYPE_4BYTE_ABGR);
			Graphics2D g = (Graphics2D) img.getGraphics();

//			if(trans!=null) g.setTransform(trans);
			g.translate(-nb.getXInt(), -nb.getYInt());
			
			for(TexturedShape ts : shapes){
				ts.render(t, b,trans).draw(g);
			}
			g.dispose();
			return new LocatedImage(nb.getLeftUp(), img);
		} else {
			return LocatedImage.empty;
		}
	}

	
	boolean isJava2DRenderable(){
		return java2d;
	}

}
