package paths.transform.nonlinear.ffd.instances;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import paths.paths.factory.CircleFactory;
import paths.points.twod.Vec;
import paths.transform.nonlinear.ffd.CubicFFD;

public class Fisheye {
	
	public static CubicFFD createFishEye(Vec loc, double origRad, double grow){
		List<Double> gridX = Arrays.asList(new Double[]{loc.x - origRad, loc.x + origRad});
		List<Double> gridY = Arrays.asList(new Double[]{loc.y - origRad, loc.y + origRad});
		List<List<Vec>> controls = new ArrayList<List<Vec>>();
		List<Vec> row = new ArrayList<Vec>();
		double kappa = CircleFactory.getKappa(Math.PI / 2.0);
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
		row.add(a.mul(0.5));
		row.add(b.mul(0.5));
		row.add(b.add(b.perpendicularCW().mul(kappa)));
		controls.add(row);
		row.add(d.add(d.perpendicularCW().mul(kappa)));
		row.add(d.mul(0.5));
		row.add(c.mul(0.5));
		row.add(c.add(c.perpendicularCCW().mul(kappa)));
		controls.add(row);
		row.add(d);
		row.add(d.add(d.perpendicularCCW().mul(kappa)));
		row.add(c.add(c.perpendicularCW().mul(kappa)));
		row.add(c);
		controls.add(row);
		return new CubicFFD(gridX, gridY, controls);
	}

}
