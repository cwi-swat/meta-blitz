package textures.texturedpaths;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;

import paths.paths.paths.Path;
import paths.points.twod.BBox;
import textures.examples.Texture;
import textures.interfaces.ISample;
import textures.interfaces.ITexturedPath;
import textures.sample.LocatedBufferedImage;
import transform.ITransform;
import demo.DummyAWTSHape;

public class TexturedPath<Sample extends ISample<Sample>> implements
		ITexturedPath<Sample> {

	public final Path path;
	public final Texture<Sample> texture;
	private final Sample zero;
	private final int sampleSize;

	public TexturedPath(Path path, Texture<Sample> texture) {
		this.path = path;
		this.texture = texture;
		this.zero = texture.sample(0, 0).mul(0);
		this.sampleSize = zero.getSize();
	}

	public void render(Graphics2D gb, BBox b) {
		BBox me = path.getBBox();
		BBox actual = b.intersections(me);
		int w = actual.getWidthInt();
		int h = actual.getHeightInt();
		BufferedImage img = new BufferedImage(actual.getWidthInt(),
				actual.getHeightInt(), getImageType(zero.getSize()));
		Graphics2D g = (Graphics2D) img.getGraphics();
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, // Anti-alias!
				RenderingHints.VALUE_ANTIALIAS_ON);
		g.setRenderingHint(RenderingHints.KEY_RENDERING,
				RenderingHints.VALUE_RENDER_QUALITY);
		g.setColor(Color.white);
		g.fill(new DummyAWTSHape(path, actual.getXInt(), actual.getYInt()));
		g.dispose();
		DataBuffer imageBuf = img.getRaster().getDataBuffer();
		int toX = actual.getXInt() + w;
		int toY = actual.getYInt() + h;
		int index = 0;
		for (int iy = actual.getYInt(); iy < toY; iy++) {
			for (int ix = actual.getXInt(); ix < toX; ix++) {
				sample(imageBuf, index, ix + 0.5, iy + 0.5).write(imageBuf,
						index);
				index += sampleSize;
			}
		}
		gb.drawImage(img, null, actual.getXInt(), actual.getYInt());
	}

	private int getImageType(int size) {
		switch (size) {
		case 1:
			return BufferedImage.TYPE_BYTE_GRAY;
		case 2:
			return BufferedImage.TYPE_3BYTE_BGR;
		case 3:
			return BufferedImage.TYPE_3BYTE_BGR;
		case 4:
			return BufferedImage.TYPE_4BYTE_ABGR;
		}
		throw new Error("Unkown image size!");
	}

	public Sample sample(DataBuffer alpha, int alphaIndex, double x, double y) {
		int i = alpha.getElem(alphaIndex);
		if (i == 0) {
			return zero;
		} else {
			Sample s = texture.sample(x, y);
			if (i == 255) {
				return s;
			}
			return s.mul(i);
		}
	}

	public Sample sample(double x, double y) {
		int alphaIndex = (int) y + (int) x;
		return zero;
		// return sample(alphaIndex, x,y);
	}

	@Override
	public Path getPath() {
		return path;
	}

	public ITexturedPath<Sample> transform(ITransform t) {
		return new TransformedTexturedPath<Sample>(t, path, texture);
	}

}
