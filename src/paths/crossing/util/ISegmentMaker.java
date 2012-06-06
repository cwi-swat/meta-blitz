package paths.crossing.util;

import java.util.List;

import paths.paths.paths.Path;


public interface ISegmentMaker<P> {
	int getEndIndex(int i);
	void getSegment(List<Path> result,int i);
}
