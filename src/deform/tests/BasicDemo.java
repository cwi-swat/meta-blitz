package deform.tests;

import static deform.Combinators.*;
import static deform.Library.*;

import java.util.ArrayList;
import java.util.List;

import deform.BBox;
import deform.Color;
import deform.Texture;
import deform.Vec;
import deform.segments.SegPath;
import deform.shapes.Shape;
import deform.texturedshape.TexturedShape;
import deform.transform.affine.IdentityTransform;

public class BasicDemo extends DemoBase{

	public static void main(String[] argv){
		new BasicDemo();
	}
	
	@Override
	void draw() {
		final Vec lu = new Vec(100,100);
		final double w = 500;
		final double h = 500;
		Vec ru = new Vec(lu.x + w, lu.y);
		Vec ld = new Vec(lu.x , lu.y + h);
		Vec rd = new Vec(lu.x + w , lu.y + h);
		Shape rect = close(
				path(lu, lineTo(ru), lineTo(rd), lineTo(ld),lineTo(lu)));
		rect = stroke(rect,30);
		List<SegPath> res = new ArrayList<SegPath>();
		TexturedShape s =fill(rect,fillColor(new Color(255,0,0,255)));
//				,new Texture() {
//			
//			@Override
//			public Color sample(Vec point) {
////				return new Color(255,0,0,255);
//				
//				if((int)(point.x /10) % 2 == 0 ^  (int)(point.y /10) % 2 ==0){
//					double frac = (point.x - lu.x) /w;
//					return new Color(255,0,0,255).lerp(frac, new Color(0,255,0,255));
//				} else {
//					double frac = (point.y - lu.y) /h;
//					return new Color(255,0,0,255).lerp(1.0 - frac, new Color(0,255,0,255));
//				}
//				
//			}
//		});
//		s = transform(fisheye(mouse,1+ Math.abs(wheel)/100, 200), s);
		s = transform(translate(mouse),s);
		draw(s);
		
	}

}
