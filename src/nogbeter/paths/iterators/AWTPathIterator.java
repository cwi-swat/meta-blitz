package nogbeter.paths.iterators;



import nogbeter.paths.Path;
import nogbeter.paths.simple.SimplePath;
import java.awt.geom.PathIterator;

import bezier.points.Vec;

public class AWTPathIterator implements PathIterator{

	static enum State{
		HEAD,
		BODY,
		TAIL,
	}
	
	State state;
	
	final ClosedPathIterator closed;
	Vec start;
	SimplePathIterator curSimple;
	SimplePath cur;
	
	public AWTPathIterator(Path root){
		closed = new ClosedPathIterator(root);
		cur = null;
		state =  State.TAIL;
	}
	
	
	@Override
	public int getWindingRule() {
		return PathIterator.WIND_EVEN_ODD;
	}

	@Override
	public boolean isDone() {
		return state == State.TAIL && !closed.hasNext();
	}

	@Override
	public void next() {
		switch(state){

		case HEAD : state = State.BODY; 
		case BODY:
			if(curSimple.hasNext()){
				cur = curSimple.next();
			} else {
				state = State.TAIL;
			}
		case TAIL : 
			state = State.HEAD; 
			Path close = closed.next();
			start = close.getStartPoint();
			curSimple = new SimplePathIterator(close);
			cur = null;
			break;
		}
	}

	@Override
	public int currentSegment(float[] coords) {
		switch(state){
		case HEAD:
			coords[0] = (float)start.x;
			coords[1] = (float)start.y;
			return PathIterator.SEG_MOVETO;
		case BODY:
			return cur.awtCurSeg(coords);
		case TAIL:
			return PathIterator.SEG_CLOSE;
		}
		throw new Error("Unkown state");
	}

	@Override
	public int currentSegment(double[] coords) {
		throw new Error("Cur seg with doubles not implemented!");
	}

}
