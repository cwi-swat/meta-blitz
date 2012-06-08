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
		int relx = fromX - path.getBBox().getXInt();
		int rely = fromY - path.getBBox().getYInt();
		int startx = Math.max(relx, 0);
		int starty = Math.max(rely, 0);
		int w= Math.min(width, path.getBBox().getWidthInt() - startx);
		int h = Math.min(height,path.getBBox().getHeightInt() - starty);

		int index = starty * rowSize + startx * sampleSize;
		int indexAlpha = starty * width + startx;
		for(int y = starty ; y < h ; y++){
			for(int x = startx ; x < w ; x++){
				sample(indexAlpha,x,y).write(imageBuf, index);
				index+=sampleSize;
				indexAlpha++;
			}
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
