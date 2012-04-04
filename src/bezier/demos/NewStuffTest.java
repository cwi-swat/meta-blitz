package bezier.demos;

import bezier.paths.Path;
import bezier.paths.compound.Paths;
import bezier.paths.factory.CircleFactory;
import bezier.paths.factory.TextFactory;
import bezier.points.Transformation;
import bezier.points.Vec;

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
			Path p = TextFactory.text2Paths("Hallo");
			p = p.transform(Transformation.id.translate(100,100).scale(2));
			draw(p);
			
		}
}
