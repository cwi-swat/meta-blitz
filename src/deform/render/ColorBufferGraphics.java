package deform.render;

import java.awt.image.BufferedImage;

import deform.BBox;
import deform.Color;

public class ColorBufferGraphics extends BufferedGraphics{

	ColorBufferGraphics(BBox area) {
		super(area, BufferedImage.TYPE_4BYTE_ABGR_PRE);
	}
	
	public Color getElem(int elem){
		int r = elem + 3;
		int g = elem + 2;
		int b = elem + 1;
		int a = elem;
		return Color.color(imgBuf.getElem(r), imgBuf.getElem(g), imgBuf.getElem(b), imgBuf.getElem(a));
	}

	public void addElem(int elem, Color c){
		int r = elem + 3;
		int g = elem + 2;
		int b = elem + 1;
		int a = elem;
		Color nc = Color.color(imgBuf.getElem(r), imgBuf.getElem(g), imgBuf.getElem(b), imgBuf.getElem(a)).add(c);
		;
		imgBuf.setElem(r, nc.r);
		imgBuf.setElem(g, nc.g);
		imgBuf.setElem(b, nc.b);
		imgBuf.setElem(a, nc.a);
	}
	
}
