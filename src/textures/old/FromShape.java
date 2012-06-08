package textures.old;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.color.ICC_ColorSpace;
import java.awt.image.BufferedImage;


import textures.old.functions.Div;
import textures.old.generated.RasterInstances;
import textures.old.generated.SampleInstances;
import textures.old.generated.RasterInstances.Raster1;
import textures.old.generated.SampleInstances.Sample1;
import transform.AffineTransformation;



public class FromShape {

//	public static Image<Sample1> paths2img(Paths p){
//		if(p.paths.isEmpty()){
//			return new Raster1(new PixelArea(0,0,0,0),new double[]{});
//		}
//		int x = (int)p.getBBox().x;
//		int y = (int)p.getBBox().y;
//		int xr = (int)Math.ceil(p.getBBox().xr);
//		int yd = (int)Math.ceil(p.getBBox().yd);
//		int w = xr - x; int h = yd - y;
//		BufferedImage bi = new BufferedImage(w, h, BufferedImage.TYPE_BYTE_GRAY);
//		Graphics2D g = (Graphics2D)bi.getGraphics();
//		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, // Anti-alias!
//	                RenderingHints.VALUE_ANTIALIAS_ON);
//	   	g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
//		g.setColor(new Color(ICC_ColorSpace.getInstance(ICC_ColorSpace.CS_GRAY), new float[]{1.0f}, 1.0f));
//		g.fill(new DummyAWTSHape(p.transform(AffineTransformation.id.translate(-x,-y)).getPathIterator()));
//		g.dispose();
//		double[] data = new double[w * h];
//		bi.getRaster().getPixels(0, 0, w, h, data);
//		Raster1 r = new RasterInstances.Raster1(new PixelArea(x,y,w,h), data);
//		return new Div<SampleInstances.Sample1>(r, 255);
//	}
	
	
	
}
