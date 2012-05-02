package bezier.projectiondeform;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import nogbeter.points.twod.Vec;

import bezier.composite.Path;
import bezier.composite.Paths;
import bezier.paths.Constants;
import bezier.paths.simple.Line;
import bezier.segment.curve.Curve;
import bezier.util.STuple;
import bezier.util.Tuple;
import bezier.util.Util;

public class LinesCoordinateSystem {
	
	final List<TriangleCoordinateSystem> lines;
	final double[] lengths;

	public LinesCoordinateSystem(List<TriangleCoordinateSystem> lines) {
		this.lines = lines;
		lengths = createLengthMap();
	}
	
	public double totalLength(){
		return lengths[lines.size()];
	}
	
	private double[] createLengthMap() {
		double[] result = new double[lines.size()+1];
		for(int i = 0; i < lines.size(); i++){
			result[i] = lines.get(i).startX ;
		}
		result[lines.size()] = lines.get(lines.size()-1).startX + lines.get(lines.size()-1).lengthX;
		return result;
	}

	public static LinesCoordinateSystem create(Path p){
		List<TriangleCoordinateSystem> result = new ArrayList<TriangleCoordinateSystem>();
		List<Vec> normals = new ArrayList<Vec>();
		List<Tuple<Double,Line>> linesnLengths = p.getApproxLinesAndLength();
		for(Tuple<Double, Line> lineLenght : linesnLengths){
			normals.add(lineLenght.r.getNormal());
		}
		double prevX = 0;
		for(int i = 0 ; i < linesnLengths.size(); i++){
			result.add(TriangleCoordinateSystem.create(prevX, linesnLengths.get(i).l, linesnLengths.get(i).r,
						normals.get(Util.mod(i-1,linesnLengths.size())), 
						normals.get(i), 
						normals.get(Util.mod(i+1,linesnLengths.size())))
					);
			prevX+=linesnLengths.get(i).l; 
		}
		return new LinesCoordinateSystem(result);
	}
	
	void splitCurveOnCoordinateSystem(Curve c, List<Curve> result){
		int first = Util.floorBinarySearch(lengths,c.getStartPoint().x);
		int last = Util.floorBinarySearch(lengths,c.getEndPoint().x);
		if(first > last){

			int tmp = first;
			first = last;
			last =tmp;
			if(Math.abs(lengths[first+1] - c.getEndPoint().x) <= Constants.MAX_ERROR){
				first++;
			}
			if(Math.abs(lengths[last] - c.getStartPoint().x) <= Constants.MAX_ERROR){
				last--;
			}
		} else {
			if(Math.abs(lengths[first+1] - c.getStartPoint().x) <= Constants.MAX_ERROR){
				first++;
			}
			if(Math.abs(lengths[last] - c.getEndPoint().x) <= Constants.MAX_ERROR){
				last--;
			}
		}
		if(first == last){
			result.add(c);
		} else {
			
			splits(c,Arrays.copyOfRange(lengths, first + 1, last+1),result);
		}
	}

	private void splits(Curve d, double[] splitsX, List<Curve> result) {
		if(d.getStartPoint().x > d.getEndPoint().x){
			splitsX = Util.reverse(splitsX);
		}
		Curve c = d;
		
		for(double x : splitsX){
			double t = c.findTForX(x);
		
			STuple<Curve> cs = c.split(t);
//			System.out.printf("%f %f %s %s %s\n",x,t,cs.l,cs.r,d);
			result.add(cs.l);
			c = cs.r;
		}
		result.add(c);
		System.out.printf("\n");
	}
	
	public Path deform(Path p){
		List<Curve> splitXCurves = new ArrayList<Curve>();
		for(Curve c : p.curves){
			splitCurveOnCoordinateSystem(c, splitXCurves);
		}
		return deformSplitCurves(splitXCurves);
	}

	private Path deformSplitCurves(List<Curve> splitXCurves) {
		List<Curve> result = new ArrayList<Curve>();
		for(Curve c : splitXCurves){
			int index = Util.floorBinarySearch(lengths,c.getStartPoint().x);
			if(index == lines.size()){
				index = lines.size()-1;
			}
			result.add(c.transform(lines.get(index)));
		}
		return new Path(result);
	}

	public Paths deform(Paths r) {
		List<Path> result = new ArrayList<Path>(r.nr());
		for(Path p : r.paths){
			result.add(deform(p));
		}
		return new Paths(result);
	}
	
	

}
