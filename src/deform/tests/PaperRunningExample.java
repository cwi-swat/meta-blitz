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
				cubicTo(550,300,600,250,600,0));

//		edge = path(100,100,lineTo(700,700));
		Path tpath = path(0,0.1,lineTo(1,0),lineTo(0,-0.1),quadTo(0.05,0,0,0.1));
		triangle = close(tpath);
		head = transform ( translate (0.7 ,0).compose(scale (0.3,0.8)),triangle);
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
	}

	@Override
	void draw() {
//		TexturedShape base = transform(translate(300,300).compose(scale(100)),ttriangle);
//		draw(base);
		TexturedShape newvis = transform(fisheye(mouse,Math.abs(wheel)/100 , 200), connection);
		newvis = over(fill(stroke(edge,10),fillColor(255,255,255)),newvis);
		draw(newvis);
		
	}

}
