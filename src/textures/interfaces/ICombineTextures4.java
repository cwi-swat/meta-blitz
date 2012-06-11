package textures.interfaces;

public interface ICombineTextures4<Sample extends ISample<Sample>, SampleInA extends ISample<SampleInA>, SampleInB extends ISample<SampleInB>, SampleInC extends ISample<SampleInC>, SampleInD extends ISample<SampleInD>> {
	Sample sample(double x, double y, SampleInA ina, SampleInB inb,
			SampleInC inc, SampleInD ind);
}