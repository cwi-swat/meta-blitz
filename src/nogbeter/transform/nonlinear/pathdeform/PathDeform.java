package nogbeter.transform.nonlinear.pathdeform;

import nogbeter.paths.Path;
import nogbeter.paths.iterators.LineIterator;
import nogbeter.paths.iterators.SimplePathIterator;
import nogbeter.paths.simple.Line;
import nogbeter.paths.simple.SimplePath;
import nogbeter.points.oned.Interval;
import nogbeter.points.twod.BBox;
import nogbeter.points.twod.Vec;
import nogbeter.transform.IToTransform;
import nogbeter.transform.ITransform;
import nogbeter.transform.nonlinear.IDeform;
import nogbeter.util.BinarySearches;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import bezier.util.Tuple;

public class PathDeform implements IDeform, IToTransform{
	
	final List<LineInfo> lines;
	final List<Double> lengths;
	
	static class LineInfo{
		Line line;
		Vec normal;
		TriangleCoordinateSystem coord;
		
		public LineInfo(Line line) {
			this.line = line;
			this.normal = line.getStartTan().tanToNormal().normalize();
		}
		
		void makeCoord(Vec prevNorm, Vec nextNorm){
			this.coord = TriangleCoordinateSystem.create(line, prevNorm, normal, nextNorm);
		}
	}

	public PathDeform(List<LineInfo> lines, List<Double> lengths){
		this.lines = lines;
		this.lengths = lengths;
	}
	
	public PathDeform(Path p){
		p = p.normaliseToLength();
		this.lines = makeLines(p);
		lengths = makeLengths();
		makeCoords(p);

	}


	private List<Double> makeLengths() {
		List<Double> lengths = new ArrayList<Double>(lines.size());
		lengths.add(0.0);
		for(LineInfo l : lines){
			lengths.add(l.line.tInterval.high);
		}
		return lengths;
	}

	private void makeCoords(Path p) {
		if(lines.size()==1){
			lines.get(0).makeCoord(lines.get(0).normal, lines.get(0).normal);
			return;
		}
		if(p.isClosed()){
			lines.get(0).makeCoord(lines.get(lines.size()-1).normal, lines.get(1).normal);
		} else {
			lines.get(0).makeCoord(lines.get(0).normal, lines.get(1).normal);
		}
		for(int i = 1 ; i < lines.size()-1; i++){
			lines.get(i).makeCoord(lines.get(i-1).normal, lines.get(i+1).normal);
			
		}
		if(p.isClosed()){
			lines.get(lines.size()-1).makeCoord(lines.get(lines.size()-2).normal,lines.get(0).normal);
		} else {
			lines.get(lines.size()-1).makeCoord(lines.get(lines.size()-2).normal,lines.get(lines.size()-1).normal);
		}
	}


	private List<LineInfo> makeLines(Path p) {
		List<LineInfo> lines = new ArrayList<LineInfo>();
		SimplePathIterator it = new SimplePathIterator(p);
		while(it.hasNext()){
			makeAndExpandLines(lines,it.next());
		}
		return lines;
	}
	
	private void makeAndExpandLines(List<LineInfo> lines,SimplePath next) {
		if(next instanceof Line){
			lines.add(new LineInfo((Line)next));
		} else {
			Tuple<Path, Path> simp = next.splitSimpler();
			makeAndExpandLines(lines, (SimplePath)simp.l);
			makeAndExpandLines(lines, (SimplePath)simp.r);
		}
		
	}
	
	private PathDeform getSubList(int start, int end){
		return new PathDeform(lines.subList(start, end),
				lengths.subList(start,end +1));
	}
	
	
	private PathDeform getSubList(Interval xInterval){
		if(xInterval.isEmpty()){
			return this;
		}
		int start = BinarySearches.floorBinarySearch(lengths, xInterval.low);
		int end = BinarySearches.floorBinarySearch(lengths, xInterval.high);
		if(lengths.get(end)!=xInterval.high && end != lengths.size()-1){
			end+=1;
		}
		if(start == end){
			// vertical line
			if(end >= lines.size()){
				start--;
			} else {
				end++;
			}
		}
		return getSubList(start,end);
	}
	
	public Boolean isSimpleTransform(){
		return lines.size() == 1;
	}

	public Vec to(Vec d) {
		return lines.get(0).coord.getAt(d);
	}

	public double getSplitPoint() {
		return lengths.get(lengths.size()/2);
	}

	@Override
	public IDeform subDeform(BBox b) {
		return getSubList(b.xInterval);
	}

	@Override
	public boolean isSimple() {
		return isSimpleX();
	}

	@Override
	public boolean isSimpleX() {
		return lines.size() == 1;
	}

	@Override
	public boolean isSimpleY() {
		return true;
	}

	@Override
	public double getSplitPointX() {
		return lengths.get(lengths.size()/2);
	}

	@Override
	public double getSplitPointY() {
		throw new Error("No splitpoint defined!");
	}

	@Override
	public Path deform(Path p) {
		return p.transform(this);
	}
}
