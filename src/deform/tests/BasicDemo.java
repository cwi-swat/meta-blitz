package deform.tests;

import static deform.Combinators.*;
import static deform.Library.*;
import deform.Color;
import deform.Texture;
import deform.Vec;
import deform.shapes.Shape;
import deform.texturedshape.TexturedShape;

public class BasicDemo extends DemoBase{

	public static void main(String[] argv){
		new BasicDemo();
	}
	
	@Override
	void draw() {
		final Vec lu = new Vec(100,100);
		final double w = 500;
		double h = 500;
		Vec ru = new Vec(lu.x + w, lu.y);
		Vec ld = new Vec(lu.x , lu.y + h);
		Vec rd = new Vec(lu.x + w , lu.y + h);
		Shape rect = close(
				path(lu, lineTo(ru), lineTo(rd), lineTo(ld),lineTo(lu)));
		TexturedShape s =fill(rect,new Texture() {
			
			@Override
			public Color sample(Vec point) {
//				return new Color(255,0,0,255);
//				double frac = (point.x - lu.x) /w;
				if((int)(point.x /10) % 2 == 0 ^  (int)(point.y /10) % 2 ==0){
					return new Color(255,0,0,255);
				} else {
					return new Color(0,255,0,255);
				}
//				return new Color(255,0,0,255).lerp(frac, new Color(0,255,0,255));
			}
		});
		s = transform(fisheye(mouse,2.1, 200), s);
		draw(s);
		
	}

}
