package textures.old;

public interface Image<A> {

	A get(double x, double y);
	PixelArea getArea();
	
}
