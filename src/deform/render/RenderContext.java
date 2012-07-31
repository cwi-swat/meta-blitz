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
import deform.Vec;
import deform.segments.LineTo;
import deform.segments.SegPath;
import deform.segments.ShapesMaker;
import deform.shapes.IntersectionShapes;
import deform.transform.affine.IdentityTransform;

public class RenderContext {
	public BBox area;
	public BBox size;
	final deform.transform.affine.AffineTransform trans;
	public final BufferedImage img;
	final DataBuffer imgBuf;
	final Graphics2D g;
	final BufferedImage imgFill;
	final DataBuffer fillBuf;
	final Graphics2D gFill;
	deform.shapes.Shape clip;
	
	public RenderContext(BBox area,deform.transform.affine.AffineTransform trans, deform.shapes.Shape clip) {
		this.trans = trans;
		this.clip = clip;
		this.area = area;
		this.size = area;
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
	}
	
	
	public void renderShapeOutline(Transform t, deform.shapes.Shape s){
		List<SegPath> res = new ArrayList<SegPath>();
		
		if(clip!=null){
			res = new ArrayList<SegPath>();
			clip.render(area, IdentityTransform.Instance, res);
			gFill.setClip(ShapesMaker.makePath(res));
			res.clear();
		} else {
			gFill.setClip(null);
		}
		s.render(area, t, res);
//		for(SegPath ss : res){
//			System.out.println(ss);
//		}
		BBox c = t.transformBBox(s.bbox);
		
		gFill.clearRect(c.getXInt(), c.getYInt(), c.getWidthInt(), c.getHeightInt());
		gFill.fill(ShapesMaker.makePath(res));
		gFill.setClip(null);
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

	public deform.transform.affine.AffineTransform getTransform(){
		return trans;
	}
	
	public void setTransform(Transform t) {
		
	}

	public void renderJava2dPaintShape(Paint p,Transform t, deform.shapes.Shape s) {
		List<SegPath> res = new ArrayList<SegPath>();
		if(clip!=null){
			res = new ArrayList<SegPath>();
			clip.render(area, IdentityTransform.Instance, res);
			g.setClip(ShapesMaker.makePath(res));
			res.clear();
		}

		g.setPaint(p);
		s.render(area, t, res);
		g.fill(ShapesMaker.makePath(res));
		g.setClip(null);
		

	}
	
	public deform.shapes.Shape getClip(){
		return clip;
	}
	
	public void setClip(deform.shapes.Shape s){
		this.clip = s;
	}

	public void renderImage(BufferedImage i, deform.transform.affine.AffineTransform t,
			deform.shapes.Shape s) {
		List<SegPath> res = new ArrayList<SegPath>();
		if(clip!=null){
			s = new IntersectionShapes(clip, s);
		}
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
	}


	public BBox getBBox() {
		return area;
	}
	
	public void setBBox(BBox b){
		this.area = b;
	}


	public void renderMemo(Vec leftUp, BufferedImage i) {
		List<SegPath> res = new ArrayList<SegPath>();
		if(clip!=null){
			res = new ArrayList<SegPath>();
			clip.render(area, IdentityTransform.Instance, res);
			g.setClip(ShapesMaker.makePath(res));
			res.clear();
		}
		g.drawImage( i, (int)leftUp.x, (int)leftUp.y, null);
		g.setClip(null);
		
	}
	
	

}
