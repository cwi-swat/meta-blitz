package textures.interfaces;

public interface ISample<A> {
	
	A mul(double d);
	A add(A rhs);
	A read(double[] data, int index);
	void write(double[] data, int index);
	int getSize();

}
