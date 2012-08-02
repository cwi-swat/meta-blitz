package deform.tests;

import deform.Color;
import deform.Texture;
import deform.Transform;
import deform.Vec;
import deform.paths.Path;
import deform.segments.LineTo;
import deform.shapes.Shape;
import deform.texturedshape.TexturedShape;
import static deform.Combinators.*;
import static deform.Library.*;

public class PaperRunningExample  extends DemoBase{

	public static final Color red= color(255,0,0);
	public static final Color green= color(0,255,0);
	
	public static void main(String[] argv){
		Shape triangle ;
		Shape head;
		Shape arrow;
		Texture hgradient;
		Transform toLeft;
		Path edge;
		TexturedShape ttriangle;
		TexturedShape connection;
		double lenght;
		edge = path(0,0,
				cubicTo(0,250,50,300,300,300),
				cubicTo(400,300,400,200,450,150));

//		edge = path(100,100,lineTo(700,700));
		Path tpath = path(1,0,lineTo(0,0.1),quadTo(0.1,0,0,-0.1),lineTo(1,0));
		triangle = close(tpath);
		head = transform ( translate (0.8 ,0).compose(scale (0.2,0.8)),triangle);
		arrow =   union(triangle,head);
		hgradient = new Texture() {
			@Override
			public Color sample(Vec point) {
				return red.lerp(point.x, green);
			}
		};
		toLeft =  new Transform() {
			
			@Override
			public Vec to(Vec from) {
				return vec(from.x - 100,from.y);
			}
			
			@Override
			public Vec from(Vec to) {
				return vec(to.x + 100 ,to.y);
			}
		};
		ttriangle = fill(arrow,fillColor(255,0,0));
		Vec trans = ttriangle.getBBox().getLeftUp().negate();
		trans = new Vec(trans.x,trans.y - ttriangle.getBBox().height()/2);
		Transform t = sweep(edge).compose(scale(length(edge)/ttriangle.getBBox().width()).compose(translate(trans)));
		System.out.println(t);
		System.out.println(t.from(new Vec(100,100)));
		new PaperRunningExample();
	}
	
	Shape triangle ;
	Shape head;
	Shape arrow;
	Texture hgradient;
	Transform toLeft;
	Path edge;
	TexturedShape ttriangle;
	TexturedShape connection;
	double lenght;
	
	@Override
	public void init() {
		edge = path(0,0,
				cubicTo(0,250,50,300,300,300),
				cubicTo(400,300,400,200,450,150));

//		edge = path(100,100,lineTo(700,700));
		Path tpath = path(1,0,lineTo(0,0.1),quadTo(0.1,0,0,-0.1),lineTo(1,0));
		triangle = close(tpath);
		head = transform ( translate (0.8 ,0).compose(scale (0.2,0.8)),triangle);
		arrow =   union(triangle,head);
		hgradient = new Texture() {
			@Override
			public Color sample(Vec point) {
				return red.lerp(point.x, green);
			}
		};
		toLeft =  new Transform() {
			
			@Override
			public Vec to(Vec from) {
				return vec(from.x - 100,from.y);
			}
			
			@Override
			public Vec from(Vec to) {
				return vec(to.x + 100 ,to.y);
			}
		};
		ttriangle = fill(arrow,hgradient);
		connection = sweep(ttriangle,edge);
		System.out.println(connection);
		System.out.println(transform(translate(10,10),connection));
	}

	@Override
	void draw() {
//		TexturedShape s = transform(translate(10,10),connection);
//		System.out.println(s);
//		draw(connection);
		draw(transform(translate(0,0),connection));
		
	}

	
	
}
