package bezier.paths.factory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import bezier.paths.ConnectedPath;
import bezier.paths.EmptyPath;
import bezier.paths.Path;
import bezier.paths.compound.Append;
import bezier.paths.compound.Paths;
import bezier.paths.simple.CubicCurve;
import bezier.paths.simple.Line;
import bezier.paths.simple.QuadCurve;
import bezier.paths.simple.SimplePath;
import bezier.points.Vec;

public class PathFactory {
	
	public static Line createLine(Vec start, Vec end){
		return new Line(start, end); 
	}
	
	public static QuadCurve createQuad(Vec start, Vec control, Vec end){
		return new QuadCurve(start, control, end); 
	}
	
	public static CubicCurve createCubic(Vec start, Vec controll, Vec controlr, Vec end){
		return new CubicCurve(start, controll, controlr, end); 
	}
	
	public static ConnectedPath append(ConnectedPath ...paths ){
		return append(Arrays.asList(paths));
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static ConnectedPath append(List<ConnectedPath> paths ){
		removeEmpties((List)paths);
		if(paths.size() == 0){
			return new EmptyPath();
		} else if(paths.size() == 1){
			return paths.get(0);
		} else {
			List<SimplePath> add = new ArrayList<SimplePath>();
			for(ConnectedPath p: paths){
				if(p instanceof Append){
					add.addAll(((Append)p).getPaths());
				} else {
					add.add(p.getSimple());
				}
			}
			return new Append(add);
		}
	}
	
	public static Path join(Path ... paths){
		return join(new HashSet<Path>(Arrays.asList(paths)));
	}
	
	private static void removeEmpties(Iterable<Path> paths){
		Iterator<Path> it = paths.iterator();
		while(it.hasNext()){
			if(it.next() instanceof EmptyPath){
				it.remove();
			}
		}
		
	}
	
	public static Path join(Set<Path>  paths){
		removeEmpties(paths);
		if(paths.size() == 0){
			return new EmptyPath();
		} else if(paths.size() == 1){
			return paths.iterator().next();
		} else {
			Set<Path> add = new HashSet<Path>();
			for(Path p: paths){
				if(p instanceof Paths){
					add.addAll(((Paths)p).getPaths());
				} else {
					add.add(p);
				}
			}
			return new Paths(add);
		}
	}

}
