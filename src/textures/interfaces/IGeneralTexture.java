package textures.interfaces;

public interface IGeneralTexture<Sample extends ISample<Sample>, SampleArg extends ISample<SampleArg>> {
	Sample sample(double x, double y, SampleArg[] args);
}
