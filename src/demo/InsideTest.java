package demo;

import demo.awt.DemoBase;
import paths.paths.factory.PathFactory;
import paths.paths.factory.TextFactory;
import paths.paths.paths.Path;
import paths.paths.paths.PathIndex;
import paths.paths.paths.compound.ClosedPath;
import paths.paths.paths.compound.NotClosedException;
import paths.paths.results.intersections.IIntersections;
import paths.paths.results.intersections.Intersection;
import paths.paths.results.intersections.Intersections;
import paths.paths.results.project.BestProject;
import paths.paths.results.project.BestProjectTup;
import paths.points.twod.Vec;
import sun.java2d.loops.DrawLine;
import textures.old.generated.ColorsAlpha;
import static paths.paths.factory.PathFactory.*;
import static transform.AffineTransformation.*;

public class InsideTest extends DemoBase{

		public static void main(String[] argv){
			new InsideTest();
		}


		private Path r;
		
		public InsideTest() {
			r = TextFactory.text2Paths("wso").transform(id.scale(5).translate(400, 400));
		}
		
		@Override
		public void draw() {

			draw(r,r.isInside(mouse) ? ColorsAlpha.green : ColorsAlpha.red);
			BestProject<PathIndex> best = r.project(mouse);
			drawLine(r.getAt(best.t),mouse);

			System.out.println("");
		}

		
//
//		public ClosedPath rectangle() throws NotClosedException{
//			Vec a = new Vec(100,100);
//			Vec b = new Vec(200,100);
//			Vec c = new Vec(200,200);
//			Vec d = new Vec(100,200);
//			Vec e = new Vec(150,50);
//			return createClosedPath(createLine(a,b), createLine(b,c), createLine(c,d),createLine(d,a));
//		}
		
	}
