package deform.render;

import java.awt.image.BufferedImage;

import deform.BBox;

public class FillBufferGraphics extends BufferedGraphics{

	public FillBufferGraphics(BBox area) {
		super(area, BufferedImage.TYPE_BYTE_GRAY);
	}

	public int getAlpha(int loc){
		return imgBuf.getElem(loc);
	}
	
	
}
