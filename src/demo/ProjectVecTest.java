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
import paths.points.twod.Vec;
import sun.java2d.loops.DrawLine;
import static paths.paths.factory.PathFactory.*;
import static transform.AffineTransformation.*;

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

	
		
	}
