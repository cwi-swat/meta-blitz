package nogbeter.points.oned;

import java.util.Iterator;

import nogbeter.points.twod.Vec;


public class VecYIterator implements Iterator<Double>{

	final Iterator<Vec> vIt;
	
	public VecYIterator(Iterator<Vec> vIt) {
		this.vIt = vIt;
	}

	@Override
	public boolean hasNext() {
		return vIt.hasNext();
	}

	@Override
	public Double next() {
		return vIt.next().y;
	}

	@Override
	public void remove() {
		vIt.remove();
	}
	
	
	
}
