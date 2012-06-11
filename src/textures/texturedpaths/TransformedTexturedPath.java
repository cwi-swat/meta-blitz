package textures.texturedpaths;

import paths.paths.paths.Path;
import textures.examples.Texture;
import textures.examples.TransformedTexture;
import textures.interfaces.ISample;
import transform.ITransform;

public class TransformedTexturedPath<Sample extends ISample<Sample>> extends
		TexturedPath<Sample> {

	public TransformedTexturedPath(ITransform t, Path path,
			Texture<Sample> texture) {
		super(path.transform(t), new TransformedTexture<Sample>(t, texture));
	}

}
