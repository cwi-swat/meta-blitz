package deform.transform.lenses;

import deform.Vec;

public class Norms {
	
	public interface Norm {
		double norm(Vec v);
	}
	
	public static final Norm diamond = new Diamond(); 
	public static final Norm circle = new Circle(); 
	public static final Norm rectcircle = new RectangularCircle(); 
	public static final Norm circlerect = new CircularRectangle(); 
	public static final Norm rect = new Rectangle();	
	
	private static class Diamond implements Norm{ // L1
		@Override
		public double norm(Vec v) {
			return Math.abs(v.x) + Math.abs(v.y);
		}
		
	}
	
	private static class Circle implements Norm{ // L2

		@Override
		public double norm(Vec v) {
			return v.norm();
		}
	}
	
	private static class RectangularCircle  implements Norm{ // L3
		@Override
		public double norm(Vec v) {
			return Math.pow(v.x * v.x * Math.abs(v.x) + v.y * v.y * Math.abs(v.y) , 1.0/3.0);
		}
	}
	
	private static class CircularRectangle  implements Norm{ // L4
		@Override
		public double norm(Vec v) {
			double x2 = v.x * v.x;
			double y2 = v.y * v.y;
			return Math.sqrt(Math.sqrt(x2 * x2 + y2 * y2));
		}
	}
	
	private static class Rectangle  implements Norm{ // LInf
		@Override
		public double norm(Vec v) {
			return Math.max(Math.abs(v.x),Math.abs(v.y));
		}
	}
	
	
}
