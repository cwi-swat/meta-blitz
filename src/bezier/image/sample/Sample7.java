package bezier.image.sample;

public class Sample7{
	public final double a,b,c,d,e,f,g;

	public Sample7(double a,double b,double c,double d,double e,double f,double g){
		this.a = a;
  this.b = b;
  this.c = c;
  this.d = d;
  this.e = e;
  this.f = f;
  this.g = g;
	}	

    public Sample7 add(Sample7 rhs){
		return new Sample7(this.a + rhs.a,this.b + rhs.b,this.c + rhs.c,this.d + rhs.d,this.e + rhs.e,this.f + rhs.f,this.g + rhs.g);
	}
 
    public Sample7 mul(Sample7 rhs){
		return new Sample7(this.a * rhs.a,this.b * rhs.b,this.c * rhs.c,this.d * rhs.d,this.e * rhs.e,this.f * rhs.f,this.g * rhs.g);
	}

	public Sample7 mul(double d){
		return new Sample7(this.a * d,this.b * d,this.c * d,this.d * d,this.e * d,this.f * d,this.g * d);
	}

	public Sample7 div(double d){
		return new Sample7(this.a / d,this.b / d,this.c / d,this.d / d,this.e / d,this.f / d,this.g / d);
	}

	public Sample7 lerp(double d,Sample7 rhs){
		double od = 1.0 - d;
		return new Sample7(od * this.a + d * rhs.a,od * this.b + d * rhs.b,od * this.c + d * rhs.c,od * this.d + d * rhs.d,od * this.e + d * rhs.e,od * this.f + d * rhs.f,od * this.g + d * rhs.g);
	}
	
	
}