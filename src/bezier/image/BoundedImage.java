package bezier.image;

public interface BoundedImage<Sample> extends Image<Sample>{

	int getX();
	int getY();
	int getWidth();
	int getHeight();
	
}
