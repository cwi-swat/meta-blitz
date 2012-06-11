package demo;

import static transform.AffineTransformation.id;
import paths.paths.factory.TextFactory;
import paths.paths.paths.Path;
import paths.paths.results.project.BestProject;

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
			BestProject best = r.project(mouse);
			System.out.println(best);
//			System.out.println(r.getAt(best.t));
			drawLine(r.getAt(best.t), mouse);


		}

	
		
	}
