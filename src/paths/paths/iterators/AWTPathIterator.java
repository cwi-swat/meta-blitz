package paths.paths.iterators;

import java.awt.geom.PathIterator;

import deform.Vec;

import paths.paths.paths.Path;
import paths.paths.paths.simple.SimplePath;
import util.DebugPrint;

public class AWTPathIterator implements PathIterator {

	static enum State {
		HEAD, BODY, TAIL,
	}

	State state;
	final int x, y;
	final ClosedPathIterator closed;
	Vec start;
	SimplePathIterator curSimple;
	SimplePath cur;

	public AWTPathIterator(Path root, int x, int y) {
		closed = new ClosedPathIterator(root);
		cur = null;
		state = State.TAIL;
		this.x = x;
		this.y = y;
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
		switch (state) {
		case HEAD:
			state = State.BODY;
			cur = curSimple.next();
			break;
		case BODY:
			if (curSimple.hasNext()) {
				cur = curSimple.next();
			} else {
				state = State.TAIL;
			}
			break;
		case TAIL:
			state = State.HEAD;
			Path close = closed.next();
			start = close.getAt(close.minPathIndex());
			curSimple = new SimplePathIterator(close);
			cur = null;
			break;
		}
	}

	@Override
	public int currentSegment(float[] coords) {
		switch (state) {
		case HEAD:
			coords[0] = (float) start.x - x;
			coords[1] = (float) start.y - y;
			return PathIterator.SEG_MOVETO;
		case BODY:
			return cur.awtCurSeg(coords, x, y);
		case TAIL:
			return PathIterator.SEG_CLOSE;
		}
		throw new Error("Unkown state");
	}

	@Override
	public int currentSegment(double[] coords) {
		switch (state) {
		case HEAD:
			coords[0] = start.x - x;
			coords[1] = start.y - y;
			return PathIterator.SEG_MOVETO;
		case BODY:
			float[] coords2 = new float[coords.length];
			int res = cur.awtCurSeg(coords2, x, y);
			for (int i = 0; i < coords.length; i++) {
				coords[i] = (float) coords2[i];
			}
			return res;
		case TAIL:
			return PathIterator.SEG_CLOSE;
		}
		throw new Error("Unkown state");
	}

}
