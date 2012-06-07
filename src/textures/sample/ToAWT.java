package textures.sample;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;

import textures.interfaces.ITexturedPath;
import textures.texturedpaths.TexturedPath;

public class ToAWT {

	
	public static void toAWT(Graphics2D g, ITexturedPath<Color> c){
		BufferedImage bi = new BufferedImage(c.getPath().getBBox().getWidthInt(), 
											c.getPath().getBBox().getHeightInt(),
											BufferedImage.TYPE_3BYTE_BGR);
		DataBuffer buf = bi.getRaster().getDataBuffer();
		Image<Color> img = c.getImage();
		int size = img.samples.length;
		for(int i = 0 ; i <  size; i++){
			int s = (int)(img.samples[i] * 255);
			buf.setElem(i,s);
		}
		g.drawImage(bi, c.getPath().getBBox().getXInt(), c.getPath().getBBox().getYInt(), null);
	}
}
