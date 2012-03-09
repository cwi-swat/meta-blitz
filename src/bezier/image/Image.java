package bezier.image;

import bezier.points.Vec;
import bezier.util.BBox;

public interface Image {
	BBox getBBox();
	int nrChannels();
	Sample getSample(Vec p);
}
