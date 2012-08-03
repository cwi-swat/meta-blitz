package deform.render;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Paint;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
import java.util.ArrayList;
import java.util.List;

import paths.points.oned.Interval;

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
	final ColorBufferGraphics img;
	final FillBufferGraphics fill;
	final BBox zeroBased;
	
	deform.shapes.Shape clip;
	
	public RenderContext(BBox area,deform.shapes.Shape clip) {
		this.clip = clip;
		this.area = area;
		this.size = area;
		this.zeroBased = new BBox(new Interval(0, area.getWidthInt()), new Interval(0, area.getHeightInt()));
		this.img = new ColorBufferGraphics(area);
		this.fill = new FillBufferGraphics(area);
	
	}
	
	
	public void renderShapeOutline(Transform t, deform.shapes.Shape s){
		t = modTrans(t);
		List<SegPath> res = new ArrayList<SegPath>();
		if(clip!=null){
			res = new ArrayList<SegPath>();
			clip.render(area, IdentityTransform.Instance, res);
			fill.g.setClip(ShapesMaker.makePath(res));
			res.clear();
		} else {
			fill.g.setClip(null);
		}
		s.render(zeroBased, t, res);
		
//		fill.g.clearRect(c.getXInt(), c.getYInt(), c.getWidthInt(), c.getHeightInt());
		fill.g.fill(ShapesMaker.makePath(res));
		fill.g.setClip(null);
	}
	
	private Transform modTrans(Transform t) {
		if(!size.getLeftUp().isEq(Vec.ZeroVec)){
			return deform.transform.affine.AffineTransform.translate(size.getLeftUp().negate()).compose(t);
		} else {
			return t;
		}
	}


	public int getAlpha(int loc){
		return fill.imgBuf.getElem(loc);
	}
	
	public void addElem(int elem, Color c){
		img.addElem(elem, c);
	}
	
	public Color getElem(int elem){
		return img.getElem(elem);
	}

	public void renderConstantColor(Paint p,Transform  t, deform.shapes.Shape s) {
		t = modTrans(t);
		List<SegPath> res = new ArrayList<SegPath>();
		AffineTransform oldTrans = img.g.getTransform();
		if(clip!=null){
			res = new ArrayList<SegPath>();
			clip.render(area, IdentityTransform.Instance, res);
			img.g.setClip(ShapesMaker.makePath(res));
			res.clear();
		}
		img.g.setPaint(p);
		s.render(zeroBased, t, res);
		img.g.fill(ShapesMaker.makePath(res));
		img.g.setClip(null);
		img.g.setTransform(oldTrans);

	}
	

	public void renderJava2dPaintShape(Paint p,deform.transform.affine.AffineTransform t, deform.shapes.Shape s) {
		t = (deform.transform.affine.AffineTransform) modTrans(t);
		List<SegPath> res = new ArrayList<SegPath>();
		AffineTransform oldTrans = img.g.getTransform();
		if(clip!=null){
			res = new ArrayList<SegPath>();
			clip.render(area, IdentityTransform.Instance, res);
			img.g.setClip(ShapesMaker.makePath(res));
			res.clear();
		}
		img.g.setPaint(p);
		img.g.setTransform(t.toJava2DTransform());
		s.render(BBox.everything, IdentityTransform.Instance, res);
		img.g.fill(ShapesMaker.makePath(res));
		img.g.setClip(null);
		img.g.setTransform(oldTrans);

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
		img.g.setClip(ShapesMaker.makePath(res));
		if(!size.getLeftUp().isEq(Vec.ZeroVec)){
			img.g.translate(-size.getXInt(), -size.getYInt());
			AffineTransform p = img.g.getTransform();
			p.concatenate(t.toJava2DTransform());
			img.g.setTransform(p);
			} else {
			AffineTransform trans = t.toJava2DTransform();
			if(trans!=null) img.g.setTransform(trans);
		}
		img.g.drawImage( i, 0, 0, null);
		img.g.setClip(null);
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
			img.g.setClip(ShapesMaker.makePath(res));
			res.clear();
		}
		img.g.drawImage( i, (int)leftUp.x, (int)leftUp.y, null);
		img.g.setClip(null);
		
	}


	public BufferedImage getImage() {
		return img.img;
	}
	
	

}
