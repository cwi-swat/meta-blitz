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
import nogbeter.paths.results.project.BestProjectTup;
import nogbeter.points.twod.Vec;
import static nogbeter.paths.factory.PathFactory.*;
import static nogbeter.transform.AffineTransformation.*;

public class ProjectPaths extends DemoBase{

		public static void main(String[] argv){
			new ProjectPaths();
		}


		private Path r;
		
		public ProjectPaths() {
			r = TextFactory.text2Paths("ws").transform(id.scale(5).translate(400, 400));
//			System.out.println(r);
		}
		
		@Override
		public void draw() {

//			Path r = rectangle();
			Path q = TextFactory.text2Paths("uvc").transform(id.translate(-150,-150).translate(mouse));
			draw(r);
			draw(q);
			BestProjectTup<PathIndex,PathIndex> best = q.project(r);
//			System.out.println(best);
//			System.out.println(r.getAt(best.t));
			drawLine(q.getAt(best.l), r.getAt(best.r));


		}


		
	}
