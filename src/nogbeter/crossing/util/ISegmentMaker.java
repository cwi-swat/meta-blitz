package nogbeter.crossing.util;

import java.util.List;

import nogbeter.paths.Path;

public interface ISegmentMaker<P> {
	int getEndIndex(int i);
	void getSegment(List<Path> result,int i);
}
