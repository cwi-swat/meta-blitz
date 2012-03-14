package bezier.image.sample;
class Sample4{
	final double a,b,c,d;

	Sample4(double a,double b,double c,double d){
		this.a = a;
  this.b = b;
  this.c = c;
  this.d = d;
	}	

    Sample4 add(Sample4 rhs){
		return new Sample4(this.a + rhs.a,this.b + rhs.b,this.c + rhs.c,this.d + rhs.d);
	}
 
    Sample4 mul(Sample4 rhs){
		return new Sample4(this.a * rhs.a,this.b * rhs.b,this.c * rhs.c,this.d * rhs.d);
	}

	Sample4 mul(double d){
		return new Sample4(this.a * d,this.b * d,this.c * d,this.d * d);
	}

	Sample4 div(double d){
		return new Sample4(this.a / d,this.b / d,this.c / d,this.d / d);
	}

	Sample4 lerp(double d,Sample4 rhs){
		double od = 1.0 - d;
		return new Sample4(od * this.a + d * rhs.a,od * this.b + d * rhs.b,od * this.c + d * rhs.c,od * this.d + d * rhs.d);
	}
	
	Sample5 concat(Sample1 rhs) {
		return new Sample5(this.a,this.b,this.c,this.d,rhs.a);
	}
	Sample6 concat(Sample2 rhs) {
		return new Sample6(this.a,this.b,this.c,this.d,rhs.a,rhs.b);
	}
	Sample7 concat(Sample3 rhs) {
		return new Sample7(this.a,this.b,this.c,this.d,rhs.a,rhs.b,rhs.c);
	}
	
}