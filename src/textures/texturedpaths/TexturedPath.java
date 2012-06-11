package textures.texturedpaths;

 

import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
import java.awt.image.DataBufferByte;

import paths.paths.paths.Path;
import textures.interfaces.ISample;
import textures.interfaces.ITexture;
import textures.interfaces.ITexturedPath;

public class TexturedPath<Sample extends ISample<Sample>>
	implements ITexturedPath<Sample>{
	
	public final Path path;
	public final ITexture<Sample> texture;
	private final BufferedImage image;
	private final Sample zero;
	private final int width, height, sampleSize, rowSize;
	DataBuffer alpha;
	DataBuffer imageBuf;
	
	
	public TexturedPath(Path path, ITexture<Sample> texture) {
		this.path = path;
		this.texture = texture;
		this.zero = texture.sample(0, 0).mul(0);
		this.width = path.getBBox().getWidthInt();
		this.height = path.getBBox().getHeightInt();
		this.sampleSize = zero.getSize();
		this.rowSize = width * sampleSize;
		this.image = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
		this.alpha = path.getImage();
		this.imageBuf = image.getRaster().getDataBuffer();
	}
	
	public void render(int fromX, int fromY, int width, int height){
		int x = path.getBBox().getXInt();
		int y = path.getBBox().getYInt();
		int xr = x + path.getBBox().getWidthInt();
		int yd = y + path.getBBox().getHeightInt();
		fromX = Math.max(x,fromX); fromY = Math.max(y, fromY);
		int toX = Math.min(xr,fromX + width); int toY = Math.min(yd,fromY + height);
		int relx = fromX - x; int rely = fromY - y;
		int relmx =  toX - x; int relmy = toY - y;

		int indexAlpha = rely * this.width + relx ;
		int index = indexAlpha * sampleSize;
		System.out.printf("%d %d %d %d %d\n", relx, relmx, rely, relmy, indexAlpha);
		int endOfLineDiffAlpha = this.width - relmx + relx;
		int endOfLineDiff = endOfLineDiffAlpha * sampleSize;
		for(int iy = rely ; iy < relmy ; iy++){
			for(int ix = relx ; ix < relmx ;ix++){
				sample(indexAlpha,ix + 0.5,iy + 0.5).write(imageBuf, index);
				index+=sampleSize;
				indexAlpha++;
			}
			index+=endOfLineDiff;
			indexAlpha+=endOfLineDiffAlpha;
		}
	}
	
	public Sample sample(int alphaIndex, double x, double y) {
		int i = alpha.getElem(alphaIndex);
		if(i == 0){
			return zero;
		} else {
			Sample s = texture.sample(x, y);
			if(i == 255){
				return s;
			}
			return s.mul(i);
		}
	}

	@Override
	public Sample sample(double x, double y) {
		int alphaIndex = (int)y * width + (int)x;
		return sample(alphaIndex, x,y);
	}

	@Override
	public Path getPath() {
		return path;
	}

	@Override
	public BufferedImage getImage() {
		return image;
	}
	


}
