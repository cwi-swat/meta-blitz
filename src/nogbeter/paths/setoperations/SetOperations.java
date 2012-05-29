package nogbeter.paths.setoperations;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import bezier.util.Tuple;

import nogbeter.crossing.Crossing;
import nogbeter.paths.Path;
import nogbeter.paths.PathIndex;
import nogbeter.paths.factory.PathFactory;
import nogbeter.paths.simple.SimplePath;

public class SetOperations<L extends PathIndex,R extends PathIndex> {
	
	private static final int NoLooseEnd = -1;
	
	List<Crossing<L,R>> crossingSortedOnLeft;
	List<SegmentStartEndIndex> leftStartEndSegment;
	List<Integer> crossingIndexesSortedOnRight;
	List<SegmentStartEndIndex> rightStartEndSegment;
	int[] fromLeftToRight;
	Set<Path> usedPathsLeft;
	Set<Path> usedPathsRight;
	boolean[] used;
	
	Path<L> leftPath;
	Path<R> rightPath;

	
	static class SegmentStartEndIndex{
		final int start;
		final int end;
		public SegmentStartEndIndex(int start, int end) {
			this.start = start;
			this.end = end;
		}
		
		int getPrevIndex(int i){
			if(i == start){
				return end;
			} else {
				return i-1;
			}
		}
		
		int getNextIndex(int i){
			if(i == end){
				return start;
			} else {
				return i+1;
			}
		}
	}
	
	public SetOperations(Path<L> leftPath, Path<R> rightPath, 
			List<List<Crossing<L,R>>> crossingPerSegmentSortedOnLeft) {
		this.leftPath = leftPath;
		this.rightPath = rightPath;
		usedPathsLeft = new HashSet<Path>();
		usedPathsRight = new HashSet<Path>();
		Tuple<List<Crossing<L,R>>,List<SegmentStartEndIndex>> norm =
				getLeftSegs(crossingPerSegmentSortedOnLeft);
		this.crossingSortedOnLeft = norm.l;
		this.leftStartEndSegment = norm.r;
		used = new boolean[this.crossingSortedOnLeft.size()];
		this.crossingIndexesSortedOnRight =
				crossingsIndexesSortedOnRight(this.crossingSortedOnLeft);
		this.rightStartEndSegment = getRightStartEndSegs();
		fromLeftToRight = fromLeftToRight(this.crossingIndexesSortedOnRight);

	}
	
	private List<SegmentStartEndIndex> getRightStartEndSegs() {
		if(used.length == 0){
			return Collections.EMPTY_LIST;
		}
		Path prevSeg = rightPath.getSegment(getRightCrossing(0).r);
		usedPathsRight.add(prevSeg);
		List<SegmentStartEndIndex> res = new ArrayList<SegmentStartEndIndex>();
		int curStart = 0;
		for(int i = 1 ; i < crossingIndexesSortedOnRight.size() ; i++){
			Path curSeg = rightPath.getSegment(getRightCrossing(i).r);
			if(prevSeg != curSeg){
				usedPathsRight.add(curSeg);
				SegmentStartEndIndex newSeg = new SegmentStartEndIndex(curStart, i-1);
				for(int j = curStart ; j < i; j++){
					res.add(newSeg);
				}
				curStart = i;
				prevSeg = curSeg;
			} 
		}
		SegmentStartEndIndex newSeg = new SegmentStartEndIndex(curStart, 
				crossingIndexesSortedOnRight.size()-1);
		for(int j = curStart ; j <= crossingIndexesSortedOnRight.size()-1; j++){
			res.add(newSeg);
		}
		return res;
	}

	Tuple<List<Crossing<L,R>>,List<SegmentStartEndIndex>> 
	getLeftSegs(List<List<Crossing<L,R>>> crossingSortedOnLeft){
		int start = 0;
		List<Crossing<L, R>> cross = new ArrayList<Crossing<L,R>>();
		List<SegmentStartEndIndex> startEnds = new ArrayList<SegmentStartEndIndex>();
		for(List<Crossing<L, R>> crossingPerSeg : crossingSortedOnLeft){
			usedPathsLeft.add(leftPath.getSegment(crossingPerSeg.get(0).l));
			cross.addAll(crossingPerSeg);
			int end = start + (crossingPerSeg.size()-1);
			SegmentStartEndIndex newSeg = new SegmentStartEndIndex(start,end) ;
			for(; start <= end ; start++ ){
				startEnds.add(newSeg);
			}
		}
		return new Tuple<List<Crossing<L,R>>, List<SegmentStartEndIndex>>(cross, startEnds);
	}
	
