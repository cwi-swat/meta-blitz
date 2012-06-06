package paths.demo;

import paths.paths.factory.PathFactory;
import paths.paths.paths.compound.ClosedPath;
import paths.points.twod.Vec;
import static paths.paths.factory.PathFactory.*;
public class ClockwiseTest {
	
	public static void main(String[] argv){
		Vec a = new Vec(0,0);
		Vec b = new Vec(10,0);
		Vec c = new Vec(10, 10);
		Vec d = new Vec(0,10);
		ClosedPath p = createClosedPath(createLine(a, b),createLine(b, c),createLine(c,d),createLine(d,a));
		System.out.println(p.isDefindedClockwise());
		ClosedPath q = createClosedPath(createLine(a, d),createLine(d, c),createLine(c,b),createLine(b,a));
		System.out.println(q.isDefindedClockwise());
	}

}
