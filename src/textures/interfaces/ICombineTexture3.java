package textures.interfaces;

public interface ICombineTexture3
	<Sample extends ISample<Sample>,
	SampleInA extends ISample<SampleInA>,
	SampleInB extends ISample<SampleInB>,
	SampleInC extends ISample<SampleInC>>{
	Sample zero();
	Sample sample(double x, double y, SampleInA ina, SampleInB inb,SampleInC inc);	
}