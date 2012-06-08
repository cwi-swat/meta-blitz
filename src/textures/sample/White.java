package textures.sample;

import static textures.Util.clamp;

import java.awt.image.DataBuffer;

import textures.Util;
import textures.interfaces.ISample;

public class White implements ISample<White>{
	public final int alpha;
	
	public White(int x) {
		this.alpha = x;
	}



	@Override
	public int getSize() {
		return 1;
	}

	@Override
	public White mul(int d) {
		d = clamp(d);
		return new White(alpha*d >> 8);
	}



	@Override
	public White add(White rhs) {
		return new White(Util.clamp(alpha + rhs.alpha));
	}



	@Override
	public White read(DataBuffer data, int index) {
		return new White(data.getElem(index));
	}



	@Override
	public void write(DataBuffer data, int index) {
		data.setElem(index, alpha);
		
	}

}
