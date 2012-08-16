package deform.tests.vszvtm;

import static deform.Combinators.fill;
import static deform.Combinators.intersection;
import static deform.Combinators.memo;
import static deform.Combinators.minus;
import static deform.Combinators.transform;
import static deform.Library.image;
import static deform.Library.over;
import static deform.Library.rectangle;
import static deform.Library.scale;
import static deform.Library.translate;
import deform.Transform;
import deform.Vec;
import deform.shapes.Shape;
import deform.texturedshape.TexturedShape;
import deform.transform.lenses.LinearLens;
import deform.transform.lenses.Norms;

public class ImageTest extends MeasureBase{
	TexturedShape s;
	
	static double zooml ;
	
	
	
	@Override
	public void init(){
		s = fill(transform(translate(1,1).compose(scale(size.x,size.y)),rectangle()),image("/home/ploeg/landscape.jpg"));
	}
	
	@Override
	public void draw() {
		Vec center = size.div(2);
		TexturedShape b = fisheyeNew(center, this.zooml, 100, 200, s);
		draw(b);
		
	}
	
	TexturedShape fisheyeNew(Vec center, double zoom, double inner, double outer, TexturedShape onto){
		if(zoom == 1){
			return onto;
		}
		Shape outerShape = transform(scale(outer),rectangle());
		Shape border = transform(translate(center),outerShape);
		Transform t = new LinearLens(Norms.circle, center, zoom, inner, outer);
		TexturedShape borderArea = intersection(transform(
				t,onto),border);
		return over(minus(onto,border),borderArea);
	}
	
	public static void main(String[] argv){
		zooml = Double.parseDouble(argv[0]);
		new ImageTest();
	}

	@Override
	public double getZoom() {
		return this.zooml;
	}
}
