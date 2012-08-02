package deform.transform.lenses;

import paths.Constants;
import deform.BBox;
import deform.Transform;
import deform.Vec;
import deform.transform.lenses.Norms.Norm;
import deform.transform.lenses.Profiles.Profile;

public class NumericToLens extends Lens{

	private static final double MAX_ERROR_SOL = Constants.MAX_ERROR_TRANSFORM/3.0;
	final Profile p;
	final double borderTo;
	final double innerdivMag;
	
	public NumericToLens(Norm norm, Profile p, Vec center, double mag, double innerRadius,
			double outerRadius) {
		super(norm, center, mag, innerRadius, outerRadius);
		this.p = p;
		this.borderTo = outerRadius - innerRadius/mag;
		this.innerdivMag = innerRadius/mag;
	}

	@Override
	double toNorm(double d) {
		double guess = ((d - innerdivMag) /borderTo) * border + innerRadius;
		
		double sol = fromNorm(guess) -d;
		int i = 0;
		while(Math.abs(sol) >= MAX_ERROR_SOL){
			double deriv = fromNormDiff(guess);
			double newguess = guess - sol / deriv;
			guess = newguess;
			sol = fromNorm(guess) -d;
			i++;
		}
		return guess;
	}
	
	double fromNormDiff(double d){
		double frac = (d-innerRadius)/border;
		double prof = p.prof(frac);
		double lu = 1 / ((mag-1) * (1 - prof) + 1);
		double lu2 = lu * lu;
		double q = d * (mag-1) * p.profDiff(frac)/border;
		return lu + lu2*q;
		
	}

	@Override
	double profile(double d) {
		return p.prof(d); 
	}

	public static void main(String[] argv){
		Lens l = new NumericToLens(Norms.circle, Profiles.sine, new Vec(0,0), 2, 10, 100);
		for(double d = 10 ; d <= 100 ; d+=10){
//		double d = 10;
			System.out.printf("%f -> %f\n", d, l.to(new Vec(d,0)).norm());
		}
	}
	
	




}
