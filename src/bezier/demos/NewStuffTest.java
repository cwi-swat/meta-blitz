package bezier.demos;

import nogbeter.points.twod.Vec;
import nogbeter.transform.AffineTransformation;
import bezier.paths.Path;
import bezier.paths.compound.Paths;
import bezier.paths.factory.CircleFactory;
import bezier.paths.factory.TextFactory;

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
			p = p.transform(AffineTransformation.id.translate(100,100).scale(2));
			draw(p);
			
		}
}
