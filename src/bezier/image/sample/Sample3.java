package bezier.image.sample;
public class Sample3{
	public final double a,b,c;

	public Sample3(double a,double b,double c){
		this.a = a;
  this.b = b;
  this.c = c;
	}	

    public Sample3 add(Sample3 rhs){
		return new Sample3(this.a + rhs.a,this.b + rhs.b,this.c + rhs.c);
	}
 
    public Sample3 mul(Sample3 rhs){
		return new Sample3(this.a * rhs.a,this.b * rhs.b,this.c * rhs.c);
	}

	public Sample3 mul(double d){
		return new Sample3(this.a * d,this.b * d,this.c * d);
	}

	public Sample3 div(double d){
		return new Sample3(this.a / d,this.b / d,this.c / d);
	}

	public Sample3 lerp(double d,Sample3 rhs){
		double od = 1.0 - d;
		return new Sample3(od * this.a + d * rhs.a,od * this.b + d * rhs.b,od * this.c + d * rhs.c);
	}
	
	public Sample4 concat(Sample1 rhs) {
		return new Sample4(this.a,this.b,this.c,rhs.a);
	}
	public Sample5 concat(Sample2 rhs) {
		return new Sample5(this.a,this.b,this.c,rhs.a,rhs.b);
	}
	public Sample6 concat(Sample3 rhs) {
		return new Sample6(this.a,this.b,this.c,rhs.a,rhs.b,rhs.c);
	}
	public Sample7 concat(Sample4 rhs) {
		return new Sample7(this.a,this.b,this.c,rhs.a,rhs.b,rhs.c,rhs.d);
	}
	
}