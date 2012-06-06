package paths.paths.iterators;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Stack;

import paths.paths.paths.Path;
import paths.paths.paths.simple.SimplePath;


public class PathIterator<P extends Path> implements Iterator<Path>{

	static class PathChild{
		public final Path p;
		public int i;
		
		public PathChild(Path p) {
			this.p = p;
			this.i = 0;
		}
	}
	
	final Stack<PathChild> paths;
	final PathSelect select;
	
	public PathIterator(PathSelect select, Path root) {
		this.paths = new Stack<PathChild>();
		paths.push(new PathChild(root));
		this.select = select;
		toNext();
	}

	
	@Override
	public boolean hasNext() {
		return !paths.isEmpty();
	}
	
	private void toNext(){
		while(!paths.isEmpty() && !select.select(paths.peek().p)){
			PathChild c = paths.peek();
			if(c.i == c.p.nrChildren()){
				paths.pop();
			} else {
				paths.push(new PathChild(c.p.getChild(c.i)));
				c.i++;
			}
		}
	}

	@Override
	public P next() {
		P res =  (P)paths.pop().p;
		toNext();
		return res;
	}

	@Override
	public void remove() {
		throw new Error("Not implemented!");
		
	}

}
