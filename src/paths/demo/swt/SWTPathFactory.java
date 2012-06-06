package nogbeter.demo.swt;

import java.awt.geom.PathIterator;

import org.eclipse.swt.graphics.Device;
import org.eclipse.swt.graphics.Path;

import nogbeter.paths.iterators.AWTPathIterator;

public class SWTPathFactory {

	public static final void makeSWTPath(Path res, nogbeter.paths.Path p){
		AWTPathIterator it = new AWTPathIterator(p);
		float[] coords = new float[6];
		while(!it.isDone()){
			switch(it.currentSegment(coords)){
			case PathIterator.SEG_MOVETO: 
				res.moveTo(coords[0], coords[1]); break; 
			case PathIterator.SEG_LINETO:
				res.lineTo(coords[0], coords[1]); break;
			case PathIterator.SEG_QUADTO: 
				res.quadTo(coords[0], coords[1], coords[2], coords[3]); break;
			case PathIterator.SEG_CUBICTO: 
				res.cubicTo(coords[0], coords[1], coords[2], coords[3], coords[4], coords[5]); break;
			case PathIterator.SEG_CLOSE:
				res.close(); break;
			}
			it.next();
		}
	}
	
}
