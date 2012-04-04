package bezier.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class KDTreeUtil{

	// Inspired by On building fast kd-Trees for Ray Tracing, and on
	// doing that in O(N log N), Wald and Havran, 2006
	public static enum EventType{
		Start,
		Stop
	}
	
	public static class Event<A>{
		public final double x;
		public final EventType type;
		public final A objectId;
		public Event(A objectId,double x, EventType type) {
			this.objectId = objectId;
			this.x = x;
			this.type = type;
		}
		
		public String toString(){
			return String.format("(%s %f)",type,x);
		}
	}
	
	public static class SplitPlane{
		public final double x;
		public final double fitness;
		public SplitPlane(double x, double fitness) {
			this.x = x;
			this.fitness = fitness;
		}
	}
	
	public static class JudgedObjects<A>{
		public final Set<A> left;
		public final Set<A> inside;
		public final Set<A> right;
		public JudgedObjects(Set<A> left, Set<A> inside, Set<A> right) {
			this.left = left;
			this.inside = inside;
			this.right = right;
		}
	}
	
	public interface SplitPlaneFitnessHeuristic<A>{
		double getFitness(double x, Set<A> left, Set<A> inside, Set<A> right);
	}
	
	public static <A> SplitPlane getBestSplitPlane(List<Event<A>> events,Set<A> all,SplitPlaneFitnessHeuristic<A> heuristic){
		Set<A> left = new HashSet<A>();
		Set<A> inside = new HashSet<A>();
		Set<A> right = new HashSet<A>(all);
		SplitPlane best = null;
		for(int i = 0 ; i < events.size() ; i++){
			double x = events.get(i).x;
			double fitness = heuristic.getFitness(x,left, inside, right);
			if(best == null || best.fitness < fitness){
				best = new SplitPlane(x, fitness);
			}
			switch(events.get(i).type){
				case Start: right.remove(events.get(i).objectId); inside.add(events.get(i).objectId); break;
				case Stop: inside.remove(events.get(i).objectId); left.add(events.get(i).objectId); break;
			}
		}
		return best;
	}
	
	public static <A> JudgedObjects<A> judge(List<Event<A>> events,Set<A> all, double split){
		Set<A> left = new HashSet<A>();
		Set<A> inside = new HashSet<A>();
		Set<A> right = new HashSet<A>(all);
		for(int i = 0 ; i < events.size() ; i++){
			double x = events.get(i).x;
			if(x == split){
				return new JudgedObjects<A>(left, inside, right);
			}
			switch(events.get(i).type){
				case Start: right.remove(events.get(i).objectId); inside.add(events.get(i).objectId); break;
				case Stop: inside.remove(events.get(i).objectId); left.add(events.get(i).objectId); break;
			}
		}
		return new JudgedObjects<A>(left, inside, right);
	}
	
	public static <A> STuple<List<Event<A>>> splitEvent(List<Event<A>> events, Set<A> left){
		List<Event<A>> lr = new ArrayList<Event<A>>();
		List<Event<A>> rr = new ArrayList<Event<A>>();
		for(Event<A> e : events){
			if(left.contains(e.objectId)){
				lr.add(e);
			} else {
				rr.add(e);
			}
		}
		return new STuple<List<Event<A>>>(lr,rr);
	}
	
	private static <A> void sortEvents(List<Event<A>> events){
		Collections.sort(events,new Comparator<Event<A>>() {

			@Override
			public int compare(Event<A> o1, Event<A> o2) {
				return Double.compare(o1.x, o2.x);
			}
		});
	}
	
	public static <A extends HasBBox> STuple<List<Event<A>>> makeEvents(Iterable<A> objects){
		List<Event<A>> eventsX = new ArrayList<Event<A>>();
		List<Event<A>> eventsY = new ArrayList<Event<A>>();
		for(A b : objects){
			BBox bb = b.getBBox();
			eventsX.add(new Event<A>(b,bb.x,EventType.Start));
			eventsX.add(new Event<A>(b,bb.xr,EventType.Stop));
			eventsY.add(new Event<A>(b,bb.y,EventType.Start));
			eventsY.add(new Event<A>(b,bb.yd,EventType.Stop));
		}
		sortEvents(eventsX);
		sortEvents(eventsY);
//		System.out.printf("XEvents:\n");
//		for(Event<A> a : eventsX){
//			System.out.printf("%s\n",a);
//		}
//		System.out.printf("YEvents:\n");
//		for(Event<A> a : eventsX){
//			System.out.printf("%s\n",a);
//		}
		return new STuple<List<Event<A>>>(eventsX, eventsY);
	}
	
	public static class SplitJudgment<A>{
		public final Set<A> left;
		public final Set<A> right;
		public final List<Event<A>> leftX, leftY;
		public final List<Event<A>> rightX, rightY;
		public SplitJudgment(Set<A> left, Set<A> right, List<Event<A>> leftX,
				List<Event<A>> leftY, List<Event<A>> rightX,
				List<Event<A>> rightY) {
			this.left = left;
			this.right = right;
			this.leftX = leftX;
			this.leftY = leftY;
			this.rightX = rightX;
			this.rightY = rightY;
		}
	}
	
	public static <A> SplitJudgment<A> judgeSplit(Set<A> all, List<Event<A>> xEvents, 
			List<Event<A>> yEvents, SplitPlaneFitnessHeuristic<A> heuristic){
		SplitPlane bestX = getBestSplitPlane(xEvents, all, heuristic);
		SplitPlane bestY = getBestSplitPlane(yEvents, all, heuristic);
		JudgedObjects<A> judged;
		if(bestX.fitness > bestY.fitness){
			judged = judge(xEvents, all, bestX.x);
		} else {
			judged = judge(yEvents, all, bestY.x);
		}
		System.out.printf("All %d Left %d inside %d Right %d\n",all.size(),judged.left.size(), judged.inside.size(), judged.right.size());
		int halfInside= judged.inside.size() / 2;
		int i = 0;
		for(A a : judged.inside){
			if(i < halfInside){
				judged.left.add(a);
			} else {
				judged.right.add(a);
			}
			i++;
		}
		STuple<List<Event<A>>> splitXEvents = splitEvent(xEvents, judged.left);
		STuple<List<Event<A>>> splitYEvents = splitEvent(yEvents, judged.left);
		return new SplitJudgment<A>(judged.left,judged.right, splitXEvents.l, splitYEvents.l, splitXEvents.r, splitYEvents.r);
	}
	
	public static class LeastOverlap<A> implements SplitPlaneFitnessHeuristic<A>{
		@Override
		public double getFitness(double x, Set<A> left,
				Set<A> inside, Set<A> right) {
			return   - ((Math.abs(left.size() - right.size()) + inside.size() * 0.95));
		}
		
	}
}
