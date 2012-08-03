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
	
	public static String txt = "Lorem ipsum dolor sit amet, consectetur adipiscing elit.\n Aliquam ultrices quam rhoncus diam sollicitudin at posuere nibh\n consequat. Quisque laoreet consequat diam ac pharetra.\n Curabitur nisl enim, lacinia at placerat a, pretium eu felis.\n Cras feugiat lobortis porttitor. Suspendisse ante lectus,\n hendrerit ullamcorper lacinia sed, porta quis augue. Vestibulum\n tristique sagittis nisl, quis tempus diam tincidunt id.\n Praesent facilisis, urna non accumsan euismod, elit nunc\n pharetra velit, quis sodales nisi ligula sit amet eros.\n Maecenas condimentum viverra lacus, non feugiat nibh fermentum\n at. Pellentesque ac enim dolor. Duis a lorem ante. Curabitur\n feugiat nisl eu leo tristique pharetra. Etiam in leo eu erat interdum\n pellentesque ut non erat.\n Donec fermentum sapien eget\n risus congue a feugiat risus luctus. Sed sollicitudin velit\n ut tellus feugiat posuere.\n\nDuis urna elit, viverra quis scelerisque nec, interdum commodo\n ligula. Fusce blandit mollis metus et molestie. Cras rutrum\n ultrices diam volutpat viverra. Mauris dapibus eros ut\n sapien convallis sit amet tempor elit iaculis. Sed sit amet\n dolor dui. Phasellus elementum condimentum lacus fringilla\n convallis. Curabitur velit metus, iaculis in pulvinar eget,\n tincidunt sit amet velit. Fusce et nisl nunc. Vestibulum\n suscipit, lacus vel blandit pretium, tortor orci sodales enim,\n sit amet aliquet est dui sit amet lacus. Fusce pellentesque\n lacus sit amet mauris facilisis sollicitudin. In convallis\n nisl vitae libero mattis blandit.";
	
	@Override
	public void init(){
		 r = text(txt,11);
		rect = transform(translate(10,100).compose(scale(2)),
				r);
		multip = 
				transform(scale(250).compose(translate(1,1)),radialGradient(new ColorAndFraction(0,color(255,0,0)),
						new ColorAndFraction(0.7,color(0,255,0)),
						new ColorAndFraction(1.0,color(0,0,255))));
		s = memo(fill(rect,multip));
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
		Texture rad = radialGradient(new ColorAndFraction[] { new ColorAndFraction(0, color(255,0,0)),  new ColorAndFraction(1, color(0,255,0))});
		Texture h = horGradient(new ColorAndFraction[] { new ColorAndFraction(0, color(255,255,255)),  new ColorAndFraction(1, color(0,0,0))});
		TexturedShape b = fisheyeNew(mouse, 1 + wheel /100, 100, 200, s);
		TexturedShape r = transform(translate(300,300).compose(scale(100)),fill(circle(),rad));
		TexturedShape q = transform(translate(400,300).compose(scale(100)),fill(circle(),h));
		TexturedShape z = combine(new ColorCombine() {
			
			@Override
			public Color combine(Color a, Color b) {
				return a.mul(b.r);
			}
		},r,q);
//		draw(z);
//		draw(z);
		draw(b);
		
	}
	
	TexturedShape fisheyeNew(Vec center, double zoom, double inner, double outer, TexturedShape onto){
		Shape innerShape = transform(scale(inner/Math.sqrt(2)),rectangle());
		Shape outerShape = transform(scale(outer),rectangle());
		Shape border = transform(translate(mouse),outerShape);
//		System.out.println(border);
//		return fill(innerShape,fillColor(255,0,0));\
		
//		TexturedShape zoomedArea = transform(scale(zoom).compose(translate(mouse.negate())),onto);
//		zoomedArea = transform(translate(mouse),intersection(zoomedArea,innerShape));
		TexturedShape borderArea = intersection(transform(
				new NumericToLens(Norms.circlerect,Profiles.gauss,
				mouse,zoom,inner,outer),onto),border);
		
//		return borderArea;
//		return new MinusTexturedShape(onto,transform(translate(mouse),outerShape));
//		return borderArea;
//		return transform(new BasicLens(mouse,zoom,inner,outer),onto);
		TexturedShape rest = minus(onto,transform(translate(mouse),outerShape));
		return over(rest,borderArea);
//		return over(minus(onto,transform(translate(mouse),outerShape)),borderArea,zoomedArea);
	}


	
}
