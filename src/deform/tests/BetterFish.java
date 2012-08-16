package deform.tests;

import static deform.Combinators.color;
import static deform.Combinators.fill;
import static deform.Combinators.quadTo;
import static deform.Combinators.transform;
import static deform.Combinators.*;
import static deform.Library.fillColor;
import static deform.Library.over;
import static deform.Library.path;
import static deform.Library.*;

import static deform.Library.image;
import static deform.Library.rectangle;
import static deform.Library.scale;
import static deform.Library.stroke;
import static deform.Library.sweep;
import static deform.Library.text;
import static deform.Library.tiledImage;
import static deform.Library.translate;
import deform.Color;
import deform.ColorCombine;
import deform.Combinators;
import deform.Texture;
import deform.Transform;
import deform.Vec;
import deform.Library.ColorAndFraction;
import deform.paths.Path;
import deform.shapes.Shape;
import deform.texturedshape.MinusTexturedShape;
import deform.texturedshape.TexturedShape;
import deform.transform.lenses.Lens;
import deform.transform.lenses.LinearLens;
import deform.transform.lenses.Norms;
import deform.transform.lenses.NumericToLens;
import deform.transform.lenses.Profiles;

public class BetterFish extends DemoBase{


	public static boolean awt = true;

	public static void main(String[] argv){
		new BetterFish();
	}
	Shape r, rect;
	Texture multip;
	TexturedShape s;
	
	public static String txt = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Aliquam sed turpis sed felis vestibulum pretium.\nPraesent at elit vitae odio eleifend tristique in sit amet sem. In cursus condimentum enim eget\nmalesuada. Donec iaculis, velit non tempor egestas, tellus lacus viverra orci, sed ultricies lorem magna";
//public static String txt = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Aliquam sed turpis sed felis vestibulum pretium.\nPraesent at elit vitae odio eleifend tristique in sit amet sem. In cursus condimentum enim eget\nmalesuada. Donec iaculis, velit non tempor egestas, tellus lacus viverra orci, sed ultricies lorem magna";
	static double zooml ;
	
	
	
	@Override
	public void init(){
		Shape r = text(txt,30);
		r = transform(translate(0,470.8203125),r);
//		 System.out.println(-r.bbox.yInterval.middle()+size.y/2-20);
		s = fill(r,fillColor(0,0,0));
		s = over(
			fill(transform(translate(1,1).compose(scale(size.x,size.y)),rectangle()),image("/home/ploeg/landscape.jpg")),s);
	}
	Texture img;
	@Override
	public void handleKeyStroke(char key){
		if(key == 'e'){
			awt = !awt;
		}
	}
	
	@Override
	public void draw() {
//		Shape rect = close(
//				path(lu, lineTo(ru), lineTo(rd), lineTo(ld),lineTo(lu)));
//		rect = stroke(rect,30);


		
//		
//				,new Texture() {
//			
//			@Override
//			public Color sample(Vec point) {
////				return new Color(255,0,0,255);
//				
//				if((int)(point.x /10) % 2 == 0 ^  (int)(point.y /10) % 2 ==0){
//					double frac = (point.x - lu.x) /w;
//					return color(255,0,0).lerp(frac, color(0,255,0));
//				} else {
//					double frac = (point.y - lu.y) /h;
//					return color(0,0,255).lerp(1.0 - frac, color(255,255,0));
//				}
//				
//			}
//		});
//		s = over(fill(stroke(rect,15),fillColor(color(0,140,0,128))),s);
//		double d = Math.abs(wheel)/100  == 0 ? 1 : Math.abs(wheel)/100 ;
//		
		
//		s = transform(sweep(p),s);
//		s = transform(new BasicLens(mouse, 5,100,200),s);
//		s = over(s,fill(transform(translate(mouse).compose(scale(100)),circle()),fillColor(255,0,0,100)));
//		s = over(s,fill(transform(translate(mouse).compose(scale(200)),circle()),fillColor(255,0,0,100)));
//		TexturedShape line = fill(stroke(p,10),fillColor(0,255,0));
//		s = over(line,s);
//		draw(transform(translate(mouse).compose(scale(5)),fill(Combinators.set(circle(),transform(scale(5),circle())),fillColor(255,0,0))));
//		Texture rad = radialGradient(new ColorAndFraction[] { new ColorAndFraction(0, color(255,0,0)),  new ColorAndFraction(1, color(0,255,0))});
//		Texture h = horGradient(new ColorAndFraction[] { new ColorAndFraction(0, color(255,255,255)),  new ColorAndFraction(1, color(0,0,0))});
//		TexturedShape b =transform(
//				new LinearLens(Norms.circle,
//				mouse,2, 100, 200),s);
		
		TexturedShape b = fisheyeNew(size.div(2), 2.5, 100, 200, s);
//		TexturedShape r = transform(translate(300,300).compose(scale(100)),fill(circle(),rad));
//		TexturedShape q = transform(translate(400,300).compose(scale(100)),fill(circle(),h));
//		TexturedShape z = combine(new ColorCombine() {
//			
//			@Override
//			public Color combine(Color a, Color b) {
//				return a.mul(b.r);
//			}
//		},r,q);
////		draw(z);
//		draw(z);
		draw(b);
		
	}
	
	TexturedShape fisheyeNew(Vec center, double zoom, double inner, double outer, TexturedShape onto){
//		Shape innerShape = transform(scale(inner/Math.sqrt(2)),rectangle());
		Shape outerShape = transform(scale(outer),rectangle());
		Shape border = transform(translate(center),outerShape);
//		System.out.println(border);
//		return fill(innerShape,fillColor(255,0,0));\
		
//		TexturedShape zoomedArea = intersection(transform(scale(zoom).compose(translate(mouse.negate())),onto);
//		zoomedArea = transform(translate(mouse),intersection(zoomedArea,innerShape));
		Transform t = new NumericToLens(Norms.circle, Profiles.gauss, center, zoom, inner, outer);
		TexturedShape borderArea = intersection(transform(
				t,onto),border);
		
//		return borderArea;
//		return new MinusTexturedShape(onto,transform(translate(mouse),outerShape));
//		return borderArea;
//		return transform(new BasicLens(mouse,zoom,inner,outer),onto);
//		TexturedShape rest = minus(onto,transform(translate(mouse),outerShape));
//		return over(rest,borderArea);
//		return minus(onto,border);
//		return zoomedArea;
//		return over(minus(onto,border),borderArea,zoomedArea);
//		return transform(t,onto);
		return over(minus(onto,border),borderArea);
	}


	
}
