package bezier.demos;

import bezier.paths.Path;
import bezier.paths.factory.CircleFactory;
import bezier.points.Transformation;
import bezier.points.Vec;

public class CircleTest extends DemoBase {

	public static void main(String[] args) {
        new CircleTest();
    }
		/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

		
		@Override
		public void draw() {
			double angularLength = wheel / 500;
			Path circle = CircleFactory.makeCircularArc(new Vec(500,500), mouse, angularLength);
			drawOval(new Vec(500,500),5);
			drawOval(CircleFactory.getCircularCenter(new Vec(500,500), mouse, angularLength),5);
			draw(circle);
			
		}


	
}
