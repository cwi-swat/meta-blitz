package demo;

import demo.awt.DemoBase;
import paths.paths.factory.PathFactory;
import paths.paths.factory.TextFactory;
import paths.paths.paths.Path;
import paths.paths.paths.PathIndex;
import paths.paths.results.intersections.IIntersections;
import paths.paths.results.intersections.Intersection;
import paths.paths.results.intersections.Intersections;
import paths.paths.results.project.BestProject;
import paths.paths.results.project.BestProjectTup;
import paths.points.twod.Vec;
import sun.java2d.loops.DrawLine;
import static paths.paths.factory.PathFactory.*;
import static paths.transform.AffineTransformation.*;

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
