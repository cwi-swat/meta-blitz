package textures.sample;

import static textures.Util.clamp;

import java.awt.image.DataBuffer;

import textures.interfaces.ISample;

public class WhiteAlpha implements ISample<WhiteAlpha> {
	public final int gray, alpha;

	public WhiteAlpha(int x, int y) {
		this.gray = x;
		this.alpha = y;
	}

	@Override
	public int getSize() {
		return 2;
	}

	@Override
	public WhiteAlpha mul(int d) {
		d = clamp(d);
		return new WhiteAlpha(gray * d >> 8, alpha * d >> 8);
	}

	@Override
	public WhiteAlpha add(WhiteAlpha rhs) {
		return new WhiteAlpha(clamp(gray + rhs.gray), clamp(alpha + rhs.alpha));
	}

	@Override
	public WhiteAlpha read(DataBuffer data, int index) {
		return new WhiteAlpha(data.getElem(index + 1), data.getElem(index));
	}

	@Override
	public void write(DataBuffer data, int index) {
		data.setElem(index + 1, gray);
		data.setElem(index, alpha);

	}
}
