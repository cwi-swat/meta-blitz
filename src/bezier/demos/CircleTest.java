package bezier.demos;

import bezier.composite.Path;
import bezier.points.Transformation;
import bezier.points.Vec;
import bezier.segment.Circle;

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
//			System.out.println(angularLength);
			
			Path circle = Circle.makeCircularArc(new Vec(500,500), mouse, angularLength);
//			System.out.println(circle);
			drawOval(new Vec(500,500),5);
			if(!circle.curves.isEmpty()){
				drawOval(Circle.getCircularCenter(new Vec(500,500), mouse, angularLength),5);
//				circle = circle.transform(Transformation.id.scale(200).translate(300, 300));
				draw(circle);
			}
			
		}


	
}
