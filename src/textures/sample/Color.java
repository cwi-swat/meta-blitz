package textures.sample;

import static textures.Util.*;

import java.awt.image.DataBuffer;

import textures.interfaces.ISample;
// alias Sample3
public class Color implements ISample<Color>{
	public final int r,g,b;
	public static final int mask = 127; 

	public Color(int x, int y, int z) {
		this.r = x;
		this.g = y;
		this.b = z;
	}

	@Override
	public Color mul(int d) {
		d = clamp(d);
		return new Color((r*d >> 8) , (g*d >> 8) , (b*d >> 8) );
	}

	@Override
	public Color add(Color rhs) {
		return new Color(clamp(r + rhs.r), clamp(g + rhs.g) , clamp(b + rhs.b) );
	}

	@Override
	public Color read(DataBuffer data, int index) {
		return new Color(data.getElem(index+2), 
						data.getElem(index+1),
						data.getElem(index));
	}

	@Override
	public void write(DataBuffer data, int index) {
		data.setElem(index+2, r );
		data.setElem(index+1, g );
		data.setElem(index, b );
	}

	@Override
	public int getSize() {
		return 3;
	}
	
	public String toString(){
		return String.format("(%d %d %d)", r,g,b);
//		return String.format("C(%f,%f,%f)",(r & mask)/255.0,(g & mask)/255.0,(b & mask)/255.0);
	}

}
