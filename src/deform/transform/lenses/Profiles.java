package deform.transform.lenses;

public class Profiles {
	
	public interface Profile{
		double prof(double d);
		double profDiff(double d);
	}
	
	public static Profile linear = new Linear();
	public static Profile sine = new Sine();
	public static Profile quadratic = new Quadratic();
	public static Profile gauss = new Gaussian();
	
	private static class Gaussian implements Profile{

		private double end = Math.exp(-4);
		private double interval = 1 - end;
		
		@Override
		public double prof(double d) {
			
			return 1 - (Math.exp(-(d*d)*4) - end) /interval;
		}

		@Override
		public double profDiff(double d) {
			return 8 * d / interval * Math.exp(-4 * d *d) ;
		}
		
	}
	
	private static class Linear implements Profile{

		@Override
		public double prof(double d) {
			return d;
		}

		@Override
		public double profDiff(double d) {
			return 1;
		}
		
	}
	
	private static class Sine implements Profile{
		private static double halfPi = 0.5 * Math.PI;
		@Override
		public double prof(double d) {
			return Math.sin(d * halfPi);
		}

		@Override
		public double profDiff(double d) {
			return halfPi * Math.cos(d * halfPi);
		}
		
		
		
	}
	
	private static class Quadratic implements Profile{
		public double prof(double d) {
			return d * d;
		}

		@Override
		public double profDiff(double d) {
			return d*2.0;
		}
		
		
		
	}

}
