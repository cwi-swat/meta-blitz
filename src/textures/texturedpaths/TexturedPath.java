package textures.texturedpaths;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;

import paths.paths.paths.QueryPath;
import paths.points.twod.Vec;
import textures.interfaces.ITexture;
import textures.interfaces.Sample;

import transform.AffineTransformation;
import transform.ITransform;
import deform.BBox;
import demo.DummyAWTSHape;

public class TexturedPath implements ITexturedPath {

	public final QueryPath path;
	public final ITexture texture;
	private OnDemandAlpha alphaOnDemand;
	
	public TexturedPath(QueryPath path, ITexture texture) {
		this.path = path;
		this.texture = texture;
		alphaOnDemand = new OnDemandAlpha(path);
	}

	public void render(Graphics2D gb, BBox b) {
		BBox me = path.getBBox();
		BBox actual = b.intersections(me);
		int w = actual.getWidthInt();
		int h = actual.getHeightInt();
		BufferedImage img = new BufferedImage(actual.getWidthInt(),
				actual.getHeightInt(), BufferedImage.TYPE_4BYTE_ABGR);
		Graphics2D g = (Graphics2D) img.getGraphics();
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, // Anti-alias!
				RenderingHints.VALUE_ANTIALIAS_ON);
		g.setRenderingHint(RenderingHints.KEY_RENDERING,
				RenderingHints.VALUE_RENDER_QUALITY);
		g.setColor(Color.white);
//		g.fill(new DummyAWTSHape(path, actual.getXInt(), actual.getYInt()));
//		System.out.println();
//		System.out.println();
		g.dispose();
		DataBuffer imageBuf = img.getRaster().getDataBuffer();
		int toX = actual.getXInt() + w;
		int toY = actual.getYInt() + h;
		int index = 0;
		for (int iy = actual.getYInt(); iy < toY; iy++) {
			for (int ix = actual.getXInt(); ix < toX; ix++) {
				sample(new Vec(ix + 0.5, iy + 0.5)).write(imageBuf,index);
//				sample(imageBuf, index, new Vec(ix + 0.5, iy + 0.5)).write(imageBuf,
//						index);
				index += Sample.SampleSize;
			}
		}
		gb.drawImage(img, null, actual.getXInt(), actual.getYInt());
	}

	public Sample sample(DataBuffer alpha, int alphaIndex, Vec v) {
		int i = alpha.getElem(alphaIndex);
		if (i == 0) {
			return Sample.zero;
		} else {
			Sample s = texture.sample(v);
			if (i == 255) {
				return s;
			}
			return s.mul(i);
		}
	}

	public Sample sample(Vec v) {
		int alpha = alphaOnDemand.sample(v);
		if(alpha == 0){
			return Sample.zero;
		}
		return texture.sample(v).mul(alpha);
	}

	public ITexturedPath transform(AffineTransformation t) {
		return new TransformedTexturedPath(t, path, texture);
	}




}
