package bezier.composite;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import bezier.points.Vec;
import bezier.segment.BestProjection;
import bezier.segment.curve.Curve;
import bezier.segment.curve.CurveApproxTree;
import bezier.segment.curve.TInterval;
import bezier.util.Util;

public class ProjectPath {

	final List<CurveApproxTree> trees;
	public final double length;
	
	ProjectPath(List<Curve> curves){
		trees = new ArrayList<CurveApproxTree>(curves.size());
		double length = 0;
		for(Curve c : curves){
			CurveApproxTree  t = c.getApproxTree();
			length = t.expandFullyLength(length);
			trees.add(t);
		}
		this.length = length;
	}
	
	public BestProjection<Double> project(Vec p){
		BestProjection<Double> best = new BestProjection<Double>();
		final List<Integer> indexesNearestFirst = Util.natListTill(trees.size());
		final List<Double> distanceMiddles = new ArrayList<Double>(trees.size());
		for(int i : indexesNearestFirst){
			double dist = trees.get(i).curve.getAt(0.5).distanceSquared(p);
			distanceMiddles.add(dist);
			best.update(i+0.5, dist);
		}
		Collections.sort(indexesNearestFirst,new Comparator<Integer>() {
			@Override
			public int compare(Integer o1, Integer o2) {
				return Double.compare(
						distanceMiddles.get(o1), distanceMiddles.get(o2));
			}
		});
		for(int i : indexesNearestFirst){
			trees.get(i).project(p, best);
		}
		return best;
	}
	
}
