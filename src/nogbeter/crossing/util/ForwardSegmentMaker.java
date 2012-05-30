package nogbeter.crossing.util;

import java.util.ArrayList;
import java.util.List;

import nogbeter.crossing.Crossing;
import nogbeter.paths.Path;
import nogbeter.paths.PathIndex;

public class ForwardSegmentMaker<L extends PathIndex> implements ISegmentMaker<L>{

	final Path<L> p;
	final ISimpleList<L> crossList;
	final List<Integer> next;
	
	public ForwardSegmentMaker(Path<L> path, ISimpleList<L> crossList, List<Integer> ends){
		this.p = path;
		this.crossList = crossList;
		next = makeNext(ends);
	}
	
	private List<Integer> makeNext(List<Integer> ends) {
		List<Integer> res = new ArrayList<Integer>();
		int start = 0;
		for(int end : ends){
			for(int i = start+1 ; i < end; i++){
				res.add(i);
			}
			res.add(start);
			start = end;
		}
		return res;
	}

	@Override
	public int getEndIndex(int i) {
		return next.get(i);
	}

	@Override
	public void getSegment(List<Path> result,int i) {
		List<Path> localRes = new ArrayList<Path>();
		p.getSubPath(crossList.get(i), crossList.get(next.get(i)), localRes);
//		System.out.printf("Start %s \n End %s\n",crossList.get(i), crossList.get(next.get(i)) );
//		for(Path p : localRes){
//			System.out.println(p);
//		}
		result.addAll(localRes);
	}

}