	List<Integer> crossingsIndexesSortedOnRight(final List<Crossing<L,R>> crossings){
		List<Integer> res = new ArrayList<Integer>(crossings.size());
		for(int i = 0 ; i < crossings.size() ; i++) {
			res.add(i);
		}
		Collections.sort(res, new Comparator<Integer>() {
			@Override
			public int compare(Integer a, Integer b) {
				return crossings.get(a).r.compareTo(crossings.get(b).r);
			}
		});
		return res;
	}
	
	int[] fromLeftToRight(List<Integer> crossingIndexesSortedOnRight){
		int[] res = new int[crossingIndexesSortedOnRight.size()];
		for(int i = 0 ; i < crossingIndexesSortedOnRight.size() ; i++){
			res[crossingIndexesSortedOnRight.get(i)] = i;
		}
		return res;
	}
	
	int findLooseEnd(boolean shouldBeInside){
		for(int i = 0 ; i < used.length ; i++){
			if(!used[i] && 
					crossingSortedOnLeft.get(i).leftAfterInside == shouldBeInside){
				return i;
			}
		}
		return NoLooseEnd;
	}

	Path doOperation(boolean shouldBeInsideL,boolean reverseRight){
		List<Path> res = new ArrayList<Path>();
		if(!shouldBeInsideL){
			leftPath.getClosedSegmentsNotInSet(usedPathsLeft, res);
		}
		if(!reverseRight && !shouldBeInsideL){
			rightPath.getClosedSegmentsNotInSet(usedPathsRight, res);
		}
		int beginIndex;
		while((beginIndex = findLooseEnd(shouldBeInsideL)) != NoLooseEnd){
			Path closedPathFrom = getClosedPathFrom(beginIndex, reverseRight);
			res.add(closedPathFrom);
		}
		return PathFactory.createSet(res);
	}
	
	private Crossing<L, R> getRightCrossing(int i){
		return crossingSortedOnLeft.get(crossingIndexesSortedOnRight.get(i));
	}
	
	private int getNextLeftIndex(int i){
		return leftStartEndSegment.get(i).getNextIndex(i);
	}
	
	private int getNextRightIndex(int i,boolean reverse){
		if(reverse){
			return rightStartEndSegment.get(i).getPrevIndex(i);
		} else {
			return rightStartEndSegment.get(i).getNextIndex(i);
		}
	}

	private Path getClosedPathFrom(int beginIndex, boolean reverseRight) {
		List<Path> res = new ArrayList<Path>();
		while(!used[beginIndex]){
			int nextIndex = getNextLeftIndex(beginIndex);
			leftPath.getSubPath(crossingSortedOnLeft.get(beginIndex).l,
					crossingSortedOnLeft.get(nextIndex).l, res);
			int startRight = fromLeftToRight[nextIndex];
			int nextRight = getNextRightIndex(startRight,reverseRight);
	
			if(reverseRight){
				List<Path> resLocalRight = new ArrayList<Path>();
				rightPath.getSubPath(getRightCrossing(nextRight).r,
						getRightCrossing(startRight).r, resLocalRight);
				res.addAll(reverseAll(resLocalRight));
			} else {
				rightPath.getSubPath(getRightCrossing(startRight).r, 
						getRightCrossing(nextRight).r, res);
			}


			used[beginIndex] = used[nextIndex] =
			used[crossingIndexesSortedOnRight.get(startRight)] = true;
			beginIndex = crossingIndexesSortedOnRight.get(nextRight);
		}
		return PathFactory.createClosedPath(res);
	}
	
	private List<Path> reverseAll(List<Path> resLocalRight) {
		List<Path> res = new ArrayList<Path>();
		for(int i = resLocalRight.size()-1 ; i >= 0; i--){
			res.add(resLocalRight.get(i).reverse());
		}
		return res;
	}

	public Path union(){
		return doOperation(false,false);
	}
	
	public Path intersection(){
		return doOperation(true,false);
	}
	
	public Path substract(){
		return doOperation(false,true);
	}
	
}
