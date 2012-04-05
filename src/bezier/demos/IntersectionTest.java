package bezier.demos;

import static bezier.points.Transformation.id;

import java.util.List;

import bezier.image.generated.ColorsAlpha;
import bezier.paths.Path;
import bezier.paths.factory.TextFactory;
import bezier.paths.util.PathParameter;
import bezier.points.Transformation;
import bezier.points.Vec;
import bezier.util.STuple;

public class IntersectionTest extends DemoBase{
	public static void main(String[] args) {
        new IntersectionTest();
    }
		/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

		
		@Override
		public void draw() {
			  Path ts = TextFactory.text2Paths("Atze");
			  Path ts2 = TextFactory.text2Paths(lastLine);

			  ts2 = ts2.transform(id.scale(5).translate(200,500));
			  ts = ts.transform(id.scale(5).rotate(wheel / 100.0 * Math.PI));
			  ts = ts.transform(id.translate(mouse.sub(ts.getBBox().middle())));
			draw(ts,ColorsAlpha.green.lerp(0.5, ColorsAlpha.black));
			draw(ts2,ColorsAlpha.red.lerp(0.5, ColorsAlpha.black));
			ts.intersectionPoints(ts2);
			STuple<List<PathParameter>> intersectionts = ts.intersections(ts2);
			for(PathParameter p : intersectionts.l){
				drawOval(ts.getAt(p), 5, ColorsAlpha.green);
			}
			for(PathParameter p : intersectionts.r){
				drawOval(ts2.getAt(p), 5, ColorsAlpha.red);
			}
		}
}
