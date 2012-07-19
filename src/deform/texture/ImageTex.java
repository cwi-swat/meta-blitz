package deform.texture;

import java.awt.Image;
import java.awt.Paint;
import java.awt.TexturePaint;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import deform.Color;
import deform.Texture;
import deform.Transform;
import deform.Vec;

public class ImageTex implements Texture , Java2DTexture{

	public final BufferedImage i;
	final int width, height;
	
	
	public ImageTex(String path){
		try {
			i = ImageIO.read(new File(path));
			width = i.getWidth()-1;
			height = i.getHeight()-1;
		} catch (IOException e) {
			throw new Error(e.toString());
		}
	}

	@Override
	public Color sample(Vec point) {
		if(point.x < 0 || point.y < 0 || point.x > width || point.y > height){
			return Color.transparent;
		}
		java.awt.Color c = new java.awt.Color(i.getRGB((int)point.x, (int)point.y),true);
		return Color.color(c.getRed(),c.getGreen(),c.getBlue(),c.getAlpha());
	}

	@Override
	public Paint getPaint() {
		return new TexturePaint(i, new Rectangle2D.Double(0,0,width,height));
	}
	
}
