package bezier.image;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;

import bezier.composite.Paths;
import bezier.points.Matrix;
import bezier.util.DummySWTSHape;

public class RenderPaths {

	public static int ANTIALIASING_BORDER = 1;
	
	public static RasterImage renderPaths(Paths p){
		int x = (int)p.getBBox().x - ANTIALIASING_BORDER;
		int y = (int)p.getBBox().y - ANTIALIASING_BORDER;
		int xr = (int)Math.ceil(p.getBBox().xr) + ANTIALIASING_BORDER;
		int yd = (int)Math.ceil(p.getBBox().yd) + ANTIALIASING_BORDER;
		int w = xr - x;
		int h = yd - y;
		BufferedImage img = makeImage(p,x,y, w, h);
		Raster r = img.getRaster();
		int size = w*h*Sample.NR_CHANNELS;
		double[] res = r.getPixels(0, 0, w, h, (double[])null);
		assert res.length == size;
		return new RasterImage(x, y, w, h, Sample.NR_CHANNELS, res);
	}

	public static BufferedImage makeImage(Paths p,int x, int y, int w, int h) {
		BufferedImage img = new BufferedImage(w, h, BufferedImage.TYPE_4BYTE_ABGR);
		Graphics2D g = (Graphics2D)img.getGraphics();
		p = p.transform(Matrix.translate(-x, -y));
	    g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, // Anti-alias!
                RenderingHints.VALUE_ANTIALIAS_ON);
   	    g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		g.setColor(Color.BLACK);
		g.fill(new DummySWTSHape(p.getPathIterator()));
		g.dispose();
		return img;
	}
	
}
