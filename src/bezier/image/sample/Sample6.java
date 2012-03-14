package bezier.image.sample;
class Sample6{
	final double a,b,c,d,e,f;

	Sample6(double a,double b,double c,double d,double e,double f){
		this.a = a;
  this.b = b;
  this.c = c;
  this.d = d;
  this.e = e;
  this.f = f;
	}	

    Sample6 add(Sample6 rhs){
		return new Sample6(this.a + rhs.a,this.b + rhs.b,this.c + rhs.c,this.d + rhs.d,this.e + rhs.e,this.f + rhs.f);
	}
 
    Sample6 mul(Sample6 rhs){
		return new Sample6(this.a * rhs.a,this.b * rhs.b,this.c * rhs.c,this.d * rhs.d,this.e * rhs.e,this.f * rhs.f);
	}

	Sample6 mul(double d){
		return new Sample6(this.a * d,this.b * d,this.c * d,this.d * d,this.e * d,this.f * d);
	}

	Sample6 div(double d){
		return new Sample6(this.a / d,this.b / d,this.c / d,this.d / d,this.e / d,this.f / d);
	}

	Sample6 lerp(double d,Sample6 rhs){
		double od = 1.0 - d;
		return new Sample6(od * this.a + d * rhs.a,od * this.b + d * rhs.b,od * this.c + d * rhs.c,od * this.d + d * rhs.d,od * this.e + d * rhs.e,od * this.f + d * rhs.f);
	}
	
	Sample7 concat(Sample1 rhs) {
		return new Sample7(this.a,this.b,this.c,this.d,this.e,this.f,rhs.a);
	}
	
}