package nogbeter.demo;

import bezier.points.Vec;
import nogbeter.paths.Path;
import nogbeter.paths.PathFactory;
import static nogbeter.paths.PathFactory.*;
public class DrawSomething extends DemoBase {

	public static void main(String[] argv){
		new DrawSomething();
	}
	
	@Override
	public void draw() {
		Path r = rectangle();
		fill(r);
		
	}

	
	public Path rectangle(){
		Vec a = new Vec(100,100);
		Vec b = new Vec(200,100);
		Vec c = new Vec(200,200);
		Vec d = new Vec(100,200);
		Vec e = new Vec(150,50);
		return createAppends(createQuad(a,e,b), createLine(b,c), createLine(c,d),createLine(d,a));
	}
	
}
