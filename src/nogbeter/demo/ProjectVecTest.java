package nogbeter.demo;

import sun.java2d.loops.DrawLine;
import nogbeter.demo.awt.DemoBase;
import nogbeter.paths.Path;
import nogbeter.paths.PathIndex;
import nogbeter.paths.factory.PathFactory;
import nogbeter.paths.factory.TextFactory;
import nogbeter.paths.results.intersections.IIntersections;
import nogbeter.paths.results.intersections.Intersection;
import nogbeter.paths.results.intersections.Intersections;
import nogbeter.paths.results.project.BestProject;
import nogbeter.points.twod.Vec;
import static nogbeter.paths.factory.PathFactory.*;
import static nogbeter.transform.AffineTransformation.*;

public class ProjectVecTest extends DemoBase{

		public static void main(String[] argv){
			new ProjectVecTest();
		}


		private Path r;
		
		public ProjectVecTest() {
			r = TextFactory.text2Paths("s").transform(id.scale(5).translate(400, 400)).normaliseToLength();
//			System.out.println(r);
		}
		
		@Override
		public void draw() {

//			Path r = rectangle();
//			Path q = rectangle().transform(id.translate(-150,-150).translate(mouse));
			draw(r);
			BestProject<PathIndex> best = r.project(mouse);
			System.out.println(best);
//			System.out.println(r.getAt(best.t));
			drawLine(r.getAt(best.t), mouse);


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
