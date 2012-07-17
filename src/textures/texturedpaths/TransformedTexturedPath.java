package textures.texturedpaths;

import paths.paths.paths.Path;
import textures.examples.TransformedTexture;
import textures.interfaces.ITexture;
import transform.ITransform;

public class TransformedTexturedPath extends TexturedPath{

	public TransformedTexturedPath(ITransform t, Path path,
			ITexture texture) {
		
		super(path.transform(t), new TransformedTexture(t, texture));
//		System.out.println( path);
	}

}
