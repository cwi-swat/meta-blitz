package textures.sample;

import java.awt.image.BufferedImage;

public class LocatedBufferedImage {
	public final int x, y;
	public final BufferedImage img;

	public LocatedBufferedImage(int x, int y, BufferedImage img) {
		this.x = x;
		this.y = y;
		this.img = img;
	}
}
