package nogbeter.demo;

import static nogbeter.transform.AffineTransformation.id;

import java.util.ArrayList;
import java.util.List;

import nogbeter.demo.awt.DemoBase;
import nogbeter.paths.Path;
import nogbeter.paths.factory.TextFactory;
import nogbeter.paths.iterators.ConnectedIterator;
import nogbeter.points.twod.Vec;
import nogbeter.transform.nonlinear.IDeform;
import nogbeter.transform.nonlinear.ffd.QuadFFD;

public class FFDTest extends DemoBase{

	public static void main(String[] argv){
		List<List<Vec>> v = new ArrayList<List<Vec>>();
		List<Vec> vv = new ArrayList<Vec>();
		vv.add(new Vec(0,0));
		vv.add(new Vec(1,0));
		vv.add(new Vec(2,0));
		v.add(vv);
		vv = new ArrayList<Vec>();
		vv.add(new Vec(0,1));
		vv.add(new Vec(1,1));
		vv.add(new Vec(2,1));
		v.add(vv);
		vv = new ArrayList<Vec>();
		vv.add(new Vec(0,2));
		vv.add(new Vec(1,2));
		vv.add(new Vec(2,2));
		v.add(vv);
		System.out.println(QuadFFD.evaluateQuadBezierSurface(new Vec(1.0,0.75),v ));
		new FFDTest();
	        
	}


	private Path r;
	
	public FFDTest() {
		
		r = TextFactory.text2Paths("wso").transform(id.scale(5).translate(400, 400));
	}
	
	@Override
	public void draw() {
        Path ts = TextFactory.text2Paths("Atze!");
        ts = ts.transform(id.scale(7).translate(100,400));
        double rad = Math.sqrt(2) * 50 + wheel / 20;
        double dia = rad / Math.sqrt(2);
        IDeform d = QuadFFD.makeQuadFFD(225, 
        		new double[] {mouse.x - 50, mouse.x + 50},
        		new double[] {mouse.y - 50, mouse.y + 50},
//        		new Vec[][]{ new Vec[] { new Vec(mouse.x - 50, mouse.y - 50), 
//						new Vec(mouse.x, mouse.y - 50),
//						new Vec(mouse.x + 50, mouse.y - 50)},
//			new Vec[] { new Vec(mouse.x - 50, mouse.y ), 
//						new Vec(mouse.x, mouse.y ),
//						new Vec(mouse.x + 50, mouse.y)},
//		new Vec[] { new Vec(mouse.x - 50, mouse.y + 50 ), 
//					new Vec(mouse.x, mouse.y + 50 ),
//					new Vec(mouse.x + 50, mouse.y + 50)}});				
        		new Vec[][]{ new Vec[] { new Vec(mouse.x - dia, mouse.y - dia), 
        								new Vec(mouse.x, mouse.y - rad),
        								new Vec(mouse.x + dia, mouse.y - dia)},
							new Vec[] { new Vec(mouse.x - rad, mouse.y ), 
										new Vec(mouse.x, mouse.y ),
										new Vec(mouse.x + rad, mouse.y)},
						new Vec[] { new Vec(mouse.x - dia, mouse.y + dia ), 
									new Vec(mouse.x, mouse.y + rad ),
									new Vec(mouse.x + dia, mouse.y + dia)}});										
        								
       
        fill(ts.deform(d));
	}
	
	
}
