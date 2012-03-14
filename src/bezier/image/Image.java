package bezier.image;

public interface Image<Sample> {

	Sample get(double x, double y);
	
}
