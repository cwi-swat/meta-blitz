package textures.interfaces;

public interface ICombineTexture2
	<Sample extends ISample<Sample>,
	SampleInA extends ISample<SampleInA>,
	SampleInB extends ISample<SampleInB>>
{
	Sample zero();
	Sample sample(double x, double y, SampleInA ina, SampleInB inb);	
}
