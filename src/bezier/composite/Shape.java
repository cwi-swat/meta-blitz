package bezier.composite;

import nogbeter.points.twod.Vec;
import bezier.MutableGraph;
import bezier.util.BBox;

public class Shape implements Area{
	
	public final Path outer;
	public final Shapes inner;
	
	public Shape(Path outer, Shapes inner) {
		this.outer = outer;
		this.inner = inner;
	}

	public Shape(MutableGraph<Path> sub) {
		outer = sub.getRoot();
		sub.removeNode(outer);
		inner = new Shapes(sub);
	}

	public boolean isInside(Vec p){
		return outer.isInside(p) && !inner.isInside(p);
	}

	@Override
	public BBox getBBox() {
		return outer.bbox;
	}
	
	
	public String toString(){
		StringBuffer b = new StringBuffer();
		b.append("Shape:outer\n");
		b.append(outer.toString());
		b.append("inner\n");
		b.append(inner.toString());
		b.append('\n');
		return b.toString();
	}
}