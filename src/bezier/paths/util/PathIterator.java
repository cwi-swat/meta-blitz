package bezier.paths.util;

import java.util.Iterator;
import java.util.Stack;

import bezier.paths.Path;
import bezier.paths.simple.Line;


public class PathIterator<T> implements Iterator<T>{

	Stack<Path> stack;
	 IPathSelector<T> selector;
	
	public PathIterator(Path root, IPathSelector<T> selector) {
		stack = new Stack<Path>();
		stack.push(root);
	}

	@Override
	public boolean hasNext() {
		return !stack.isEmpty();
	}

	@Override
	public T next() {
		while(selector.select(stack.peek()) == null){
			Path p = stack.pop();
			stack.push(p.getRightSimpler());
			stack.push(p.getLeftSimpler());
		}
		return selector.select(stack.pop());
	}

	@Override
	public void remove() {
		throw new Error("Remove not implemented!");
		
	}
	
	
	
}
