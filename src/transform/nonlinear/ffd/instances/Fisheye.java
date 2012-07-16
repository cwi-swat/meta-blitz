package transform.nonlinear.ffd.instances;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import deform.Vec;

import paths.paths.factory.CircleFactory;
import transform.nonlinear.ffd.CubicFFD;

public class Fisheye {
	
	public static CubicFFD createFishEye(Vec loc, double origRad, double grow){
		origRad /= Math.sqrt(2);
		List<Double> gridX = Arrays.asList(new Double[]{loc.x - origRad, loc.x + origRad});
		List<Double> gridY = Arrays.asList(new Double[]{loc.y - origRad, loc.y + origRad});
		List<List<Vec>> controls = new ArrayList<List<Vec>>();
		List<Vec> row = new ArrayList<Vec>();
		double kappa = CircleFactory.getKappa(Math.PI * 0.25);
		Vec a = CircleFactory.getCircleVec(-Math.PI * .75);
		Vec b = CircleFactory.getCircleVec(-Math.PI * .25);
		Vec c = CircleFactory.getCircleVec(Math.PI * .25);
		Vec d = CircleFactory.getCircleVec(Math.PI * .75);
		row.add(a);
		row.add(a.add(a.perpendicularCW().mul(kappa)));
		row.add(b.add(b.perpendicularCCW().mul(kappa)));
		row.add(b);
		controls.add(row);
		row = new ArrayList<Vec>();
		row.add(a.add(a.perpendicularCCW().mul(kappa)));
		row.add(a.mul(0.7));
		row.add(b.mul(0.7));
		row.add(b.add(b.perpendicularCW().mul(kappa)));
		controls.add(row);
		row = new ArrayList<Vec>();
		row.add(d.add(d.perpendicularCW().mul(kappa)));
		row.add(d.mul(0.7));
		row.add(c.mul(0.7));
		row.add(c.add(c.perpendicularCCW().mul(kappa)));
		controls.add(row);
		row = new ArrayList<Vec>();
		row.add(d);
		row.add(d.add(d.perpendicularCCW().mul(kappa)));
		row.add(c.add(c.perpendicularCW().mul(kappa)));
		row.add(c);
		controls.add(row);
		controls = mulAndMoveControls(controls,loc, origRad * grow);
		return new CubicFFD(origRad*2, gridX, gridY, controls);
	}
//
//	
//	private static List<List<Vec>> getRightDownQuadrant(double zoom){
//		double kappa = CircleFactory.getKappa(Math.PI * 0.5);
//		Vec a = CircleFactory.getCircleVec(0);
//		Vec b = CircleFactory.getCircleVec(Math.PI * .5);
//		List<List<Vec>> res = new ArrayList<List<Vec>>();
//		List<Vec> row = new ArrayList<Vec>();
//		res.add(new )
//		
//	}

	private static List<List<Vec>> mulAndMoveControls(List<List<Vec>> controls, Vec loc, double d) {
//		System.out.println("HALLLO!!!");
		List<List<Vec>> res = new ArrayList<List<Vec>>();
		for(List<Vec> controlRow : controls){
			List<Vec> row = new ArrayList<Vec>();
			for(Vec v : controlRow){
//				System.out.print(v + " ");
				row.add(v.mul(d).add(loc));
			}
			res.add(row);
//			System.out.println();
		}
//		System.out.println();
		return res;
	}

}
