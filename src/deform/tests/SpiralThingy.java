package deform.tests;

import static deform.Combinators.*;
import static deform.Combinators.fill;
import static deform.Combinators.quadTo;
import static deform.Combinators.transform;
import static deform.Combinators.*;
import static deform.Library.fillColor;
import static deform.Library.over;
import static deform.Library.path;
import static deform.Library.*;
import deform.BBox;
import deform.Color;
import deform.IFunction;
import deform.Texture;
import deform.Transform;
import deform.Vec;
import deform.paths.Path;
import deform.shapes.Shape;
import deform.texturedshape.TexturedShape;

import static deform.Library.image;
import static deform.Library.rectangle;
import static deform.Library.scale;
import static deform.Library.stroke;
import static deform.Library.sweep;
import static deform.Library.text;
import static deform.Library.tiledImage;
import static deform.Library.translate;

public class SpiralThingy extends DemoBase {

	TexturedShape s,q;
	Transform sineThing;
	
	Color gradient(double x) {
		double lx = x - Math.floor(x);
		Color red = color(255,0,0);
		Color yellow = color(255,255,0);
		if(lx < 0.5){
			return red.lerp(lx*2,yellow);
		} else {
			return yellow.lerp((lx-0.5)*2,red);
		}
	}
	
	@Override
	public void init() {
		Path p = funcPath(new IFunction() {
			
			final double a = 1;
			final double b = 0.1;
			
			@Override
			public BBox getBBox() {
				return BBox.everything;
			}
			
			@Override
			public Vec getAt(double t) {
				t = t * 6 * Math.PI + 6 * Math.PI;
				double e = 12 * a* Math.exp(b*t);
				return new Vec(e*Math.cos(t) + 500,e * Math.sin(t) + 500);
			}
		});
//		Path p = funcPath(new IFunction() {
//				final double a = 1;
//				final double b = 0.1;
//				
//				@Override
//				public BBox getBBox() {
//					return BBox.everything;
//				}
//				
//				@Override
//				public Vec getAt(double t) {
//					Vec p = new Vec(100*Math.cos(t*2.0*Math.PI) + 800,100*Math.sin(t*2.0*Math.PI) + 800);
////					System.out.printf("%f %s\n",t,p);
//					return p;
//				}
//		});
//		p = transform(translate(size.div(2)).compose(scale(12)),p);
//		s = fill(stroke(p,5),fillColor(0,0,0));
//		
		Shape r = close(path(0,0,lineTo(new Vec(1,0.5)),lineTo(new Vec(1,-0.5)),lineTo(new Vec(0,0))));
//		
		Texture trianglefill = 				new Texture() {
			@Override
			public Color sample(Vec p) {
				double lx = Math.abs(p.y*2)/p.x;
				return gradient(p.x*10).lerp(lx*lx, color(0,0,0));
			}
		};
		
		s = transform(translate(100,size.y/2).compose(scale(800)),fill(r,trianglefill));
		
		q = transform(scale(1,0.026),
				fill(r,trianglefill));
//				
//		s = sweep(q, p);
//		Shape def = transform(scale(1,2),text("Deform",200));
//		
//		TexturedShape m = fill(transform(translate(size.div(2).add(new Vec(-def.bbox.getWidthInt()/2,0)))
//				,def),fillColor(0,0,0,30));
////		s = over(s,m);
		sineThing = new Transform() {
			
			@Override
			public Vec to(Vec from) {
				double a = from.y/30;
				double off = Math.sin(a);
				return new Vec(from.x + off * 30, from.y);
			}
			
			@Override
			public Vec from(Vec to) {
				double a = to.y;
				double off = Math.sin(a);
				return new Vec(to.x - off*30, to.y/30);
			}
		};
//		s = transform(sineThing,s);
//	
////		s = fill(stroke(p, 5), fillColor(0,0,0));
	}

	@Override
	public void draw() {
//		TexturedShape s = transform(sineThing,this.s);
		draw(s);
	}

	public static void main(String[] argv){
		new SpiralThingy();
	}
	
}
