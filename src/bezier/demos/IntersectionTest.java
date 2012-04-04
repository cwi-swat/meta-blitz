package bezier.demos;

import java.util.List;

import bezier.paths.Path;
import bezier.paths.factory.TextFactory;
import bezier.paths.util.PathParameter;
import bezier.points.Transformation;

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
			Path ts = TextFactory.text2Paths(lastLine);
			ts = ts.transform(Transformation.id.rotate(wheel / 100.0 * Math.PI).scale(5).translate(mouse));//.;
			Path ts2 = TextFactory.text2Paths("Atze");
			ts2 = ts2.transform(Transformation.id.scale(5).translate(100,400));
			draw(ts);
			draw(ts2);
			List<PathParameter> intersectionts = ts.intersections(ts2).l;
			for(PathParameter p : intersectionts){
				drawOval(ts.getAt(p), 5);
			}
		}
}
