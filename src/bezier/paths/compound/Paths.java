package bezier.paths.compound;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import bezier.paths.IConnectedPath;
import bezier.paths.Path;
import bezier.paths.simple.Line;
import bezier.paths.util.ITransform;
import bezier.paths.util.PathParameter;
import bezier.points.Vec;
import bezier.util.BBox;
import bezier.util.HasBBox;
import bezier.util.KDTreeUtil;
import bezier.util.KDTreeUtil.Event;
import bezier.util.STuple;
import bezier.util.KDTreeUtil.SplitJudgment;
import bezier.util.Util;
import static bezier.util.KDTreeUtil.*;

public class Paths extends CompoundPath{
	

	final Set<Path> paths;
	final List<Event<Path>> xEvents,yEvents;


	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Paths(Set<Path> paths) {
		this.paths = paths;
		Iterable<Path> iPaths = (Set)paths;
		STuple<List<Event<Path>>> events = KDTreeUtil.makeEvents(iPaths);
		xEvents = events.l; yEvents = events.r;
	}
	
	public Paths(Set<Path> paths, List<Event<Path>> xEvents, List<Event<Path>> yEvents){
		this.paths = paths;
		this.xEvents = xEvents;
		this.yEvents = yEvents;
	}
	
	
	public Paths(Path[] paths) {
		this(new HashSet<Path>(Arrays.asList(paths)));
	}

	@SuppressWarnings({"unchecked", "rawtypes"})
	@Override
	public BBox makeBBox() {
		Iterable<HasBBox> bbx = (Set)paths;
		return new BBox(bbx);
	}

	@Override
	public
	boolean isLine() {
		return false;
	}

	@Override
	public
	Line getLine() {
		return null;
	}

	@Override
	public Vec getAt(PathParameter t) {
		return t.connected.getConnected().getAt(t.t);
	}

	@Override
	public Vec getTangentAt(PathParameter t) {
		return t.connected.getConnected().getTangentAt(t.t);
	}

	@Override
	public  Path transform(ITransform m) {
		Set<Path> ps = new HashSet<Path>(paths.size());
		for(Path p : paths){
			ps.add((Append)p.transform(m));
		}
		return new Paths(ps);
	}



	@Override
	public boolean isInside(Vec p) {
		expand();
		return Util.xor(!getLeftSimpler().isSimple() && getLeftSimpler().getCompound().isInside(p), 
			(!getRightSimpler().isSimple() && getRightSimpler().getCompound().isInside(p)));
	}


	public boolean isConnected(){
		return false;
	}
	
	public IConnectedPath getConnected(){
		throw new Error("Not connected!");
	}

	private Path create(Set<Path> paths, List<Event<Path>> eventX, List<Event<Path>> eventsY){
		if(paths.size() == 1){
			return paths.iterator().next();
		} else {
			return new Paths(paths,eventX,eventsY);
		}
	}

	@Override
	public STuple<Path> splitSimpler() {
		SplitJudgment<Path> splitJudge = KDTreeUtil.judgeSplit(paths,xEvents,yEvents, new LeastOverlap<Path>());
		return new STuple<Path>(
				create(splitJudge.left,splitJudge.leftX,splitJudge.leftY),
				create(splitJudge.right,splitJudge.rightX,splitJudge.rightY));
	}	


	public PathParameter getLeftParentPath(PathParameter original) {
		if(getLeftSimpler().isConnected()){
			return new PathParameter(getLeftSimpler(),0);
		}
		return original;
	}
	
	public PathParameter getRightParentPath(PathParameter original) {
		if(getRightSimpler().isConnected()){
			return new PathParameter(getRightSimpler(),0);
		}
		return original;
	}

	@Override
	public Path getSubPath(PathParameter start, PathParameter end) {
		assert start.connected == end.connected;
		return start.connected.getSubPath(start, end);
	}
	
}
