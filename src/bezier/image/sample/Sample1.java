package bezier.image.sample;
public class Sample1{
	public final double a;

	public Sample1(double a){
		this.a = a;
	}	

    public Sample1 add(Sample1 rhs){
		return new Sample1(this.a + rhs.a);
	}
 
    public Sample1 mul(Sample1 rhs){
		return new Sample1(this.a * rhs.a);
	}

	public Sample1 mul(double d){
		return new Sample1(this.a * d);
	}

	public Sample1 div(double d){
		return new Sample1(this.a / d);
	}

	public Sample1 lerp(double d,Sample1 rhs){
		double od = 1.0 - d;
		return new Sample1(od * this.a + d * rhs.a);
	}
	
	public Sample2 concat(Sample1 rhs) {
		return new Sample2(this.a,rhs.a);
	}
	public Sample3 concat(Sample2 rhs) {
		return new Sample3(this.a,rhs.a,rhs.b);
	}
	public Sample4 concat(Sample3 rhs) {
		return new Sample4(this.a,rhs.a,rhs.b,rhs.c);
	}
	public Sample5 concat(Sample4 rhs) {
		return new Sample5(this.a,rhs.a,rhs.b,rhs.c,rhs.d);
	}
	public Sample6 concat(Sample5 rhs) {
		return new Sample6(this.a,rhs.a,rhs.b,rhs.c,rhs.d,rhs.e);
	}
	public Sample7 concat(Sample6 rhs) {
		return new Sample7(this.a,rhs.a,rhs.b,rhs.c,rhs.d,rhs.e,rhs.f);
	}
	
}