package demo;

import static paths.transform.AffineTransformation.id;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import demo.awt.DemoBase;

import paths.crossing.Crossing;
import paths.crossing.IntersectionsToCrossings;
import paths.paths.factory.TextFactory;
import paths.paths.paths.Path;
import paths.paths.paths.PathIndex;
import paths.paths.results.intersections.IIntersections;
import paths.paths.results.intersections.Intersection;
import paths.points.angles.AngularInterval;
import paths.points.angles.AngularIntervalFactory;
import paths.points.twod.Vec;

import textures.generated.ColorsAlpha;
import textures.generated.SampleInstances.Sample4;


public class CrossTest extends DemoBase{
	public static void main(String[] argv){
		AngularInterval in = AngularIntervalFactory.create180DegreesInterval(new Vec(0,1));
		System.out.println(in.isInside(new Vec(1,0)));
		new CrossTest();
	}

	Vec location = new Vec(0,0);
	private Path<PathIndex> r,q;
	
	public CrossTest() {
//		r = (Path)rectangle().transform(id.scale(200).translate(400,400));
//		System.out.println(r);
////		r = TextFactory.text2Paths("ws").transform(id.scale(5).translate(200, 200));
		q = TextFactory.text2Paths("t").transform(id.scale(5));
		r = TextFactory.text2Paths("a").transform(id.scale(5).translate(200, 200));
//		z = TextFactory.text2Paths("w").transform(id.scale(5));
//		q = rectangle().transform(id.scale(200));
		System.out.println(q);
	}
	
	public void handleKeyStroke(char key){
		switch(key){
		case 'n' : location = new Vec(0,0); break;
		case 'w' : location = location.add(new Vec(0,-1)); break;
		case 's' : location = location.add(new Vec(0,1)); break;
		case 'a' : location = location.add(new Vec(-1,0)); break;
		case 'd' : location = location.add(new Vec(1,0)); break;
		}
	}
	
	@Override
	public void draw() {
//
		Path<PathIndex> q = // rectangle().transform(id.scale(200).translate(mouse.add(location)));
		this.q.transform(id.translate(mouse.add(location)));
		draw(r);
		draw(q);
		IIntersections<PathIndex,PathIndex> ints = r.intersection(q);

//		System.out.printf("%d\n",cross.size());
			for(Intersection<PathIndex, PathIndex> c : ints){
				fillOval(c.locl.interpolate(0.5, c.locr), 3 + 1000 * c.locl.distance(c.locr));
//				drawLine()
//				fillOval(c.loc, 10, c.leftAfterInside ? ColorsAlpha.green : ColorsAlpha.red);
			}

	}
}