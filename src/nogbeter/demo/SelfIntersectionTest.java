package nogbeter.demo;

import static nogbeter.paths.factory.PathFactory.createLine;
import static nogbeter.transform.AffineTransformation.id;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import nogbeter.crossing.Crossing;
import nogbeter.crossing.IntersectionsToCrossings;
import nogbeter.demo.awt.DemoBase;
import nogbeter.paths.Path;
import nogbeter.paths.PathIndex;
import nogbeter.paths.compound.NotClosedException;
import nogbeter.paths.factory.PathFactory;
import nogbeter.paths.factory.TextFactory;
import nogbeter.paths.results.intersections.IIntersections;
import nogbeter.paths.results.intersections.Intersection;
import nogbeter.points.angles.AngularInterval;
import nogbeter.points.angles.AngularIntervalFactory;
import nogbeter.points.twod.Vec;
import bezier.image.generated.ColorsAlpha;
import bezier.image.generated.SampleInstances.Sample4;

public class SelfIntersectionTest{
	public static void main(String[] argv){
		System.out.println(selfAntiPar());
		
	}

	public static Path selfAntiPar(){
		Vec a = new Vec(-10,0);
		Vec b = new Vec(20,0);
		try {
			return PathFactory.createClosedPath(createLine(a,b), createLine(b,a));
		} catch (NotClosedException e) {
			throw new Error(e.getMessage());
		}
	}
	
	public static Path simpleSelfIntersection(){
		Vec a = new Vec(-1,-1);
		Vec b = new Vec(1,-1);
		Vec c = new Vec(1,1);
		Vec d = new Vec(-1,1);
		try {
			return PathFactory.createClosedPath(createLine(a,c), createLine(c,b), createLine(b,d),createLine(d,a));
		} catch (NotClosedException e) {
			throw new Error(e.getMessage());
		}
	}
	
	
}
