package bezier.paths;

import java.util.Iterator;

import bezier.paths.compound.CompoundPath;
import bezier.paths.simple.Line;
import bezier.paths.simple.SimplePath;
import bezier.paths.util.IPathSelector;
import bezier.paths.util.ITransform;
import bezier.paths.util.PathIterator;
import bezier.paths.util.PathParameter;
import bezier.points.Vec;
import bezier.util.BBox;
import bezier.util.STuple;

public class EmptyPath extends Path {

	public EmptyPath(){}
	
	@Override
	public BBox makeBBox() {
		return new BBox(0, 0, 0, 0);
	}

	@Override
	public boolean isLine() {
		
		return false;
	}

	@Override
	public Line getLine() {
		
		return null;
	}

	@Override
	public boolean isSimple() {
		
		return false;
	}

	@Override
	public SimplePath getSimple() {
		
		return null;
	}

	@Override
	public boolean isConnected() {
		
		return false;
	}

	@Override
	public IConnectedPath getConnected() {
		
		return null;
	}

	@Override
	public CompoundPath getCompound() {
		
		return null;
	}

	@Override
	public Vec getAt(PathParameter t) {
		
		return null;
	}

	@Override
	public Vec getTangentAt(PathParameter t) {
		
		return null;
	}

	@Override
	public Path transform(ITransform m) {
		
		return this;
	}

	@Override
	public STuple<Path> splitSimpler() {
		
		return null;
	}
	
	<T> Iterator<T> getIterator(IPathSelector<T> selector){
		return new Iterator<T>(){

			@Override
			public boolean hasNext() {
				return false;
			}

			@Override
			public T next() {
				return null;
			}

			@Override
			public void remove() {
			}
			
		};
	}

}
