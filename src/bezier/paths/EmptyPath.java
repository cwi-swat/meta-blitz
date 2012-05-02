package bezier.paths;

import java.util.Iterator;

import nogbeter.points.twod.Vec;
import nogbeter.transform.ITransform;

import bezier.paths.compound.ICompoundPath;
import bezier.paths.simple.Line;
import bezier.paths.simple.SimplePath;
import bezier.paths.util.IPathSelector;
import bezier.paths.util.PathParameter;
import bezier.util.BBox;
import bezier.util.STuple;

public class EmptyPath extends ConnectedPath {

	public EmptyPath(){}
	
	public boolean isClosed(){
		return true;
	}
	
	@Override
	public boolean isInside(Vec p) {
		return false;
	}
	
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
	public ConnectedPath getConnected() {
		
		return null;
	}

	@Override
	public ICompoundPath getCompound() {
		
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

	
	public boolean isEmpty(){
		return true;
	}

	@Override
	public Path getSubPath(PathParameter start, PathParameter end) {
		throw new Error("Cannot get subpath of emtpy path!");
	}

	@Override
	public Vec getStartPoint() {
		throw new Error("Empty path!");
	}

	@Override
	public Vec getEndPoint() {
		throw new Error("Empty path!");
	}

	@Override
	public Vec getAt(double t) {
		throw new Error("Empty path!");
	}

	@Override
	public Vec getTangentAt(double t) {
		throw new Error("Empty path!");
	}

	@Override
	public ConnectedPath reverse() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ConnectedPath getWithAdjustedStartPoint(Vec newStart) {
		throw new Error("Empty path!");
	}

	@Override
	public ConnectedPath getSubPath(double start, double end) {
		throw new Error("Empty path!");
	}

	@Override
	public int nrBelow(Vec p) {
		return 0;
	}
	
	
}
