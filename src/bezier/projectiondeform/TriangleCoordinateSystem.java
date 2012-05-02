package bezier.projectiondeform;

import nogbeter.points.twod.Vec;
import bezier.paths.simple.Line;

public final class TriangleCoordinateSystem implements CoordinateSystem{
	
	final double startX, lengthX;
	final Vec start,end;
	final Vec dirA, dirB; // must be normalized
	
	public TriangleCoordinateSystem(double startX, double lengthX, Vec start,
			Vec end, Vec dirA, Vec dirB) {
		this.startX = startX;
		this.lengthX = lengthX;
		this.start = start;
		this.end = end;
		this.dirA = dirA;
		this.dirB = dirB;
	}
	
	public static TriangleCoordinateSystem create(double startX, double lengthX, Line line, Vec prevNormal, Vec normal, Vec nextNormal){
		Vec dirA = prevNormal.add(normal).div(1 + prevNormal.dot(normal));
		Vec dirB = nextNormal.add(normal).div(1 + nextNormal.dot(normal));
		return new TriangleCoordinateSystem(startX, lengthX, line.start, line.end, dirA, dirB);
	}

	public Vec getAt(Vec loc){
		double relX = (loc.x - startX) / lengthX;
		Vec l = start.add(dirA.mul(loc.y));
		Vec r = end.add(dirB.mul(loc.y));
		return l.interpolate(relX, r);
	}
	

}
