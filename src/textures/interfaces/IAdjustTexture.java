package textures.interfaces;

public interface IAdjustTexture<Sample extends ISample<Sample>, SampleIn extends ISample<SampleIn>> {
	Sample sample(double x, double y, SampleIn in);
}
