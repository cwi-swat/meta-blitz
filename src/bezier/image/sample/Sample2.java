package bezier.image.sample;
class Sample2{
	final double a,b;

	Sample2(double a,double b){
		this.a = a;
  this.b = b;
	}	

    Sample2 add(Sample2 rhs){
		return new Sample2(this.a + rhs.a,this.b + rhs.b);
	}
 
    Sample2 mul(Sample2 rhs){
		return new Sample2(this.a * rhs.a,this.b * rhs.b);
	}

	Sample2 mul(double d){
		return new Sample2(this.a * d,this.b * d);
	}

	Sample2 div(double d){
		return new Sample2(this.a / d,this.b / d);
	}

	Sample2 lerp(double d,Sample2 rhs){
		double od = 1.0 - d;
		return new Sample2(od * this.a + d * rhs.a,od * this.b + d * rhs.b);
	}
	
	Sample3 concat(Sample1 rhs) {
		return new Sample3(this.a,this.b,rhs.a);
	}
	Sample4 concat(Sample2 rhs) {
		return new Sample4(this.a,this.b,rhs.a,rhs.b);
	}
	Sample5 concat(Sample3 rhs) {
		return new Sample5(this.a,this.b,rhs.a,rhs.b,rhs.c);
	}
	Sample6 concat(Sample4 rhs) {
		return new Sample6(this.a,this.b,rhs.a,rhs.b,rhs.c,rhs.d);
	}
	Sample7 concat(Sample5 rhs) {
		return new Sample7(this.a,this.b,rhs.a,rhs.b,rhs.c,rhs.d,rhs.e);
	}
	
}