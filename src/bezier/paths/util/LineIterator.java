package bezier.paths.util;

import java.util.Iterator;
import java.util.Stack;

import bezier.paths.Path;
import bezier.paths.leaf.Line;


public class LineIterator implements Iterator<Line>{

	Stack<Path> stack;
	
	public LineIterator(Path root) {
		stack = new Stack<Path>();
		stack.push(root);
	}

	@Override
	public boolean hasNext() {
		return !stack.isEmpty();
	}

	@Override
	public Line next() {
		while(!stack.peek().isLine()){
			Path p = stack.pop();
			stack.push(p.getRightSimpler());
			stack.push(p.getLeftSimpler());
		}
		return stack.pop().getLine();
	}

	@Override
	public void remove() {
		throw new Error("Remove not implemented!");
		
	}
	
	
	
}
