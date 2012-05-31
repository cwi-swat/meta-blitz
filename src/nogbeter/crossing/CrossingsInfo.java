package nogbeter.crossing;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import bezier.util.Tuple;

import nogbeter.paths.Path;
import nogbeter.paths.PathIndex;

public class CrossingsInfo<L extends PathIndex, R extends PathIndex> {
	public final List<Crossing<L, R>> crossings;
	public final Set<Tuple<Path,Path>> parrallels;
	public final Set<Tuple<Path,Path>> antiParallels;
	public final Path<L> l ;
	public final Path<R> r;

	public CrossingsInfo(Path<L> l, Path<R> r, List<Crossing<L, R>> crossings,
			Set<Tuple<Path, Path>> parrallels,
			Set<Tuple<Path, Path>> antiParallels) {
		this.crossings = crossings;
		this.parrallels = parrallels;
		this.antiParallels = antiParallels;
		this.l = l;
		this.r = r;
	}



	public Tuple<Set<Path>,Set<Path>> getRemovesAndKeeps(){
		normalizePars();
		Set<Path> removes = new HashSet<Path>();
		Set<Path> keeps = new HashSet<Path>();
		for(Tuple<Path, Path> p : parrallels){
			removes.add(p.r);
			keeps.add(p.l);
		}
		for(Tuple<Path, Path> p : antiParallels){
			removes.add(p.l);
			removes.add(p.r);
		}
		return new Tuple<Set<Path>, Set<Path>>(removes,keeps);
	}
	
	private void normalizePars(){
		if(parrallels.isEmpty() && antiParallels.isEmpty()){
			return;
		}
		for(Crossing<L, R> c : crossings){
			Tuple<Path,Path> tuple = new Tuple(l.getClosedPath(c.l),r.getClosedPath(c.r));
			parrallels.remove(tuple);
			antiParallels.remove(tuple);
		}
	}

	
}
