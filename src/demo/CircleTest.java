package demo;

import static transform.AffineTransformation.id;
import demo.awt.DemoBase;
import paths.paths.factory.CircleFactory;
import paths.paths.factory.TextFactory;
import paths.paths.paths.Path;
import paths.paths.paths.PathIndex;
import paths.paths.results.intersections.IIntersections;
import paths.paths.results.intersections.Intersection;
import paths.points.angles.AngularInterval;
import paths.points.angles.AngularIntervalFactory;
import paths.points.twod.Vec;

public class CircleTest extends DemoBase{
	public static void main(String[] argv){
		new CircleTest();
	}

	@Override
	public void draw() {
//
		Path c = CircleFactory.makeCircle(mouse, wheel * 10 + 100);
		draw(c);
	}
}
