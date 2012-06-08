package textures.sample;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;

import textures.interfaces.ITexturedPath;
import textures.texturedpaths.TexturedPath;

public class ToAWT {

	
	public static void toAWT(Graphics2D g, ITexturedPath<Color> c, int width, int height){
		c.render(0,0,width,height);
		g.drawImage(c.getImage(), c.getPath().getBBox().getXInt(), c.getPath().getBBox().getYInt(), null);
	}
}
