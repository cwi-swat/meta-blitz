package bezier.paths.util;

import java.util.Iterator;
import java.util.Stack;

import bezier.paths.Path;


public class PathIterator<T> implements Iterator<T>{

	Stack<Path> stack;
	 IPathSelector<T> selector;
	
	public PathIterator(Path root, IPathSelector<T> selector) {
		stack = new Stack<Path>();
		stack.push(root);
//		System.out.printf("Root is%s\n",root);
		this.selector = selector;
	}

	@Override
	public boolean hasNext() {
		return !stack.isEmpty();
	}

	@Override
	public T next() {
		while(selector.select(stack.peek()) == null){
			Path p = stack.pop();
			p.expand();
//			System.out.printf("adding %s\n",p.getRightSimpler());
//			System.out.printf("adding %s\n",p.getLeftSimpler());
			stack.push(p.getRightSimpler());
			stack.push(p.getLeftSimpler());
		}
		T s = selector.select(stack.pop());;

		return s;
	}

	@Override
	public void remove() {
		throw new Error("Remove not implemented!");
		
	}
	
	
	
}
