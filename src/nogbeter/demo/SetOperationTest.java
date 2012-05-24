package nogbeter.demo;

import static nogbeter.transform.AffineTransformation.id;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import nogbeter.crossing.Crossing;
import nogbeter.crossing.GroupedIntersections;
import nogbeter.demo.awt.DemoBase;
import nogbeter.paths.Path;
import nogbeter.paths.PathIndex;
import nogbeter.paths.factory.TextFactory;
import nogbeter.paths.results.intersections.IIntersections;
import nogbeter.paths.results.intersections.Intersection;
import nogbeter.paths.setoperations.SetOperations;
import nogbeter.points.angles.AngularInterval;
import nogbeter.points.angles.AngularIntervalFactory;
import nogbeter.points.twod.Vec;
import bezier.image.generated.ColorsAlpha;
import bezier.image.generated.SampleInstances.Sample4;

public class SetOperationTest extends DemoBase{
	public static void main(String[] argv){
//		AngularInterval in = AngularIntervalFactory.create180DegreesInterval(new Vec(0,1));
//		System.out.println(in.isInside(new Vec(1,0)));
//		Path<PathIndex>  r = TextFactory.text2Paths("s").transform(id.scale(5).translate(200, 200));
//		Path<PathIndex>  z = TextFactory.text2Paths("s").transform(id.scale(5));
//		for(int x = 000; x < 500 ; x++){
//			for(int y = 0 ; y < 500 ; y++){
//				Path<PathIndex> q = z.transform(id.translate(x,y));
//				IIntersections<PathIndex,PathIndex> ints = r.intersection(q);
//				List<List<Crossing<PathIndex, PathIndex>>> cross = new GroupedIntersections(ints, r,q).getCrossings();
//				int i = 0;
//				for(List<Crossing<PathIndex, PathIndex>> cc : cross){
//					for(Crossing<PathIndex,PathIndex> c : cc){
//						i++;
//					}
//				}
//				if(i % 2 == 1){
//					System.out.printf("%d %d crossings: %s\n", x,y,i);
//				}
//				try{
//					Path p = new SetOperations<PathIndex, PathIndex>(r, q, cross).union();
//				} catch(Error p){
//					System.out.println(p.getMessage());
//				}
//			}
//		}
//		System.out.println("Done!");
		new SetOperationTest();
	}

	Vec location = new Vec(0,0);
	private Path<PathIndex> r,z;
	
	public SetOperationTest() {
//		r = rectangle().transform(id.scale(200).translate(400,400));
		r = TextFactory.text2Paths("swe").transform(id.scale(5).translate(200, 200));
		z = TextFactory.text2Paths("snmk").transform(id.scale(5));
//		z = rectangle().transform(id.scale(200));
	}
	
	public void handleKeyStroke(char key){
		switch(key){
		case 'n' : location = new Vec(0,0); break;
		case 'w' : location = location.add(new Vec(0,-1)); break;
		case 's' : location = location.add(new Vec(0,1)); break;
		case 'a' : location = location.add(new Vec(-1,0)); break;
		case 'd' : location = location.add(new Vec(1,0)); break;
		}
	}
	
	@Override
	public void draw() {

		Path<PathIndex> q =
		z.transform(
				id.translate(mouse));

		IIntersections<PathIndex,PathIndex> ints = r.intersection(q);
		List<List<Crossing<PathIndex, PathIndex>>> cross = new GroupedIntersections(ints, r,q).getCrossings();
//		int i = 0;
//		for(List<Crossing<PathIndex, PathIndex>> cc : cross){
//			for(Crossing<PathIndex,PathIndex> c : cc){
//				fillOval(c.loc, 10, c.leftAfterInside ? ColorsAlpha.green : ColorsAlpha.red);
//				i++;
//			}
//		}
//		Sample4 c;
//		if(i % 2 != 0){
//			c = ColorsAlpha.red;
//			System.err.println(i);
//		} else {
//			c = ColorsAlpha.black;
//		}
//		draw(r,c);
//		draw(q,c);
		Path p = new SetOperations<PathIndex, PathIndex>(r, q, cross).substract();
		draw(p);
		
//		for(Crossing<PathIndex, PathIndex> c : cross){
//		
//			fillOval(c.loc, 10, c.leftAfterInside ? ColorsAlpha.green : ColorsAlpha.red);
//		}
//		System.out.println();

	}
}
