package nogbeter.demo;

import bezier.image.generated.ColorsAlpha;
import bezier.image.generated.SampleInstances.Sample4;
import nogbeter.crossing.GroupedIntersections;
import nogbeter.paths.Path;
import nogbeter.paths.PathIndex;
import nogbeter.paths.factory.PathFactory;
import nogbeter.paths.factory.TextFactory;
import nogbeter.paths.results.intersections.IIntersections;
import nogbeter.paths.results.intersections.Intersection;
import nogbeter.paths.results.intersections.Intersections;
import nogbeter.points.twod.Vec;
import static nogbeter.paths.factory.PathFactory.*;
import static nogbeter.transform.AffineTransformation.*;
public class IntersectionTest extends DemoBase {

	public static void main(String[] argv){
		new IntersectionTest();
	}


	private Path<PathIndex> r;
	
	public IntersectionTest() {
		r = // rectangle().transform(id.scale(200).translate(400,400));
		r = TextFactory.text2Paths("ws").transform(id.scale(5).translate(200, 200));
	}
	
	@Override
	public void draw() {

		Path<PathIndex> q = TextFactory.text2Paths("nm").transform(id.scale(5).translate(mouse));
//		Path<PathIndex> q = rectangle().transform(id.scale(200).translate(mouse.add(mouse)));
		draw(r);
		draw(q);
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
