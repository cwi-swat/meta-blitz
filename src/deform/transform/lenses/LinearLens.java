package deform.transform.lenses;

import deform.Vec;
import deform.transform.lenses.Norms.Norm;

public class LinearLens extends Lens{
	
	public LinearLens(Norm norm, Vec center, double mag, double innerRadius,
			double outerRadius) {
		super(norm, center, mag, innerRadius, outerRadius);
	}
	

	@Override
	double toNorm(double d) {
		return  ((d*mag* outerRadius) - d * innerRadius)/ (outerRadius + d*mag - innerRadius - d);
	}

	@Override
	double profile(double d) {
		return d;
	}

	

}
