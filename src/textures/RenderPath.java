package textures;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.color.ICC_ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;

import demo.DummyAWTSHape;

import paths.paths.paths.Path;
import paths.transform.AffineTransformation;
import textures.sample.Image;
import textures.sample.Sample1;

public class RenderPath {

	public static DataBufferByte render(Path p, int x, int y , int w, int h){
		BufferedImage bi = new BufferedImage(w, h, BufferedImage.TYPE_BYTE_GRAY);
		Graphics2D g = (Graphics2D)bi.getGraphics();
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, // Anti-alias!
	                RenderingHints.VALUE_ANTIALIAS_ON);
	   	g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		g.setColor(new Color(ICC_ColorSpace.getInstance(ICC_ColorSpace.CS_GRAY), new float[]{1.0f}, 1.0f));
		g.fill(new DummyAWTSHape(p.transform(AffineTransformation.id.translate(-x,-y))));
		g.dispose();
		
		return (DataBufferByte) bi.getRaster().getDataBuffer();
	}
	
}
