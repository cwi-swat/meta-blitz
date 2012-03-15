package bezier.image;

public interface BoundedImage<A> extends Image<A>{

	int getX();
	int getY();
	int getWidth();
	int getHeight();
	
}
