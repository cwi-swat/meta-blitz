package bezier.demos;

import bezier.paths.Path;
import bezier.paths.compound.Paths;
import bezier.paths.factory.FontFactory;
import bezier.points.Transformation;
import bezier.points.Vec;
import bezier.segment.Circle;

public class NewStuffTest extends DemoBase {
	public static void main(String[] args) {
        new NewStuffTest();
    }
		/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

		
		@Override
		public void draw() {
			Path p = FontFactory.text2Paths("Hallo");
			p = p.transform(Transformation.id.translate(100,100).scale(2));
			draw(p);
			
		}
}
