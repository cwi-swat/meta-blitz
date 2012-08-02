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
import deform.render.RenderContext;
import deform.segments.SegPath;
import deform.segments.ShapesMaker;
import deform.tests.BasicDemo;
import deform.texture.Java2DTexture;

public class Over extends TexturedShape{

	final TexturedShape[] shapes;
	final BBox b;
	
	public Over(TexturedShape[] shapes){
		this.shapes = shapes;
		BBox tmp = BBox.emptyBBox;
		for(TexturedShape ts : shapes){
			tmp = tmp.union(ts.getBBox());
		}
		b = tmp;
	}
	
	@Override
	public
	BBox getBBox() {
		return b;
	}

	@Override
	public
	void render(Transform t,  RenderContext ctx) {
		BBox nb = t.transformBBox(this.b);
		if(nb.overlaps(ctx.area)){
			nb = nb.intersections(ctx.area);

			
			for(TexturedShape ts : shapes){
				ts.render(t,ctx);
			}
		} else {
			System.out.println("Skip!!!!!!");
		}
	}

	
}
