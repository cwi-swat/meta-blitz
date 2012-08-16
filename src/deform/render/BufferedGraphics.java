package deform.render;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;

import deform.BBox;
import deform.Vec;

public class BufferedGraphics {
	final BufferedImage img;
	final DataBuffer imgBuf;
	final Graphics2D g;
	
	BufferedGraphics(BBox area, int type){
		img = new BufferedImage(area.getWidthInt(),
				area.getHeightInt(), type);
		g = (Graphics2D) img.getGraphics();
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, // Anti-alias!
				RenderingHints.VALUE_ANTIALIAS_ON);
		g.setRenderingHint(RenderingHints.KEY_RENDERING,
				RenderingHints.VALUE_RENDER_QUALITY);
//		g.setRenderingHint(RenderingHints., hintValue)
		imgBuf = img.getRaster().getDataBuffer();
	
	}
	
	
}
