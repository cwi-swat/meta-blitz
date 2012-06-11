package textures.sample;

import static textures.Util.clamp;

import java.awt.image.DataBuffer;

import textures.interfaces.ISample;

public class ColorAlpha implements ISample<ColorAlpha> {
	public final int r, g, b, a;

	public ColorAlpha(int r, int g, int b, int a) {
		this.r = r;
		this.g = g;
		this.b = b;
		this.a = a;
	}

	@Override
	public ColorAlpha mul(int d) {
		d = clamp(d);
		return new ColorAlpha((r * d >> 8), (g * d >> 8), (b * d >> 8),
				(a * d) >> 8);
	}

	@Override
	public ColorAlpha add(ColorAlpha rhs) {
		return new ColorAlpha(clamp(r + rhs.r), clamp(g + rhs.g), clamp(b
				+ rhs.b), clamp(a + rhs.a));
	}

	@Override
	public ColorAlpha read(DataBuffer data, int index) {
		return new ColorAlpha(data.getElem(index + 3), data.getElem(index + 2),
				data.getElem(index + 1), data.getElem(index));
	}

	@Override
	public void write(DataBuffer data, int index) {
		data.setElem(index + 3, a);
		data.setElem(index + 2, b);
		data.setElem(index + 1, g);
		data.setElem(index, r);
	}

	@Override
	public int getSize() {
		return 4;
	}

}
