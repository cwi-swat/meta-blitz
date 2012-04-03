package bezier.paths.awt;

import java.awt.geom.PathIterator;
import java.util.Stack;

import bezier.paths.Path;


public class AWTPathIterator implements PathIterator{

	Stack<IAWTPath> stack;
	IAWTLeafPath current;
	
	public AWTPathIterator(Path root) {
		stack = new Stack<IAWTPath>();
	}
	
	@Override
	public int getWindingRule() {
		return PathIterator.WIND_EVEN_ODD;
	}

	@Override
	public boolean isDone() {
		return stack.isEmpty();
	}

	@Override
	public void next() {
		while(!stack.peek().isLeaf()){
			IAWTNodePath p = stack.pop().getNode();
			p.pushChildren(stack);
		}
		current = stack.pop().getLeaf();
	}
	@Override
	public int currentSegment(float[] coords) {
		return current.currentSegmentAWT(coords);
	}

	@Override
	public int currentSegment(double[] coords) {
		throw new Error("Double current seg not implemented! Because AWT doesn't actually use it... until now..");
	}

}
