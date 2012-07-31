package deform.tests;

import static deform.Combinators.*;
import static deform.Library.*;
import deform.Color;
import deform.Texture;
import deform.Transform;
import deform.Vec;
import deform.library.Camera;
import deform.texturedshape.TexturedShape;
import deform.transform.lenses.BasicLens;

public class MandelBrot extends DemoBase{

	public static void main(String[] argv){
		new MandelBrot();
	}
	
	double zoom = 1;
	boolean fisheye = false;
	Camera c;
	Texture mandelRect;
	Vec prevMouse;
	@Override
	public void init() {
		zoom = 1;
		c = new Camera();
		mandelRect = transform(translate(size.div(2)).compose(scale(size.x/4*3)),new MandelBrotTex());
	}
	
	static class MandelBrotTex implements Texture{
		static int MaxIters = 30;
		static final double log2 = Math.log(2);
		static final Color[] colors = new Color[]{color(0, 0, 0),color(255,255,0),color(255,0,0),color(0,0,0)};
		static final double amp = 1.0/(colors.length-1);
		@Override
		public Color sample(Vec point) {
			double tq = point.x - 0.25;
			double q = tq * tq + point.y * point.y;
			if( q * (q + (point.x - 0.25)) < 0.25*point.y * point.y){
				return color(0,0,0);
			}
			
			double x = point.x;
			double y = point.y;
			int i ;
			double x2 = x*x;
			double y2 = y*y;
			double mod = x2 + y2;
			for(i = 0 ; i < MaxIters && mod < 4; i++){
				double xtemp = x2 - y2 + point.x;
				y = 2 * x * y + point.y;
				x = xtemp;
				x2 = x*x;
				y2 = y*y;
				mod = x2 + y2;
				
			}
			double mu = (i - Math.log(Math.log(Math.sqrt(mod))/log2))/MaxIters;
//			System.out.println(mu);
//			double mu = (double)i / MaxIters;
			int start = (int)(mu / amp);
			if(start < 0 || start >= colors.length-1){
				return color(0,0,0);
			}
			
			return colors[start].lerpNoAlpha((mu - start*amp)/amp, colors[start+1]);
			
		}
		
	}

	public void handleKeyStroke(char key){
		if(key == 'a'){
			MandelBrotTex.MaxIters++;
		}
		if(key == 'z'){
			if(MandelBrotTex.MaxIters > 1){
				MandelBrotTex.MaxIters--;
			}
		}
		if(key == 'f'){
			this.fisheye = !fisheye;
		}
	}
	

	public void handleMouseRelease(int button) {
		prevMouse =null;
		
	}
	

	public void handleMouseClick(int button) {
		prevMouse = mouse;
	}
	
	public void handleMouseWheel(int unitsToScroll) {
//			System.out.println(unitsToScroll);
			c.zoom(mouse, unitsToScroll  > 0 ? 0.8 : 1.2);
	}

	@Override
	void draw() {
		if(prevMouse!=null){
			c.move(prevMouse.sub(mouse));
			prevMouse = mouse;
		}
		Transform t = fisheye? new BasicLens(mouse, 5, 100, 180).compose(c.getTransform()) : c.getTransform();
		TexturedShape s = fill(transform(scale(Math.max(size.x,size.y)),rectangle()),
				
				transform(t,mandelRect));
		draw(s);
	}

}
