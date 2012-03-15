package bezier.image;

public interface Sample<A> {
	
	A add(A b);
	A sub(A b);
	A mul(double d);
	A div(double d);
	A clamp();
	A lerp(double d,A b);
	double last();
	
	// clamp<indexes> : clamps indexes
	// concat<other sample> : appends
	// del<indexes> : deletes indexes
}
