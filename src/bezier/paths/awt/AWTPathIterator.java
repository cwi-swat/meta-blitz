package bezier.paths.awt;

import java.awt.geom.PathIterator;
import java.util.Iterator;

import bezier.paths.ConnectedPath;
import bezier.paths.Path;
import bezier.paths.simple.SimplePath;
import bezier.points.Vec;

public class AWTPathIterator implements PathIterator{

	static enum State{
		HEAD,
		BODY,
		TAIL,
	}
	
	Iterator<ConnectedPath> connectedPaths;
	Iterator<SimplePath> current;
	SimplePath now;
	Vec start;
	State state;
	
	public AWTPathIterator(Iterator<ConnectedPath> connectedPaths) {
		this.connectedPaths = connectedPaths;
		state = State.TAIL;
	}

	public void setCurrent() {
		if(connectedPaths.hasNext()){
			Path connected = (Path)connectedPaths.next();
//			System.out.printf("Setting connected %s!\n",connected);
			current = connected.getSimpleIterator();

			now = current.next();

		}
	}

	@Override
	public int getWindingRule() {
		return PathIterator.WIND_EVEN_ODD;
	}

	@Override
	public boolean isDone() {
		return state == State.TAIL && !connectedPaths.hasNext();
	}

	@Override
	public void next() {
		switch(state){
		case TAIL : state = State.HEAD; setCurrent(); break;
		case HEAD : state = State.BODY; break;
		case BODY : if(current.hasNext()){
//						System.out.printf("Setting current!\n");
						now = current.next();
					} else {
						state = State.TAIL;
					} break;
		}
	}

	@Override
	public int currentSegment(float[] coords) {
		switch(state){
		case HEAD :
			Vec start = now.getSimple().getStartPoint();
			coords[0] = (float)start.x;
			coords[1] = (float)start.y;
			this.start = start;
			return PathIterator.SEG_MOVETO;
		case BODY : return now.currentSegmentAWT(coords);
		case TAIL : return PathIterator.SEG_CLOSE;
		}
		throw new Error("Unkonw state!");
	}

	@Override
	public int currentSegment(double[] coords) {
		throw new Error("Not supported!");
	}

}
