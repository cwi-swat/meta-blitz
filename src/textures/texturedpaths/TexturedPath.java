package textures.texturedpaths;

 

import java.awt.image.DataBufferByte;

import paths.paths.paths.Path;
import textures.interfaces.ISample;
import textures.interfaces.ITexture;
import textures.interfaces.ITexturedPath;
import textures.sample.Image;
import textures.sample.Sample1;

public class TexturedPath<Sample extends ISample<Sample>>
	implements ITexturedPath<Sample>{
	
	public final Path path;
	public final ITexture<Sample> texture;
	private final Image<Sample> image;
	private final Sample zero;
	
	
	public TexturedPath(Path path, ITexture<Sample> texture) {
		this.path = path;
		this.texture = texture;
		this.zero = texture.sample(0, 0).mul(0);
		this.image = new Image<Sample>(path.getBBox().getWidthInt(), path.getBBox().getHeightInt(), zero);
		render();
	}
	
	private void render(){
		DataBufferByte alpha = path.getImage();
		int index = 0;
		int w= path.getBBox().getWidthInt();
		int h = path.getBBox().getHeightInt();
		for(int y = 0 ; y < h ; y++){
			for(int x = 0 ; x < w ; x++){
				int i = alpha.getElem(index);
				if(i != 0){
					double frac= i/255.0;
					Sample samp = texture.sample((double)x / w, (double)y / h).mul(frac);
					image.set(index, samp);
				} else {
					image.set(index, zero);
				}
				index++;
			}
		}
	}

	@Override
	public Sample sample(double x, double y) {
		return image.get((int)x, (int)y);
	}

	@Override
	public Path getPath() {
		return path;
	}

	@Override
	public Image<Sample> getImage() {
		return image;
	}
	


}
