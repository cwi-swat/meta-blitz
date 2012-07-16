package deform;

import static textures.Util.clamp;

import java.awt.image.DataBuffer;

import textures.interfaces.ISample;

public class Color {
	public final int r, g, b, a;

	public Color(int r, int g, int b, int a) {
		this.r = r;
		this.g = g;
		this.b = b;
		this.a = a;
	}


}
