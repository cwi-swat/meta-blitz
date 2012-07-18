package deform.texture;

import deform.Texture;
import deform.Transform;

public interface AffineTransformableTex {
	Texture transform(Transform t);
}
