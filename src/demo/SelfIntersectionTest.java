package demo;

import static paths.paths.factory.PathFactory.createLine;
import paths.paths.factory.PathFactory;
import paths.paths.paths.Path;
import paths.points.twod.Vec;


public class SelfIntersectionTest{
	public static void main(String[] argv){
		System.out.println(selfAntiPar());
		
	}

	public static Path selfAntiPar(){
		Vec a = new Vec(-10,0);
		Vec b = new Vec(20,0);
		
			return PathFactory.createClosedPath(createLine(a,b), createLine(b,a));
	}
	
	public static Path simpleSelfIntersection(){
		Vec a = new Vec(-1,-1);
		Vec b = new Vec(1,-1);
		Vec c = new Vec(1,1);
		Vec d = new Vec(-1,1);
		return PathFactory.createClosedPath(createLine(a,c), createLine(c,b), createLine(b,d),createLine(d,a));
	
	}
	
	
}
