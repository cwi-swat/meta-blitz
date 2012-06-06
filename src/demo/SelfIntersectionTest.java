package demo;

import static paths.paths.factory.PathFactory.createLine;
import static paths.transform.AffineTransformation.id;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import demo.awt.DemoBase;

import paths.crossing.Crossing;
import paths.crossing.IntersectionsToCrossings;
import paths.paths.factory.PathFactory;
import paths.paths.factory.TextFactory;
import paths.paths.paths.Path;
import paths.paths.paths.PathIndex;
import paths.paths.paths.compound.NotClosedException;
import paths.paths.results.intersections.IIntersections;
import paths.paths.results.intersections.Intersection;
import paths.points.angles.AngularInterval;
import paths.points.angles.AngularIntervalFactory;
import paths.points.twod.Vec;

import textures.generated.ColorsAlpha;
import textures.generated.SampleInstances.Sample4;


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
