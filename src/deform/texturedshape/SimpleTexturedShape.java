package deform.texturedshape;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
import java.util.ArrayList;
import java.util.List;



import deform.BBox;
import deform.Color;
import deform.Texture;
import deform.Transform;
import deform.segments.SegPath;
import deform.segments.ShapesMaker;
import deform.shapes.Shape;
import deform.texture.TransformTexture;
import deform.transform.IdentityTransform;
import deform.transform.TransformBBox;

public class SimpleTexturedShape implements TexturedShape{
	final Texture tex;
	final Shape shape;
	
	public SimpleTexturedShape(Texture tex, Shape shape) {
		this.tex = tex;
		this.shape = shape;
	}
	
	public LocatedImage render(Transform t,BBox b) {
		BBox me = TransformBBox.transformBBox(t,b);
		if(!me.overlaps(b)){
			return LocatedImage.empty;
		}

		BBox actual = b.intersections(me);
		int w = actual.getWidthInt();
		int h = actual.getHeightInt();
		BufferedImage img = new BufferedImage(actual.getWidthInt(),
				actual.getHeightInt(), BufferedImage.TYPE_4BYTE_ABGR);
		Graphics2D g = (Graphics2D) img.getGraphics();
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, // Anti-alias!
				RenderingHints.VALUE_ANTIALIAS_ON);
		g.setRenderingHint(RenderingHints.KEY_RENDERING,
				RenderingHints.VALUE_RENDER_QUALITY);
		g.setColor(java.awt.Color.white);
		List<SegPath> res = new ArrayList<SegPath>();
		shape.render(b, t, res);
		Texture tex = new TransformTexture(t, this.tex);
		g.fill(ShapesMaker.makePath(res));
		g.dispose();
		DataBuffer imageBuf = img.getRaster().getDataBuffer();
		int toX = actual.getXInt() + w;
		int toY = actual.getYInt() + h;
		int index = 0;
		for (int iy = actual.getYInt(); iy < toY; iy++) {
			for (int ix = actual.getXInt(); ix < toX; ix++) {
				int alpha = imageBuf.getElem(index) ;
				if(alpha!= 0){
					Color c= tex.sample(new deform.Vec(ix + 0.5, iy + 0.5)).mul(alpha);
					imageBuf.setElem(index++,c.a);
					imageBuf.setElem(index++,c.b);
					imageBuf.setElem(index++,c.g);
					imageBuf.setElem(index++,c.r);
				} else {
					index += Color.SampleSize;
				}
			}
		}
		return new LocatedImage(actual.getLeftUp(), img);
	}

	@Override
	public BBox getBBox() {
		return shape.bbox;
	}
	
	
	

}
