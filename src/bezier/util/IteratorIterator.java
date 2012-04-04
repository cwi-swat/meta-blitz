package bezier.util;

import java.util.Iterator;

public class IteratorIterator<A,B>  implements Iterator<B>{

	Iterator<A> base;
	Iterator<B> current;
	
	public IteratorIterator(Iterator<A> baseIt, IteratorGetter<A,B> get ) {
		base = baseIt;
		current = null;
	}
	
	@Override
	public boolean hasNext() {
		return (current == null && base.hasNext()) || current.hasNext();
	}

	@Override
	public B next() {
		while(current == null || !current.hasNext()){
			ret
		}
	}

	@Override
	public void remove() {
		throw new Error("Not implemented!!");
		
	}

}
