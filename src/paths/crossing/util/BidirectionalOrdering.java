package paths.crossing.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/* Given a list of elements and a comparator, define two views on the elements,
 * one in the original ordering, and one with the new ordering
 * this class then allows you switch indexes between the two views on the elements
 * 
 * i.e. what is the index on the right ordering for the 3rd element in the left ordering?
 *  */

public class BidirectionalOrdering<A> {
	
	private final List<A> leftOrdering;
	private final List<Integer> fromRightToLeft;
	private final List<Integer> fromLeftToRight;
	
	public BidirectionalOrdering(List<A> leftOrdering, Comparator<A> rightOrdering){
		this.leftOrdering = leftOrdering;
		this.fromRightToLeft = indexes(leftOrdering.size());
		Collections.sort(this.fromRightToLeft,
				new UseIndexInsteadComparator<A>(leftOrdering, rightOrdering));
		this.fromLeftToRight = makeFromLeftToRight(fromRightToLeft);
	}
	
	public ISimpleList<A> getLeft(){
		return new ISimpleList<A>() {
			@Override
			public int size() {
				return leftOrdering.size();
			}

			@Override
			public A get(int i) {
				return leftOrdering.get(i);
			}
		};
	}
	
	public ISimpleList<A> getRight(){
		return new ISimpleList<A>() {
			@Override
			public int size() {
				return leftOrdering.size();
			}

			@Override
			public A get(int i) {
				return leftOrdering.get(fromRightToLeft(i));
			}
		};
	}
	
	
	public int size(){
		return leftOrdering.size();
	}
	
	public A getRight(int i){
		return leftOrdering.get(fromRightToLeft.get(i));
	}
	
	public int fromLeftToRight(int i){
		return fromLeftToRight.get(i);
	}
	
	public int fromRightToLeft(int i){
		return fromRightToLeft.get(i);
	}

	private static List<Integer> makeFromLeftToRight(List<Integer> right) {
		Integer[] res = new Integer[right.size()];
		for(int i = 0 ; i < right.size() ; i++){
			res[right.get(i)] = i;
		}
		return Arrays.asList(res);
	}

	static List<Integer> indexes(int size){
		List<Integer> res = new ArrayList<Integer>(size);
		for(int i = 0 ; i < size ; i++){
			res.add(i);
		}
		return res;
	}
	
	static class UseIndexInsteadComparator<A> implements Comparator<Integer>{
		final List<A> orig;
		final Comparator<A> cmp;
		UseIndexInsteadComparator(List<A> orig, Comparator<A> cmp){
			this.orig = orig; 
			this.cmp = cmp;
		}
		@Override
		public int compare(Integer o1, Integer o2) {
			return cmp.compare(orig.get(o1),orig.get(o2));
		}
	}
}
