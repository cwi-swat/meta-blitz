package bezier.image.sample;
class Sample1{
	final double a;

	Sample1(double a){
		this.a = a;
	}	

    Sample1 add(Sample1 rhs){
		return new Sample1(this.a + rhs.a);
	}
 
    Sample1 mul(Sample1 rhs){
		return new Sample1(this.a * rhs.a);
	}

	Sample1 mul(double d){
		return new Sample1(this.a * d);
	}

	Sample1 div(double d){
		return new Sample1(this.a / d);
	}

	Sample1 lerp(double d,Sample1 rhs){
		double od = 1.0 - d;
		return new Sample1(od * this.a + d * rhs.a);
	}
	
	Sample2 concat(Sample1 rhs) {
		return new Sample2(this.a,rhs.a);
	}
	Sample3 concat(Sample2 rhs) {
		return new Sample3(this.a,rhs.a,rhs.b);
	}
	Sample4 concat(Sample3 rhs) {
		return new Sample4(this.a,rhs.a,rhs.b,rhs.c);
	}
	Sample5 concat(Sample4 rhs) {
		return new Sample5(this.a,rhs.a,rhs.b,rhs.c,rhs.d);
	}
	Sample6 concat(Sample5 rhs) {
		return new Sample6(this.a,rhs.a,rhs.b,rhs.c,rhs.d,rhs.e);
	}
	Sample7 concat(Sample6 rhs) {
		return new Sample7(this.a,rhs.a,rhs.b,rhs.c,rhs.d,rhs.e,rhs.f);
	}
	
}