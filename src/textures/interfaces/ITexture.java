package textures.interfaces;

public interface ITexture<Sample extends ISample<Sample>>{
	Sample sample(double x, double y);
}
