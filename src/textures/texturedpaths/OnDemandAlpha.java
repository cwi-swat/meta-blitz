package textures.texturedpaths;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;

import demo.DummyAWTSHape;

import paths.paths.paths.Path;
import paths.points.twod.BBox;
import paths.points.twod.Vec;
import textures.interfaces.ITexture;
import textures.interfaces.Sample;

public class OnDemandAlpha{
	private static final int PrerenderDistance = 50;
	private static final Vec PrerenderVec = new Vec(PrerenderDistance, PrerenderDistance);
	public final Path p;
	private BufferedImage alphaLocal;
	private DataBuffer buf;
	private BBox alphaBBox;
	
	
	
	public OnDemandAlpha(Path p) {
		this.p = p;
		alphaBBox = BBox.emptyBBox;
	}

	
	public int sample(Vec v) {
		if(p.getBBox().isInside(v)){
			if(!alphaBBox.isInside(v)){
				renderExtra(v);
			}
				Vec local = v.sub(alphaBBox.getLeftUp());
				int x= (int)local.x; int y = (int)local.y;
				return buf.getElem(y * alphaLocal.getWidth() + x);
		} else {
			return 0;
		}
	}


	private void renderExtra(Vec v) {
		BBox extra = BBox.from2Points(v.sub(PrerenderVec), v.add(PrerenderVec));
		BBox newAlphaBbox = alphaBBox.union(extra).intersections(p.getBBox());
		
		BufferedImage newAlpha = new BufferedImage(
				newAlphaBbox.getWidthInt()+1, 
				newAlphaBbox.getHeightInt() + 1, BufferedImage.TYPE_BYTE_GRAY);
		Graphics2D g = (Graphics2D)newAlpha.getGraphics();
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, // Anti-alias!
				RenderingHints.VALUE_ANTIALIAS_ON);
		g.setRenderingHint(RenderingHints.KEY_RENDERING,
				RenderingHints.VALUE_RENDER_QUALITY);
		if(alphaLocal!=null){
				Area r2 = new Area(new Rectangle2D.Double(newAlphaBbox.getXInt(), newAlphaBbox.getYInt(),
						newAlphaBbox.getWidthInt()+1, newAlphaBbox.getHeightInt()+1));
				r2.subtract(new Area(new Rectangle2D.Double(alphaBBox.getXInt(), alphaBBox.getYInt(),
						alphaBBox.getWidthInt()+1, alphaBBox.getHeightInt()+1)));
				r2.transform(AffineTransform.getTranslateInstance(-newAlphaBbox.getXInt(), -newAlphaBbox.getYInt()));
				g.drawImage(alphaLocal, null, newAlphaBbox.getXInt() - alphaBBox.getXInt(),  newAlphaBbox.getYInt() - alphaBBox.getYInt());
				g.setClip(r2);
		}
		
		g.fill(new DummyAWTSHape(p,newAlphaBbox.getXInt() ,newAlphaBbox.getYInt() ));
		g.dispose();
		this.alphaLocal = newAlpha;
		this.alphaBBox = newAlphaBbox;
		this.buf = alphaLocal.getRaster().getDataBuffer();

	}
	
	
	
	
	
}
