package nogbeter.demo;

import nogbeter.paths.Path;
import nogbeter.paths.factory.PathFactory;
import nogbeter.paths.factory.TextFactory;
import nogbeter.paths.results.intersections.IIntersections;
import nogbeter.paths.results.intersections.Intersection;
import nogbeter.paths.results.intersections.Intersections;
import nogbeter.points.twod.Vec;
import static nogbeter.paths.factory.PathFactory.*;
import static nogbeter.transform.AffineTransformation.*;
public class DrawSomething extends DemoBase {

	public static void main(String[] argv){
		new DrawSomething();
	}


	private Path r;
	
	public DrawSomething() {
		r = TextFactory.text2Paths("ws").transform(id.scale(5).translate(200, 200));
		System.out.println(r);
	}
	
	@Override
	public void draw() {

		Path q = TextFactory.text2Paths("nm").transform(id.scale(5).translate(mouse));
//		Path r = rectangle();
//		Path q = rectangle().transform(id.translate(-150,-150).translate(mouse));
		draw(r);
		draw(q);
		IIntersections ints = r.intersection(q);
		for(Object i : ints){
			Intersection in = (Intersection)i;
//			System.out.println(in);
			drawOval(r.getAt(in.left),5);
		}

	}

	
	public Path rectangle(){
		Vec a = new Vec(100,100);
		Vec b = new Vec(200,100);
		Vec c = new Vec(200,200);
		Vec d = new Vec(100,200);
		Vec e = new Vec(150,50);
		return createAppends(createLine(a,b), createLine(b,c), createLine(c,d),createLine(d,a));
	}
	
}
