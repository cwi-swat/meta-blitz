package nogbeter.demo;

import static nogbeter.transform.AffineTransformation.id;
import nogbeter.crossing.Crossing;
import nogbeter.crossing.CrossingType;
import nogbeter.crossing.Crossings;
import nogbeter.crossing.GroupedIntersections;
import nogbeter.paths.Path;
import nogbeter.paths.PathIndex;
import nogbeter.paths.factory.TextFactory;
import nogbeter.paths.results.intersections.IIntersections;
import nogbeter.paths.results.intersections.Intersection;
import nogbeter.points.twod.Vec;
import bezier.image.generated.ColorsAlpha;
import bezier.image.generated.SampleInstances.Sample4;

public class CrossTest extends DemoBase{
	public static void main(String[] argv){
		new CrossTest();
	}

	Vec location = new Vec(0,0);
	private Path<PathIndex> r;
	
	public CrossTest() {
		r = rectangle().transform(id.scale(200).translate(400,400));
//		r = TextFactory.text2Paths("ws").transform(id.scale(5).translate(200, 200));
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

		Path<PathIndex> q = rectangle().transform(id.scale(200).translate(mouse.add(location)));
		TextFactory.text2Paths("nm").transform(id.scale(5).translate(mouse));
		draw(r);
		draw(q);
		IIntersections<PathIndex,PathIndex> ints = r.intersection(q);
		Crossings<PathIndex, PathIndex> cross = new GroupedIntersections(ints, r,q).toCrossings();
		for(Crossing<PathIndex, PathIndex> c : cross){
			Sample4 color;
			if(c.type == CrossingType.Enter){
				color = ColorsAlpha.green;
			} else if(c.type == CrossingType.Exit){
				color = ColorsAlpha.red;
			} else {
				color = ColorsAlpha.beige;
			}
			fillOval(c.loc, 10, color);
		}

	}
}
