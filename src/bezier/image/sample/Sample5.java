package bezier.image.sample;
class Sample5{
	final double a,b,c,d,e;

	Sample5(double a,double b,double c,double d,double e){
		this.a = a;
  this.b = b;
  this.c = c;
  this.d = d;
  this.e = e;
	}	

    Sample5 add(Sample5 rhs){
		return new Sample5(this.a + rhs.a,this.b + rhs.b,this.c + rhs.c,this.d + rhs.d,this.e + rhs.e);
	}
 
    Sample5 mul(Sample5 rhs){
		return new Sample5(this.a * rhs.a,this.b * rhs.b,this.c * rhs.c,this.d * rhs.d,this.e * rhs.e);
	}

	Sample5 mul(double d){
		return new Sample5(this.a * d,this.b * d,this.c * d,this.d * d,this.e * d);
	}

	Sample5 div(double d){
		return new Sample5(this.a / d,this.b / d,this.c / d,this.d / d,this.e / d);
	}

	Sample5 lerp(double d,Sample5 rhs){
		double od = 1.0 - d;
		return new Sample5(od * this.a + d * rhs.a,od * this.b + d * rhs.b,od * this.c + d * rhs.c,od * this.d + d * rhs.d,od * this.e + d * rhs.e);
	}
	
	Sample6 concat(Sample1 rhs) {
		return new Sample6(this.a,this.b,this.c,this.d,this.e,rhs.a);
	}
	Sample7 concat(Sample2 rhs) {
		return new Sample7(this.a,this.b,this.c,this.d,this.e,rhs.a,rhs.b);
	}
	
}