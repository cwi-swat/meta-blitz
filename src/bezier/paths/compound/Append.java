package bezier.paths.compound;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import nogbeter.points.twod.Vec;
import nogbeter.transform.ITransform;

import bezier.paths.ConnectedPath;
import bezier.paths.Path;
import bezier.paths.simple.Line;
import bezier.paths.simple.SimplePath;
import bezier.paths.util.PathParameter;
import bezier.segment.curve.Curve;
import bezier.util.BBox;
import bezier.util.HasBBox;
import bezier.util.STuple;
import bezier.util.Tuple;
import bezier.util.Util;

public class Append extends ConnectedPath implements ICompoundPath{

	List<SimplePath> curves;
	int startIndex, endIndex;
	double startLength;
	double length;
	
	public Append(List<SimplePath> curves,int startIndex, int endIndex) {
		this.curves = curves;
		this.startIndex = startIndex;
		this.endIndex = endIndex;
	}
	
	public Append(List<SimplePath> curves) {
		this.curves = curves;
		startIndex = 0;
		endIndex = curves.size();
	}
	
	public Append(SimplePath[] curves) {
		this(Arrays.asList(curves));
	}
	
	public List<SimplePath> getPaths(){
		return curves;
	}
	
	public boolean isLine(){
		return false;
	}

	public Line getLine(){
		throw new Error("Not a line!");
	}
	
	public Vec getStartPoint(){
		return curves.get(0).getStartPoint();
	}
	
	public Vec getEndPoint(){
		return curves.get(curves.size()-1).getEndPoint();
	}


	private Tuple<Integer,Integer> getStraightInterval(int start, double x){
		int startStraight = start;
		do{
			startStraight--;
		} while(curves.get(Util.mod(startStraight, curves.size())).getEndPoint().x == x);
		startStraight++;
		int endStraight = start ;
		do{
			endStraight++;
		} while(curves.get(Util.mod(endStraight, curves.size())).getStartPoint().x == x);
		endStraight--;
		return new Tuple<Integer, Integer>(startStraight, endStraight);
	}
	
	public int nrBelow(Vec p){
		int nr = 0;
		int i = 0;
		int end = curves.size();
		while(i < end){
			SimplePath c = curves.get(i);
			boolean sameStart = c.getStartPoint().x == p.x;
			boolean sameEnd = c.getEndPoint().x == p.x;
			if(sameStart || sameEnd){ // border case
				Tuple<Integer,Integer> straight = getStraightInterval(i, p.x);
				// if tangent did not change, it is an intersection
				int s = Util.mod(straight.l, curves.size());
				int e = Util.mod(straight.r, curves.size());
				if(straight.l < 0){
					end = Math.min(end, -straight.l);
				}
				double yHigh = Math.max(curves.get(s).getEndPoint().y, curves.get(e).getStartPoint().y);
				
				if(yHigh < p.y && Math.signum(curves.get(s).getTangentAt(1.0).x) ==
							Math.signum(curves.get(e).getTangentAt(0.0).x)){
							nr++;
				} 
				i = straight.r+1;
			} else {
				nr+=c.nrBelow(p);
				i++;
			}
		}
		return nr;
	}
	


	public Vec getTangentAt(double t){
		int n = (int) t;
		if(n!= 0 && n % curves.size() == 0){
			n--; 
		}
		n = Util.mod(n, curves.size());
		double tl = t - n;
		return curves.get(n).getTangentAt(tl);
	}
	
	
	public Vec getAt(double t){
		int n = (int) t;
		if(n!= 0 && n % curves.size() == 0){
			n--; 
		}
		n = Util.mod(n, curves.size());
		double tl = t - n;
		return curves.get(n).getAt(tl);
	}

	@Override
	public  Path transform(ITransform m) {
		List<SimplePath> result = new ArrayList<SimplePath>(curves.size());
		for(SimplePath p : curves){
			result.add((SimplePath)p.transform(m));
		}
		return new Append(result);
	}
	@Override
	public ConnectedPath reverse() {
		List<SimplePath> result = new ArrayList<SimplePath>(curves.size());
		for(int i = curves.size()-1; i >= 0 ; i--){
			result.add((SimplePath)curves.get(i).reverse());
		}
		return new Append(result);
	}

	@Override
	public  ConnectedPath getWithAdjustedStartPoint(Vec newStart) {
		List<SimplePath> result = new ArrayList<SimplePath>(curves.size());
		result.add((SimplePath)curves.get(0).getWithAdjustedStartPoint(newStart));
		result.addAll(curves.subList(1, curves.size()));
		return new Append(result);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public BBox makeBBox() {
		List<HasBBox> hasBB = (List) curves;
		return new BBox(hasBB);
	}

	

	public static Path createComposite(List<SimplePath> p){
		if(p.size() == 1){
			return p.get(0);
		} else {
			return new Append(p);
		}
	}


	private Path create(List<SimplePath> curves, int start, int end){
		if(end - start   == 1){
			return curves.get(start);
		} else {
			return new Append(curves,start,end);
		}
	}
	
	@Override
	public  STuple<Path> splitSimpler() {
		int split = (startIndex + endIndex)/ 2;
		return new STuple<Path>(
				create(curves,startIndex,split),
				create(curves,split,endIndex));
	}

	@Override
	public boolean isClosed() {
		return getStartPoint().isEqError(getEndPoint());
	}


	@Override
	public Vec getAt(PathParameter t) {
		return getAt(t.t);
	}

	@Override
	public Vec getTangentAt(PathParameter t) {
		return getTangentAt(t.t);
	}
	

	public boolean isConnected(){
		return true;
	}
	
	public ConnectedPath getConnected(){
		return this;
	}

	
	public PathParameter getLeftParentPath(PathParameter original) {
		if(getLeftSimpler().isSimple()){
			return new PathParameter(original.connected,startIndex);
		} 
		return original;
	}
	
	public PathParameter getRightParentPath(PathParameter original) {
		if(getRightSimpler().isSimple()){
			return new PathParameter(original.connected,endIndex-1);
		}
		return original;
	}

	@Override
	public ConnectedPath getSubPath(PathParameter start, PathParameter end) {
		return getSubPath(start.t, end.t);
	}

	public ConnectedPath getSubPath(double start, double end) {
		int startn = (int)start;
		int endn = (int)end;
		if(endn == end){
			endn--;
		}
		if(startn == endn && start < end){
			return curves.get(startn).getSubPath(start-startn, end-endn);
		}
		List<SimplePath> result = new ArrayList<SimplePath>();
		result.add(curves.get(startn).getSubPath(start - startn,1.0).getSimple());
		for(int i = startn+1; Util.mod(i,curves.size()) != endn; i++){
			result.add(curves.get(Util.mod(i,curves.size())));
		}
		result.add(curves.get(endn).getSubPath(0, end - endn).getSimple());
		return new Append(result);
	}
	
	public Vec getBetween(double start, double end) {
		if(start < end){
			return getAt((start + end)/2);
		} else {
			end += curves.size();
			double between = Util.mod((start + end)/2,curves.size());
			return getAt(between);
		}
	}
	
	public Path getPath(){
		return this;
	}

	@Override
	public boolean isSimple() {
		return false;
	}

	@Override
	public SimplePath getSimple() {
		throw new Error("Not simple!");
	}

	@Override
	public ICompoundPath getCompound() {
		return this;
	}
	public String toString(){
		StringBuffer b = new StringBuffer();
		b.append("Append:\n");
		for(Path c : curves){
			b.append(c.toString());
			b.append('\n');
		}
		b.append('\n');
		return b.toString();
	}
	

}
