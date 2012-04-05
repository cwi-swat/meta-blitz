package bezier.demos;

import java.util.Iterator;
import java.util.List;

import bezier.image.generated.ColorsAlpha;
import bezier.paths.Path;
import bezier.paths.factory.TextFactory;
import bezier.paths.simple.Line;
import bezier.paths.util.PathParameter;
import bezier.points.Transformation;
import bezier.util.STuple;

public class SimplifyTest extends DemoBase {

	public static void main(String[] args) {
        new SimplifyTest();
    }
		/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

		
		@Override
		public void draw() {
			Path ts = TextFactory.text2Paths(lastLine);
			ts = ts.transform(Transformation.id.rotate(wheel / 100.0 * Math.PI).scale(5).translate(mouse));//.;
			Iterator<Line> lit = ts.getLineIterator();
			while(lit.hasNext()){
				draw(lit.next(),ColorsAlpha.green);
			}
//			draw(ts);
		
		}
}
