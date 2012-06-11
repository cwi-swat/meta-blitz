package textures.interfaces;

import java.awt.image.DataBuffer;

public interface ISample<A> {
	A mul(int d);

	A add(A rhs);

	A read(DataBuffer data, int index);

	void write(DataBuffer data, int index);

	int getSize();

}
