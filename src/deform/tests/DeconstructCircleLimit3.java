package deform.tests;

import deform.Texture;
import deform.Transform;
import deform.Vec;
import deform.shapes.Shape;
import deform.texturedshape.TexturedShape;

import static deform.Combinators.*;
import static deform.Library.*;

public class DeconstructCircleLimit3 extends DemoBase{
	public static Vec scenter = vec(700,700);
	public static Vec fcenter = vec(372,367);

	public static Vec center = vec(700,700);
	public static double factor = 1/256.0;
	public static double rfactor = 0.005;
	public static double mfac = 1.0 / (Math.PI/2.0)*372;
	TexturedShape circleLimit3;
	Transform inverHyperbolic;
	Shape triangles;
	
	public static void main(String[] argv){
		new DeconstructCircleLimit3();
	}
	
	@Override
	public void init() {
//		Shape triangle = close(path(-0.5,-0.5,lineTo(0.5,-0.5),lineTo(0.5,0.5),lineTo(-0.5,-0.5)));
//		Shape[] ts = new Shape[10 * 10];
//		triangles = triangle;
//		int q = 0;
//		for(int i = 0; i < 10 ; i++){
//			for(int j = 0; j < 10 ; j++){
//					ts[q++] = transform(translate(i,j),triangle);
//			}
//		}
//		triangles = set(ts);
		Texture circleLimit3t = transform(translate(700-fcenter.x,700 - fcenter.y),
				image("/home/ploeg/circlelimit3.jpg"));
		circleLimit3 =fill(close(path(0,0, lineTo(1400,0), lineTo(1400,1400),
				lineTo(0,1400),lineTo(0,0))),circleLimit3t);
		 inverHyperbolic = new Transform() {
			


//			public final double radiusFac = (1.0/372.0 * Math.PI);
			
			@Override
			public Vec to(Vec from) {
				return from;
//				Vec toCenter = from.sub(center);
//				double dist = toCenter.norm();
//				toCenter = toCenter.div(dist);
//				double n = 1/(dist+3) * mfac ;
////				System.out.println(dist);
////				double n = Math.atan(rfactor * dist  )/Math.PI/2  ;
////				n*=n;
////				n*=mfac;
//				return toCenter.mul(n).add(center);
			}
			
			@Override
			public Vec from(Vec to) {
				Vec toCenter = to.sub(center);
				double dist = toCenter.norm();
				toCenter = toCenter.div(dist);
				double n = Math.atan(dist*dist *rfactor) * mfac;
//				System.out.println(dist);
//				double n = Math.atan(rfactor * dist  )/Math.PI/2  ;
//				n*=n;
//				n*=mfac;
				return toCenter.mul(n).add(center);
//				
			}
		};
	}

	@Override
	void draw() {
		center = scenter.add(mouse.sub(scenter).div(100));
		rfactor = factor * (wheel/200 + 1);
		draw(transform(inverHyperbolic,circleLimit3));
//		draw(transform(inverHyperbolic,fill(transform(scale(100),triangles),fillColor(255,255,255))));
		
	}

}
