package deform.transform.sweep;

import paths.paths.paths.QueryPath;
import paths.paths.results.project.BestProject;
import deform.Transform;
import deform.Vec;
import deform.paths.Path;



public class Sweep extends Transform {

	QueryPath path;
	SweepTo to;
	double prevT;
	
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
		BestProject best =path.project(to);
		return new Vec(best.t.getSimple(),Math.sqrt(best.distSquared));
		
	}

}
