package paths.paths.factory;

import static java.lang.Math.atan2;
import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static java.lang.Math.tan;

import java.util.ArrayList;
import java.util.List;

import deform.Vec;
import deform.paths.Path;
import deform.segments.SegPath;
import deform.segments.SegmentsMaker;
import deform.shapes.Shape;

import static deform.Combinators.*;
import static deform.Library.*;

public class CircleFactory {

	public static Shape makeCircle(Vec center, double diameter) {
		return close(makeArc(0, 2 * Math.PI));
	}

	public static Shape makeEllipse(Vec center, double width, double heigth) {
		return close(makeEllipiticalArc(center, width,
				heigth, 0, 2 * Math.PI));
	}

	public static Vec getCircularCenter(Vec pointA, Vec pointB,
			double anglularLength) {
		Vec between = pointA.interpolate(0.5, pointB);
		double dist = pointA.distance(pointB);
		double radius = ((dist / 2) / tan(0.5 * anglularLength));
		return between.add(pointB.sub(pointA).normalize().mul(radius)
				.perpendicularCW());
	}

	public static Path makeCircularArc(Vec pointA, Vec pointB,
			double anglularLength) {
		Vec center = getCircularCenter(pointA, pointB, anglularLength);
		Vec relPointA = pointA.sub(center);
		double startAngle = atan2(relPointA.y, relPointA.x);
		return makeCircularArc(center, relPointA.norm() * 2, startAngle,
				startAngle - anglularLength);
	}

	public static Path makeCircularArc(Vec center, double diameter,
			double startAngle, double endAngle) {
		return makeEllipiticalArc(center, diameter, diameter, startAngle,
				endAngle);
	}

	public static Path makeEllipiticalArc(Vec center, double width,
			double height, double startAngle, double endAngle) {
		Path result = makeArc(startAngle, endAngle);
		return transform(scale(width/2,height/2).compose(translate(center)),result);
	}

	public static double getKappa(double angularLength) {
		double middleAngle = angularLength / 2.0;
		return (4.0 / 3.0) * (sin(angularLength) - 2 * sin(middleAngle))
				/ (cos(angularLength) - 1);
	}

	public static Path makeArc(double startAngle, double endAngle) {
		SegmentsMaker maker = new SegmentsMaker();
		double angleDiff = endAngle - startAngle;
		int segs = (int) Math.ceil(Math.abs((angleDiff) / (0.25 * Math.PI)));
		double radInc = angleDiff / segs;
		double kappa = getKappa(radInc);
		double prevRad = startAngle;
		Vec prevPoint = getCircleVec(prevRad);
		double rad = prevRad;
		for (int i = 0; i < segs; i++) {
			rad += radInc;

			Vec curPoint = getCircleVec(rad);
			maker.cubic(prevPoint,
					prevPoint.add(prevPoint.perpendicularCW().mul(kappa)),
					curPoint.add(curPoint.perpendicularCCW().mul(kappa)),
					curPoint);
			prevPoint = curPoint;
		}
		return maker.done().toPath();
	}

	public static Vec getCircleVec(double d) {
		return new Vec(Math.cos(d), Math.sin(d));
	}

	public static Shape makeCircle(double size) {
		return makeCircle(Vec.ZeroVec, size);
	}

	public static Shape makeCircle() {
		return close(makeArc(0, 2 * Math.PI));
	}

}
