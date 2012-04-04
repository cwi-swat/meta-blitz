package bezier.paths;

public class Constants {
	
//	public static final double MAX_ERROR_LENGTH = 0.25;
	public static final double MAX_ERROR = 0.2;
//	public static final double HALF_MAX_ERROR = 0.5 * MAX_ERROR;
//	public static final double MAX_ERROR_2 = 2 * MAX_ERROR;
//	public static final double MAX_ERROR_SIDE = MAX_ERROR / Math.sqrt(2.0);
//	public static final double MAX_ERROR_SIDE_HALF = 0.5 * MAX_ERROR_SIDE;
//	public static final double MAX_ERROR_SIDE_HALF_POW2 = MAX_ERROR_SIDE_HALF * MAX_ERROR_SIDE_HALF;
//	public static final double MAX_ERROR_SIDE_HALF_POW2_HALF = MAX_ERROR_SIDE_HALF_POW2/2.0;
//	public static final double MAX_ERROR_SQRT = Math.sqrt(MAX_ERROR /2.0);
	public static final double MAX_ERROR_POW2 = MAX_ERROR * MAX_ERROR;
	public static final double HALF_MAX_ERROR_POW2 = MAX_ERROR_POW2/16.0;
//	public static final double MAX_WIDTH = 5 * Math.sqrt(MAX_ERROR / 2.0);
//	public static final double MAX_ERROR_AREA_POW2 = MAX_ERROR_POW2 * MAX_ERROR_POW2;
//	public static final double MAX_ERROR_HEIGHT = MAX_ERROR / (Math.sqrt(4+ 2*Math.sqrt(2))) / 4;
//	public static final double MAX_ERROR_HEIGHT_POW2 = MAX_ERROR_HEIGHT * MAX_ERROR_HEIGHT;
	public static final double MAX_ERROR_X = 0.25 * MAX_ERROR;
	public static final double MAX_ERROR_X_POW_2 = MAX_ERROR_X*MAX_ERROR_X;
	
	public static final double T_MAX_DIFF_CUBIC_QUADRATIC = (Math.sqrt(3) + 3)/6;
//	public static final double DEFAULT_SAMPLES_PER_DIRECT = 10;
	public static final double MAX_ERROR_2_POW2 = 4 * MAX_ERROR_POW2;
	
}
