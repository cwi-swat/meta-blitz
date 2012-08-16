package deform.tests;

import static deform.Combinators.*;
import static deform.Combinators.fill;
import static deform.Combinators.quadTo;
import static deform.Combinators.transform;
import static deform.Library.fillColor;
import static deform.Library.over;
import static deform.Library.path;
import static deform.Library.radialGradient;
import static deform.Library.rectangle;
import static deform.Library.scale;
import static deform.Library.stroke;
import static deform.Library.sweep;
import static deform.Library.text;
import static deform.Library.*;
import deform.Color;
import deform.Texture;
import deform.Vec;
import deform.Library.ColorAndFraction;
import deform.paths.Path;
import deform.segments.LineTo;
import deform.shapes.Shape;
import deform.texturedshape.TexturedShape;
import deform.transform.lenses.LinearLens;
import deform.transform.lenses.Norms;
import deform.transform.lenses.NumericToLens;
import deform.transform.lenses.Profiles;

public class LensImage extends DemoBase{

	public static void main(String[] argv){
		
		final Vec size = new Vec(800,800);
		Vec mouse = size.div(2);
		int linesize = 20;
		int linewidth = 2;
		Shape hline = stroke(path(0, 0, lineTo(new Vec(size.x,0))),linewidth);
		Shape vline = stroke(path(0, 0, lineTo(new Vec(0,size.y))),linewidth);
		int linesx = (int)(size.x / linesize);
		int linesy = (int)(size.y / linesize);
		Shape[] lines = new Shape[linesx + linesy];
		for(int i = 0 ; i < linesy ; i++){
			lines[i] = transform(translate(0,i * linesize + linesize / 2), hline);
		}
		for(int i = 0 ; i < linesx ; i++){
			lines[i+linesy] = transform(translate(i * linesize + linesize / 2,0), vline);
		}
		Shape allLines = set(lines);
		
		Texture fade = new Texture() {
			final double border = 90;
			@Override
			public Color sample(Vec point) {
				double dx = point.x;
				double dy = point.y ;
				if(point.x <= border){
					dx = border;
				}  else if(point.x >= size.x - border){
					dx = size.x - border;
				}
				
				if(point.y <= border){
					dy = border;
				}  else if(point.y >= size.y - border){
					dy = size.y - border;
				}
				return color(170,170,170);
			}
		};
		TexturedShape s = fill(allLines,fade);

//		TexturedShape innercircle = fill(stroke(transform(translate(mouse).compose(scale(150)),circle()),2),fillColor(0,0,0));
//		TexturedShape outercircle = fill(stroke(transform(translate(mouse).compose(scale(300)),circle()),2),fillColor(0,0,0));
		int fontsize = 25;
//		Shape focuss = text("Focus area",fontsize);
//		focuss = transform(translate(new Vec(-focuss.bbox.xInterval.middle(),0).add(mouse)),focuss);
//		Shape distortion = text("Distortion area",fontsize);
//		distortion = transform(translate(new Vec(-distortion.bbox.xInterval.middle(),-225).add(mouse)),distortion);
//		Shape context = text("Context",fontsize);
//		context = transform(translate(new Vec(-context.bbox.xInterval.middle(),-325).add(mouse)),context);
//		TexturedShape distortions = fill(set(distortion,focuss,context), fillColor(0,0,0));
//		TexturedShape focus =fill(focuss, fillColor(0,0,0));
		s = transform(new NumericToLens(Norms.circle,Profiles.gauss,mouse, 2,150,300),s);
//		s = over(s, distortions,outercircle,innercircle);
//		s = transform(new LinearLens(Norms.circle, mouse, 2,100,200),s);
//		s = transform(coneLens(mouse, 100 + wheel),s);
//		s = transform(fisheye(mouse,Math.abs(wheel)/100 , 200), s);
		
		render(800, 800, "/home/ploeg/bla", s);
	}
	
	
	@Override
	public void init(){
//		img = tiledImage("/home/ploeg/marble.jpg");
	}
	
	@Override
	void draw() {
	
		
	}
}
