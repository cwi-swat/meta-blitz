package bezier.composite;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Intersections {
	
	public final List<List<Double>> intersectionsPerPath;
	private boolean isEmpty;
	public Intersections(int nrPaths) {
		intersectionsPerPath = new ArrayList<List<Double>>(nrPaths);
		for(int i = 0 ; i < nrPaths ; i++){
			intersectionsPerPath.add(new ArrayList<Double>());
		}
		isEmpty = true;
	}
	
	boolean isEmpty(){
		return isEmpty;
	}
	
	
	void add(int pathIndex,double t){
		intersectionsPerPath.get(pathIndex).add(t);
		isEmpty = false;
	}
	
	public List<Double> getFor(int pathIndex){
		List<Double> result = intersectionsPerPath.get(pathIndex);
		Collections.sort(result);
		return result;
	}
	

}
