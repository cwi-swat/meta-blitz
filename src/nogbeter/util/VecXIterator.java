package nogbeter.util;

import java.util.Iterator;

import bezier.points.Vec;

public class VecXIterator implements Iterator<Double>{

	final Iterator<Vec> vIt;
	
	public VecXIterator(Iterator<Vec> vIt) {
		this.vIt = vIt;
	}

	@Override
	public boolean hasNext() {
		return vIt.hasNext();
	}

	@Override
	public Double next() {
		return vIt.next().x;
	}

	@Override
	public void remove() {
		vIt.remove();
	}
	
	
	
}
