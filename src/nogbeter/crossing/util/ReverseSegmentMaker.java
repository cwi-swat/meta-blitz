package nogbeter.crossing.util;

import java.util.ArrayList;
import java.util.List;

import nogbeter.paths.Path;
import nogbeter.paths.PathIndex;

public class ReverseSegmentMaker <L extends PathIndex> implements ISegmentMaker<L>{

	final Path<L> p;
	final ISimpleList<L> crossList;
	final List<Integer> prev;
	
	public ReverseSegmentMaker(Path<L> path, ISimpleList<L> crossList, List<Integer> ends){
		this.p = path;
		this.crossList = crossList;
		prev = makePrev(ends);
	}
	
	private List<Integer> makePrev(List<Integer> ends) {
		List<Integer> res = new ArrayList<Integer>();
		int start = 0;
		for(int end : ends){
			res.add(end-1);
			for(int i = start ; i < end; i++){
				res.add(i);
			}
			start = end;
		}
		return res;
	}

	@Override
	public int getEndIndex(int i) {
		return prev.get(i);
	}
	
	public static <A extends Path>  List<A> reverseAll(List<A> resLocalRight) {
		List<A> res = new ArrayList<A>();
		for(int i = resLocalRight.size()-1 ; i >= 0; i--){
			res.add((A) resLocalRight.get(i).reverse());
		}
		return res;
	}
	@Override
	public void getSegment(List<Path> result,int i) {
		List<Path> localRes = new ArrayList<Path>();
		p.getSubPath(crossList.get(i), crossList.get(prev.get(i)), localRes);
		result.addAll(reverseAll(localRes));
	}
}
