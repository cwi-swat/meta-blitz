package bezier.segment;

import java.util.ArrayList;
import java.util.List;

import bezier.composite.Path;
import bezier.paths.simple.CubicCurve;
import bezier.points.Transformation;
import bezier.points.Vec;
import bezier.segment.curve.Curve;

import static java.lang.Math.*;

public class Circle {
	
	
	public static Path makeCircle(Vec center, double diameter){
		return makeEllipse(center, diameter, diameter);
	}
	
	public static Path makeEllipse(Vec center, double width, double heigth){
		return makeEllipiticalArc(center, width, heigth, 0, 2*Math.PI);
	}
	
	public static Vec getCircularCenter(Vec pointA, Vec pointB, double anglularLength){
		Vec between = pointA.interpolate(0.5, pointB);
		double dist = pointA.distance(pointB);
		double radius = ((dist/2) / tan(0.5*anglularLength));
		return between.add(pointB.sub(pointA).normalize().mul(radius).perpendicularCW());
	}
	
	public static Path makeCircularArc(Vec pointA, Vec pointB, double anglularLength){
		Vec center =getCircularCenter(pointA,pointB,anglularLength);
		Vec relPointA= pointA.sub(center);
		double startAngle = atan2(relPointA.y,relPointA.x);
		return makeCircularArc(center, relPointA.norm() * 2, startAngle, startAngle- anglularLength);
	}
	
	public static Path makeCircularArc(Vec center, double diameter, double startAngle, double endAngle){
		return makeEllipiticalArc(center, diameter, diameter, startAngle, endAngle);
	}
	
	public static Path makeEllipiticalArc(Vec center, double width , double height, double startAngle, double endAngle){
		Path result = makeArc(startAngle,endAngle);
		return result.transform(Transformation.id.scale(width/2, height/2).translate(center));
	}
	
	
	public static double getKappa(double angularLength){
		double middleAngle = angularLength/2.0;
		return (4.0/3.0)*( sin(angularLength) - 2*sin(middleAngle))/(cos(angularLength) - 1);
	}

	
	
	public static Path makeArc(double startAngle, double endAngle){
		double angleDiff =endAngle - startAngle;
		int segs = (int)Math.ceil(Math.abs((angleDiff) / (0.25*Math.PI)));
		double radInc = angleDiff/segs;
		double kappa = getKappa(radInc);
		double prevRad = startAngle;
		Vec prevPoint = getCircleVec(prevRad);
		double rad = prevRad ;
		List<Curve> result = new ArrayList<Curve>();
		for(int i = 0 ; i < segs ; i++){
			rad+=radInc;
			
			Vec curPoint = getCircleVec(rad);
			result.add(new CubicCurve(prevPoint, prevPoint.add(prevPoint.perpendicularCCW().mul(kappa)),
					curPoint.add(curPoint.perpendicularCW().mul(kappa)),curPoint));
			prevPoint = curPoint;
		}
		return new Path(result);
	}
	
	private static Vec getCircleVec(double d) {
		return new Vec(Math.cos(d),Math.sin(d));
	}
	
}
