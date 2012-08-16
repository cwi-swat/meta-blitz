package deform.transform.sweep;

import paths.paths.paths.PathIndex;
import paths.paths.paths.QueryPath;
import paths.paths.results.project.BestProject;
import deform.Transform;
import deform.Vec;
import deform.paths.Path;



public class Sweep extends Transform {

	QueryPath path;
	SweepTo to;
	PathIndex prevT;
	Vec prev;
	
	public Sweep(Path p){
		this.path = p.normalise().toQueryPath().normaliseToLength();
		this.to = new SweepTo(path);
	}
	
	@Override
	public Vec to(Vec from) {
		return to.to(from);
	}

	@Override
	public Vec from(Vec to) {
		double bestd = Double.POSITIVE_INFINITY;
		if(prevT != null && prev.distanceSquared(to) < 10){
			bestd = path.getAt(prevT).distanceSquared(to);
		}
		BestProject best =path.project(bestd,to);
		prevT = best.t;
		prev = to;
		return new Vec(best.t.getSimple(),Math.sqrt(best.distSquared));
		
	}

}
