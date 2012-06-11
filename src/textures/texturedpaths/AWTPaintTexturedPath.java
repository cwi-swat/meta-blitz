package textures.texturedpaths;

import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Rectangle2D;

import demo.DummyAWTSHape;

import paths.paths.paths.Path;
import paths.points.twod.BBox;
import textures.examples.AWTRenderedTexture;
import textures.examples.Texture;
import textures.interfaces.ISample;
import textures.interfaces.ITexturedPath;
import transform.ITransform;

public class AWTPaintTexturedPath <Sample extends ISample<Sample>> implements
ITexturedPath<Sample> {
	public final Path path;
	public final AWTRenderedTexture<Sample> texture;

	public AWTPaintTexturedPath(Path path, AWTRenderedTexture<Sample> texture) {
		this.path = path;
		this.texture = texture;
	}

	@Override
	public Path getPath() {
		return path;
	}

	@Override
	public void render(Graphics2D g, BBox b) {
		g.setPaint(texture.getPaint());
		g.fill(new DummyAWTSHape(path));
		
	}

	private Shape getRectangleFromBBox(BBox b) {
		return new Rectangle2D.Double(b.getXInt(), b.getYInt(), b.getWidthInt(), b.getHeightInt());
	}

	public ITexturedPath<Sample> transform(ITransform t) {
		if(t.isAffine()){
			return new AWTPaintTexturedPathAffine<Sample>(t.getAffine().to, path.transform(t), texture);
		}
		return new TransformedTexturedPath<Sample>(t, path, texture);
	}
	
}
