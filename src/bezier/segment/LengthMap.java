package bezier.segment;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import bezier.util.Util;

public class LengthMap{
	
	public static class LengthTPair{
		public final double length;
		public final double t;
		
		public LengthTPair(double length, double t) {
			this.length = length;
			this.t = t;
		} 
	}
	
	private final List<LengthTPair> lengthTPairs;
	double totalLength = 0;
	double totalT = 0;
	
	public LengthMap() {
		this.lengthTPairs = new ArrayList<LengthMap.LengthTPair>();
		lengthTPairs.add(new LengthTPair(0, 0));
	}

	public String toString(){
		StringBuffer b = new StringBuffer();
		for(LengthTPair tp : lengthTPairs){
			b.append(String.format("%f %f\n",tp.t, tp.length));
		}
		return b.toString();
	}

	public void add(double tdiff, double lengthDiff){
		lengthTPairs.add(new LengthTPair(totalLength + lengthDiff,totalT + tdiff));
		totalT += tdiff;
		totalLength+=lengthDiff;
	}
	
	public double lengthBetween(double tStart, double tEnd){
		if(tEnd < tStart){
			return findLength(tEnd) + (totalLength - findLength(tStart));
		}
		return findLength(tEnd) - findLength(tStart);
	}
	
	public double totalLength(){
		return lengthTPairs.get(lengthTPairs.size()-1).length;
	}
	
	public double totalT(){
		return lengthTPairs.get(lengthTPairs.size()-1).t;
	}
	
	private static class LengthComparator implements Comparator<LengthTPair>{
		static LengthComparator instance = new LengthComparator();
		@Override
		public int compare(LengthTPair o1, LengthTPair o2) {
			return Double.compare(o1.length,o2.length);
		}
		
	}
	
	private static class TComparator implements Comparator<LengthTPair>{
		static TComparator instance = new TComparator();
		@Override
		public int compare(LengthTPair o1, LengthTPair o2) {
			return Double.compare(o1.t,o2.t);
		}
		
	}
	
	public double findT(double length){
		int i = Util.floorBinarySearch(lengthTPairs, new LengthTPair(length, 0),LengthComparator.instance);
		if(i <0 ){
			i = 0;
		}
		LengthTPair first = lengthTPairs.get(i);
		if(i == lengthTPairs.size()-1){
			return first.t;
		} else {
			LengthTPair second = lengthTPairs.get(i+1);
			double extraPart = (first.length - length) / (second.length - first.length);
			double extraT = (second.t - first.t) * extraPart;
			return first.t + extraT;
		}
	}
	
	public double findLength(double t){
		int i = Util.floorBinarySearch(lengthTPairs, new LengthTPair(0, t),TComparator.instance);
		LengthTPair first = lengthTPairs.get(i);
		if(i == lengthTPairs.size()-1){
			return first.length;
		} else {
			LengthTPair second = lengthTPairs.get(i+1);
			double extraPart = (first.t - t) / (second.t - first.t);
			double extraLength = (second.length - first.length) * extraPart;
			return first.length + extraLength;
		}
	}
	
}