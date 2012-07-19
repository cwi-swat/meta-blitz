package deform.render;

import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
import java.util.ArrayList;
import java.util.List;

import deform.BBox;
import deform.Color;
import deform.Transform;
import deform.segments.LineTo;
import deform.segments.SegPath;
import deform.segments.ShapesMaker;

public class RenderContext {
	public final BBox area;
	final AffineTransform trans;
	public final BufferedImage img;
	final DataBuffer imgBuf;
	final Graphics2D g;
	final BufferedImage imgFill;
	final DataBuffer fillBuf;
	final Graphics2D gFill;
	final Shape clip;
	
	public RenderContext(BBox area,AffineTransform trans, Shape clip) {
		this.trans = trans;
		this.clip = clip;
		this.area = area;
		img = new BufferedImage(area.getWidthInt(),
				area.getHeightInt(), BufferedImage.TYPE_4BYTE_ABGR_PRE);
		g = (Graphics2D) img.getGraphics();
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, // Anti-alias!
				RenderingHints.VALUE_ANTIALIAS_ON);
		g.setRenderingHint(RenderingHints.KEY_RENDERING,
				RenderingHints.VALUE_RENDER_QUALITY);
		imgBuf = img.getRaster().getDataBuffer();
		imgFill = new BufferedImage(area.getWidthInt(),
				area.getHeightInt(), BufferedImage.TYPE_BYTE_GRAY);
		gFill = (Graphics2D) imgFill.getGraphics();
		gFill.setRenderingHint(RenderingHints.KEY_ANTIALIASING, // Anti-alias!
				RenderingHints.VALUE_ANTIALIAS_ON);
		gFill.setRenderingHint(RenderingHints.KEY_RENDERING,
				RenderingHints.VALUE_RENDER_QUALITY);
		fillBuf = imgFill.getRaster().getDataBuffer();
		if(trans!=null){
			g.setTransform(trans);
			gFill.setTransform(trans);
		}
	}
	
	public void setPaint(Paint p){
		g.setPaint(p);
	}
	
	public void setShape(Transform t, deform.shapes.Shape s){
		List<SegPath> res = new ArrayList<SegPath>();
		s.render(area, t, res);
		BBox c = s.bbox;
		gFill.clearRect(c.getXInt(), c.getYInt(), c.getWidthInt(), c.getHeightInt());
		gFill.fill(ShapesMaker.makePath(res));
	}
	
	public int getAlpha(int loc){
		return fillBuf.getElem(loc);
	}
	
	public void addElem(int elem, Color c){
		int r = elem + 3;
		int g = elem + 2;
		int b = elem + 1;
		int a = elem;
		Color nc = 
				c.add(Color.color(imgBuf.getElem(r), imgBuf.getElem(g), imgBuf.getElem(b), imgBuf.getElem(a)))
		;
		imgBuf.setElem(r, nc.r);
		imgBuf.setElem(g, nc.g);
		imgBuf.setElem(b, nc.b);
		imgBuf.setElem(a, nc.a);
	}

	public void setTransform(java.awt.geom.AffineTransform java2dTransform) {
		if(java2dTransform!=null){
			g.setTransform(java2dTransform);
			gFill.setTransform(java2dTransform);
		}
		
	}
	
	public void resetTransform(){
		setTransform(new java.awt.geom.AffineTransform());
	}

	public void directShape(Transform t, deform.shapes.Shape s) {
		List<SegPath> res = new ArrayList<SegPath>();
		s.render(area, t, res);
		g.fill(ShapesMaker.makePath(res));
		
	}

	public void renderImage(BufferedImage i, deform.transform.affine.AffineTransform t,
			deform.shapes.Shape s) {
		List<SegPath> res = new ArrayList<SegPath>();
		s.render(area, t, res);
//		res.add(
//				new SegPath(area.getLeftUp(),
//						new LineTo(area.getRightUp()),
//						new LineTo(area.getRightDown()),
//						new LineTo(area.getLeftDown()),
//						new LineTo(area.getLeftUp())));
		g.setClip(ShapesMaker.makePath(res));
		AffineTransform trans = t.toJava2DTransform();
		if(trans!=null) g.setTransform(trans);
		g.drawImage( i, 0, 0, null);
		g.setClip(null);
		resetTransform();
	}
	
	

}
