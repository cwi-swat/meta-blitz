package deform.texture;

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

public class RepeatingImage implements Texture , Java2DTexture{

	public final BufferedImage i;
	final int width, height;
	
	
	public RepeatingImage(String path){
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
//		System.out.println(point);
		double x ;
		if(point.x < 0){
			x = point.x + ((int)(-point.x / width) + 1) * width;	
		} else if(point.x > width ){
			x = point.x - (int)(point.x / width) * width;	
		} else {
			x = point.x;
		}
		double y ;
		if(point.y < 0){
			y = point.y + ((int)(-point.y / height) + 1) * height;	
		} else if(point.y > height ){
			y = point.y - ((int)(point.y / height)) * height;	
		} else {
			y = point.y;
		}
//		System.out.printf("%f %f %d %d\n", x, y, width , height);
		java.awt.Color c = new java.awt.Color(i.getRGB((int)x, (int)y),true);
		return Color.color(c.getRed(),c.getGreen(),c.getBlue(),c.getAlpha());
	}

	@Override
	public Paint getPaint() {
		return new TexturePaint(i, new Rectangle2D.Double(0,0,width,height));
	}

	public Paint getTranslatedPaint(Transform t) {
		Vec v = t.to(Vec.ZeroVec);
		return new TexturePaint(i, new Rectangle2D.Double(v.x,v.y,width,height));
	}

	
}