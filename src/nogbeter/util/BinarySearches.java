package nogbeter.util;

import java.util.Comparator;
import java.util.List;

public class BinarySearches {

	public static <T extends Comparable<T>> int floorBinarySearch(List<T> elems, T toFind){
		return floorBinarySearch(elems, toFind, new Comparator<T>() {
			@Override
			public int compare(T o1, T o2) {
				return o1.compareTo(o2);
			}
		});
	}
	
	public static <T> int floorBinarySearch(List<T> elems, T toFind, Comparator<T> comp ){
		int min = -1;
		int max = elems.size();
		while(max - min > 1){
			int mid = ( max + min ) / 2;
			if(comp.compare(toFind,elems.get(mid)) < 0) max = mid;
			else min = mid; 
		}
		return min;
	}
	
}
