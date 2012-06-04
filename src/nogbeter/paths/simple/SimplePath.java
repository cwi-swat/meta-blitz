package nogbeter.paths.simple;

import static nogbeter.paths.results.transformers.TupleTransformers.unitTup;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import bezier.util.Tuple;
import nogbeter.paths.Path;
import nogbeter.paths.PathIndex;
import nogbeter.paths.SimplyIndexedPath;
import nogbeter.paths.SplittablePath;
import nogbeter.paths.compound.Append;
import nogbeter.paths.results.intersections.IIntersections;
import nogbeter.paths.results.intersections.IntersectionType;
import nogbeter.paths.results.intersections.Intersections;
import nogbeter.paths.results.project.BestProjectTup;
import nogbeter.paths.results.transformers.IPathIndexTransformer;
import nogbeter.paths.results.transformers.PITransformers;
import nogbeter.paths.results.transformers.PathIndexTupleTransformer;
import nogbeter.paths.simple.curve.Curve;
import nogbeter.points.angles.AngularInterval;
import nogbeter.points.angles.AngularIntervalFactory;
import nogbeter.points.oned.Interval;
import nogbeter.points.twod.Vec;
import nogbeter.transform.nonlinear.pathdeform.PathDeform;

public abstract class SimplePath extends SimplyIndexedPath{


	public SimplePath(Interval tInterval) {
		super(tInterval);
	}

	public Vec getStartPoint(){
		return getAtLocal(0.0);
	}
	
	public Vec getEndPoint(){
		return getAtLocal(1.0);
	}
	
	public abstract Vec getAtLocal(double t);
	public abstract Vec getTangentAtLocal(double t);
	
	@Override
	public Vec getAtSimply(double t){
		return getAtLocal(tInterval.getFactorForPoint(t));
	}
	
	@Override
	public Vec getTangentAtSimply(double t){
		return getTangentAtLocal(tInterval.getFactorForPoint(t));
	}
	
	
	public IIntersections<SimplePathIndex, SimplePathIndex> selfIntersection() {
		IIntersections<SimplePathIndex,SimplePathIndex> a =
				new Intersections<SimplePathIndex, SimplePathIndex>(minPathIndex(), minPathIndex(),
						getStartPoint(), getStartPoint(), getStartTan(), getStartTan(), 
						IntersectionType.BorderRight, 
						IntersectionType.BorderRight);
		IIntersections<SimplePathIndex,SimplePathIndex> b =
				new Intersections<SimplePathIndex, SimplePathIndex>(maxPathIndex(), maxPathIndex(),
						getEndPoint(), getEndPoint(), getEndTan(), getEndTan(), 
						IntersectionType.BorderLeft, 
						IntersectionType.BorderLeft);
		return a.append(b);
	}
	
	protected IIntersections<SimplePathIndex,SimplePathIndex> makeIntersectionResult(
			SimplePath lhs, double tl, double tr) {
		SimplePathIndex l = lhs.makeGlobalPathIndexFromLocal(tl);
		SimplePathIndex r = makeGlobalPathIndexFromLocal(tr);
		return new Intersections<SimplePathIndex, SimplePathIndex>(l, r,
				lhs.getAtLocal(tl), getAtLocal(tr), lhs.getTangentAtLocal(tl),getTangentAtLocal(tr),
				lhs.getIntersectionType(l), this.getIntersectionType(r));
	}
	
	private IntersectionType getIntersectionType(SimplePathIndex l) {
		if(l.t == tInterval.high){
			return IntersectionType.BorderLeft;
		} else if(l.t == tInterval.low){
			return IntersectionType.BorderRight;
		} else {
			return IntersectionType.Normal;
		}
	}

	public SimplePathIndex makeGlobalPathIndexFromLocal(double t){
		return new SimplePathIndex(tInterval.getAtFactor(t));
	}
	
	public BestProjectTup<SimplePathIndex, SimplePathIndex> makeBestProject(double dist,SimplePath lhs,
			double tl, double tr) {
		return new BestProjectTup<SimplePathIndex,SimplePathIndex>(dist,
						lhs.makeGlobalPathIndexFromLocal(tl), 
						makeGlobalPathIndexFromLocal(tr));
	}
	
	public int nrChildren(){
		return 0;
	}
	
	public Path getChild(int i){
		return null;
	}

	public abstract int awtCurSeg(float[] coords) ;
	
	public abstract Tuple<Path<SimplePathIndex>,Double> normaliseToLength(double prevLength);

	
	@Override
	public AngularInterval getAngularInsideInterval(SimplePathIndex t) {
		return AngularIntervalFactory.create180DegreesInterval(getTangentAt(t));
	}
	
	@Override
	public Vec getStartTan() {
		return getTangentAtLocal(0.0);
	}

	@Override
	public Vec getEndTan() {
		return getTangentAtLocal(1.0);
	}
	
	public Vec getArbPoint(){ return getStartPoint();}
	public Vec getArbPointTan(){ return getStartTan(); }

	@Override
	public List<Vec> getTangents(SimplePathIndex t) {
		List<Vec> v = new ArrayList<Vec>();
		v.add(getTangentAt(t));
		return v;
	}
	
	@Override
	public Path pathDeform(PathDeform p) {
		if(p.isSimpleTransform()){
			return transform(p);
		} else {
			double x = p.getSplitPoint();
			double midt = findXFast(x);
			
			if(x == 0){
				Vec mid = new Vec(x,getStartPoint().y);
				Path z = getWithAdjustedStartPointMono(mid);
				return z.pathDeform(p.getSubList(z.getBBox().xInterval));
			} 
			if(x == 1){
				Vec mid = new Vec(x,getEndPoint().y);
				Path z = getWithAdjustedEndPointMono(mid);
				return z.pathDeform(p.getSubList(z.getBBox().xInterval));
			} 
			Tuple<SimplePath,SimplePath> simp = splitSimp(midt);
			Vec mid = new Vec(x,simp.r.getStartPoint().y);
			SimplePath left =simp.l.getWithAdjustedEndPointMono(mid);
			SimplePath right = (SimplePath) simp.r.getWithAdjustedStartPointMono(mid);
			if(left.getBBox().xInterval.high != x && left.getBBox().xInterval.low != x){
				System.out.printf("Left wrong!! %f %s\n",x,left);
			} 
			if(right.getBBox().xInterval.high != x && right.getBBox().xInterval.low != x){
				System.out.printf("Right wrong!! %f %f %f\n",x,right.getBBox().xInterval.low,right.getBBox().xInterval.high);
			} 
			if(left == null){
				return right.pathDeform(p.getSubList(right.getBBox().xInterval));
			} else if(right == null){
				return left.pathDeform(p.getSubList(left.getBBox().xInterval));
			}
			return new Append(left.pathDeform(p.getSubList(left.getBBox().xInterval)),
					right.pathDeform(p.getSubList(right.getBBox().xInterval)));
		}
	}
	
	public abstract SimplePath getWithAdjustedStartPointMono(Vec mid) ;

	public abstract SimplePath getWithAdjustedEndPointMono(Vec v);

	public abstract Tuple<SimplePath, SimplePath> splitSimp(double midt) ;

	public abstract double findXFast(double splitPoint);
	
}
