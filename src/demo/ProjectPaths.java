package demo;

import static transform.AffineTransformation.id;
import paths.paths.factory.TextFactory;
import paths.paths.paths.Path;
import paths.paths.results.project.BestProjectTup;

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
			Path q = TextFactory.text2Paths("uvc").transform(id.translate(mouse));
			draw(r);
			draw(q);
			BestProjectTup best = q.project(r);
//			System.out.println(best);
//			System.out.println(r.getAt(best.t));
			drawLine(q.getAt(best.l), r.getAt(best.r));


		}


		
	}
