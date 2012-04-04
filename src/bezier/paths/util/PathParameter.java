package bezier.paths.util;

import bezier.paths.IConnectedPath;
import bezier.paths.Path;
import bezier.paths.compound.Append;



public final class PathParameter {

	public final Path connected;
	public final double t;
	
	
	public PathParameter(Path connected, double t) {
		this.connected = connected;
		this.t = t;
	}


	public PathParameter(double d) {
		this(null,d);
	}
	
	public String toString(){
		return String.format("PP(%s %f)", connected,t);
	}
}
