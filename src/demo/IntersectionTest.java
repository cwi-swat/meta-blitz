package demo;

import demo.awt.DemoBase;
import paths.crossing.IntersectionsToCrossings;
import paths.paths.factory.PathFactory;
import paths.paths.factory.TextFactory;
import paths.paths.paths.Path;
import paths.paths.paths.PathIndex;
import paths.paths.results.intersections.IIntersections;
import paths.paths.results.intersections.Intersection;
import paths.paths.results.intersections.Intersections;
import paths.points.twod.Vec;
import textures.old.generated.ColorsAlpha;
import textures.old.generated.SampleInstances.Sample4;
import static paths.paths.factory.PathFactory.*;
import static transform.AffineTransformation.*;
public class IntersectionTest extends DemoBase {

	public static void main(String[] argv){
		new IntersectionTest().run();
	}



	private Path<PathIndex> r;
	
	public IntersectionTest() {
		r = // rectangle().transform(id.scale(200).translate(400,400));
		r = TextFactory.text2Paths("ws").transform(id.scale(10).translate(200, 400));
	}
	
	@Override
	public void draw() {
		mouse = new Vec(0,500);
		Path<PathIndex> q = TextFactory.text2Paths("nm").transform(id.scale(10).translate(mouse));
//		Path<PathIndex> q = rectangle().transform(id.scale(200).translate(mouse.add(mouse)));
		long first = System.currentTimeMillis();
		fill(r);
		fill(q);
		System.out.println(System.currentTimeMillis() - first);
		IIntersections<PathIndex,PathIndex> ints = r.intersection(q);
		for(Intersection<PathIndex, PathIndex> i : ints){
//			fillOval(i.loc, 5,ColorsAlpha.green);
			drawLine(i.locl, i.locl.add(i.tanl.normalize().mul(75)),ColorsAlpha.red);
//			drawLine(i.loc, i.loc.add(i.tanr.normalize().mul(75)),ColorsAlpha.green);
		}
//		
//		for(Crossing<PathIndex, PathIndex> c : cr){
//			Sample4 color;
//			if(c.type == CrossingType.Enter){
//				color = ColorsAlpha.green;
//			} else if(c.type == CrossingType.Exit){
//				color = ColorsAlpha.red;
//			} else {
//				color = ColorsAlpha.beige;
//			}
//			fillOval(c.loc, 5, color);
//		}

	}


}
