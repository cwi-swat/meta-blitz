package bezier.paths.util;

import java.awt.geom.PathIterator;
import java.util.Iterator;

import bezier.paths.IConnectedPath;

public class AWTPathIterator implements PathIterator{

	Iterator<IConnectedPath> connectedIterator;
	
	public AWTPathIterator(Iterator<IConnectedPath> connectedIterator) {
		this.connectedIterator = connectedIterator;
	}
	
	@Override
	public int getWindingRule() {
		return PathIterator.WIND_EVEN_ODD;
	}

	@Override
	public boolean isDone() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void next() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int currentSegment(float[] coords) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int currentSegment(double[] coords) {
		// TODO Auto-generated method stub
		return 0;
	}

}
