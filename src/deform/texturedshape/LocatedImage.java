package deform.texturedshape;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;

import deform.Color;
import deform.Vec;

public class LocatedImage {
	public static final LocatedImage empty = new LocatedImage(Vec.ZeroVec, null);
	
	final Vec pos;
	final BufferedImage img;
	LocatedImage(Vec pos, BufferedImage img) {
		this.pos = pos;
		this.img = img;
	
	}
	
	public void draw(Graphics2D g){
		if(img != null){
			g.drawImage(img, (int)pos.x, (int)pos.y,  null);
		}
	}
	
}
