package bezier.segment.curve;

public class CurvePart {
	
	final Curve part;
	public final double tstart, tend;
	
	public CurvePart(Curve part, double tstart, double tend) {
		this.part = part;
		this.tstart = tstart;
		this.tend = tend;
	}

}
