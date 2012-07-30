package deform.texturedshape;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import deform.BBox;
import deform.Color;
import deform.ColorCombine;
import deform.Transform;
import deform.render.LocatedImage;
import deform.render.ScanLiner;
import deform.segments.SegPath;
import deform.segments.ShapesMaker;
import deform.transform.affine.IdentityTransform;

public class CombineTexturedShape extends TexturedShape{
	
	final TexturedShape a, b;
	final ColorCombine comb;
	
	public CombineTexturedShape(TexturedShape a, TexturedShape b, ColorCombine comb) {
		this.a = a;
		this.b = b;
		this.comb = comb;
	}
	
	@Override
	public LocatedImage render(Transform t,BBox b, java.awt.geom.AffineTransform trans) {
		LocatedImage la = LocatedImage.empty;
		if(t.transformBBox(a.getBBox()).overlaps(b)){
			la = a.render(t,null, b);
		}
		LocatedImage lb = LocatedImage.empty;
		if(t.transformBBox(this.b.getBBox()).overlaps(b)){
			lb = this.b.render(t,null, b);
		}
		if(la == LocatedImage.empty) return lb;
		if(lb == LocatedImage.empty) return la;
		
		BBox actual = b.intersections(a.getBBox().union(this.b.getBBox()));
		int w = actual.getWidthInt();
		int h = actual.getHeightInt();
		BufferedImage img = new BufferedImage(actual.getWidthInt(),
				actual.getHeightInt(), BufferedImage.TYPE_4BYTE_ABGR);
		Graphics2D g = (Graphics2D) img.getGraphics();
		la.draw(g);
		lb.draw(g);
		g.dispose();
		BBox overlap = a.getBBox().intersections(this.b.getBBox());
		ScanLiner overlapscan = new ScanLiner(img,b, overlap);
		ScanLiner lascan = new ScanLiner(la.img,this.a.getBBox(), overlap);
		ScanLiner lbscan = new ScanLiner(lb.img,this.b.getBBox(), overlap);
		while(!overlapscan.isDone()){
			Color c = comb.combine(lascan.getColor(), lbscan.getColor());
			overlapscan.setElem(c);
			overlapscan.increment();
			lascan.increment();
			lbscan.increment();
		}
		return new LocatedImage(actual.getLeftUp(), img);
	}


	@Override
	public BBox getBBox() {
		return a.getBBox().union(b.getBBox());
	}


	@Override
	public boolean isJava2DRenderable() {
		return false;
	}
}
