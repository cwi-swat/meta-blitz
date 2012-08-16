package deform.tests;

import static deform.Combinators.*;
import static deform.Library.*;

import deform.Color;
import deform.Library;
import deform.Library.ColorAndFraction;
import deform.Texture;
import deform.Vec;
import deform.paths.Path;
import deform.shapes.Shape;
import deform.tests.MandelBrot.MandelBrotTex;
import deform.texturedshape.TexturedShape;
import deform.transform.lenses.Lens;
import deform.transform.lenses.Norms;
import deform.transform.lenses.NumericToLens;
import deform.transform.lenses.Profiles;

public class BasicDemo extends DemoBase{


	public static void main(String[] argv){
		new BasicDemo();
	}
	
	
	@Override
	public void init(){
//		img = tiledImage("/home/ploeg/marble.jpg");
	}
	Texture img;
	@Override
	public void handleKeyStroke(char key){
		if(key == 'e'){
			awt = !awt;
		}
	}
	
	@Override
	void draw() {
		Shape rect = transform(translate(size.x/1.5,size.y/2).compose(scale(size.x/4)),new MandelBrotTex());
//		Shape rect = close(
//				path(lu, lineTo(ru), lineTo(rd), lineTo(ld),lineTo(lu)));
//		rect = stroke(rect,30);

		Texture multip = transform(scale(250).compose(translate(1,1)),radialGradient(new ColorAndFraction(0,color(255,0,0)),
								new ColorAndFraction(0.7,color(0,255,0)),
								new ColorAndFraction(1.0,color(0,0,255))));
		Shape rect = transform(scale(50),text("Atze!"));
//		Shape rect = transform(translate(400,400).compose(scale(400)),circle());
//		System.out.println(rect);
		Path p = path(0,h/2, quadTo(mouse,new Vec(w,h/2)));
		TexturedShape s =fill(rect //, transform(scale(100),horGradient(new Color(255,0,0,255),new Color(0,255,0,255))));
//				, img);
//				,fillColor(color(255,0,0)));
				,multip);
		
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
		
		s = transform(sweep(p),s);
		new NumericToLens(Norms.circlerect,Profiles.gauss,
				mouse,c.getZoom(),200,400);
//		s = transform(coneLens(mouse, 100 + wheel),s);
//		s = transform(fisheye(mouse,Math.abs(wheel)/100 , 200), s);
		TexturedShape line = fill(stroke(p,10),fillColor(0,255,0));
		s = over(line,s);
//		s = transform(rotate(wheel/100*Math.PI),s);
//		System.out.println(translate(new Vec(w/2,h/2)).compose(rotate(wheel/100*Math.PI).compose(translate(new Vec(-w/2,-h/2)))));
//		s = transform(translate(mouse).compose(rotate(wheel/100*Math.PI).compose(translate(new Vec(-w/2,-h/2)))),s);
		draw(s);
		
	}




}
